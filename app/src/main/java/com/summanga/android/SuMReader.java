package com.summanga.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.CookieManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SuMReader extends AppCompatActivity {

    private boolean SuMReadingMode = false;
    private String FILES_LINK = null;
    private String FILES_LINK_NEXT = null;
    private String THEME_RBG = null;
    private RecyclerView recyclerView;
    private ScoutAdapterIMG adapter;
    private ArrayList<ScoutIMG> scoutArrayList;
    private boolean SUM_NEXT = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sumreader_popup);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        int UID = 0;
        Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");
        if (cookies != null) {
            if (cookies.toString().contains("SuMCurrentUser")) {
                String[] AA = cookies.toString().replace(" ", "").split(";");
                for (int i = 0; i < AA.length; i++) {
                    if (AA[i].contains("ID=")) {
                        String[] BBA = AA[i].split("&");
                        for(int ii = 0; ii<BBA.length;ii++) {
                            if (BBA[ii].contains("ID=")&& !BBA[ii].contains("SID")) {
                                UID = Integer.parseInt(BBA[ii].replaceAll("[^\\d.]", "").replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
                                ii = BBA.length;
                            }
                        }
                        i = AA.length;
                    }
                }
            }
        }
        if(UID<1){
            notifyUser("Login First!");
            SuMReader.this.finish();
        }
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(320);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                THEME_RBG= null;
            } else {
                THEME_RBG= extras.getString("THEME_RBG");
            }
        } else {
            THEME_RBG= (String) savedInstanceState.getSerializable("THEME_RBG");
        }
        if(THEME_RBG == null){
            SuMReader.this.finish();
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                FILES_LINK= null;
            } else {
                FILES_LINK= extras.getString("FILES_LINK");
            }
        } else {
            FILES_LINK= (String) savedInstanceState.getSerializable("FILES_LINK");
        }
        if(FILES_LINK == null){
            SuMReader.this.finish();
        }

        String[] cp = cookies.toString().split(";");
        String SuMCurrentUser_Value = null;
        for (int i = 0;i<cp.length;i++){
            if(cp[i].contains("SuMCurrentUser=")&&cp[i].contains("SID=")) {
                SuMCurrentUser_Value = cp[i].replace("SuMCurrentUser=", "");
                i=cp.length;
            }
        }
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        cookieHelper.setCookie("https://sum-manga.azurewebsites.net/", "SuMCurrentUser", SuMCurrentUser_Value);
        Handler mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieHelper.cookieJar())
                .build();
        String mid = FILES_LINK.split("MID=")[1];
        Request request = new Request.Builder()
                .url("https://sum-manga.azurewebsites.net/APIs/SuMCoinsReq.aspx?MID="+mid)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String ResBody =  response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
                            @Override
                            public void run() {
                                ((TextView)findViewById(R.id.SuMReader_ReqCoinsNum)).setText(" "+ResBody+" ");
                                findViewById(R.id.LoadChapter_BTN_Cancel).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c18dp), Color.parseColor("#000000")));
                                findViewById(R.id.LoadChapter_BTN).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c18dp), Color.parseColor(THEME_RBG)));
                                findViewById(R.id.SuMReadeInfinateLoading_anim).setVisibility(View.GONE);
                                findViewById(R.id.SuMAgreementPOPUP).setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyUser("Loading failed!");
                        SuMReader.this.finish();
                    }
                });
            }

        });

    }
    public void Cancel(View view){
        SuMStaticVs.SUMREADER_MOVEON_MBIT = 2;
        SuMReader.this.finish();
    }
    public static Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
    public void LoadChapter(View view) {
        SuMStaticVs.SUMREADER_MOVEON_MBIT = 1;
        findViewById(R.id.SuMAgreementPOPUP).setVisibility(View.GONE);
        SuMSecure();
        notifyUser("SuM-Reading mode is activated");
    }
    private void SuMSecure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.SuMReadeInfinateLoading_anim).setVisibility(View.VISIBLE);
                findViewById(R.id.SuMAgreementPOPUP).setVisibility(View.GONE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

                );
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                findViewById(R.id.SuMReaderToolBar).setVisibility(View.VISIBLE);

            }
        });
        SuMReadingMode = true;
        Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");
        String[] cp = cookies.toString().split(";");
        String SuMCurrentUser_Value = null;
        for (int i = 0; i < cp.length; i++) {
            if (cp[i].contains("SuMCurrentUser=") && cp[i].contains("SID=")) {
                SuMCurrentUser_Value = cp[i].replace("SuMCurrentUser=", "");
                i = cp.length;
            }
        }
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        cookieHelper.setCookie("https://sum-manga.azurewebsites.net/", "SuMCurrentUser", SuMCurrentUser_Value);
        Handler mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieHelper.cookieJar())
                .build();
        Request request = new Request.Builder()
                .url("https://sum-manga.azurewebsites.net" + FILES_LINK)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String ResBody = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
                            @Override
                            public void run() {
                                initView(ResBody);
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyUser("Loading failed!");
                        SuMReader.this.finish();
                    }
                });
            }

        });

    }
    private void initView(String RS) {

        // Initialize RecyclerView and set Adapter
        SuMStaticVs.MainC = SuMReader.this;
        recyclerView = findViewById(R.id.scout_IMG_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SuMReader.this));
        scoutArrayList = new ArrayList<>();
        adapter = new ScoutAdapterIMG(SuMReader.this,scoutArrayList);
        recyclerView.setAdapter(adapter);
        createList(RS);
    }

    private void createList(final String RS) {
        runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                String[] PRS = RS.split("#File;Split&");
                for (int i = 1; i < PRS.length; ++i) {
                    scoutArrayList.add(new ScoutIMG(PRS[i]));
                }

                adapter.notifyDataSetChanged();
                findViewById(R.id.SuMReadeInfinateLoading_anim).setVisibility(View.GONE);
                findViewById(R.id.SuMMangaIMGs_C).setVisibility(View.VISIBLE);
                if(!RS.contains("#0&")) SUM_NEXT = true;
                StringBuilder bvcn = new StringBuilder();
                String[] a = FILES_LINK.split("&CN=ch");
                for (int i = 0;i<a[1].length();i++){
                    if(a[1].charAt(i) != '&') {
                        bvcn.append(a[1].charAt(i));
                    } else i = a[1].length();
                }
                String CN = (Integer.parseInt(bvcn.toString().replace(" ", ""))+1)+"";
                String cnf = "ch";
                if(CN.length()==1) cnf+="000";
                if(CN.length()==2) cnf+="00";
                if(CN.length()==3) cnf+="0";
                cnf+=CN;
                String[] bb = FILES_LINK.split("&");
                for(int i=0;i<bb.length;i++){
                    if(bb[i].contains("CN=ch")){
                        FILES_LINK_NEXT = FILES_LINK.replace(bb[i].replace("CN=",""),cnf);
                        i = bb.length;
                    }
                }
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && SuMReadingMode) {
            runOnUiThread(new Runnable() {
                @SuppressLint("ObsoleteSdkInt")
                @Override
                public void run() {
                    Toast.makeText(SuMReader.this, "To exit reading mode PRESS ON THE CLOSE ICON in the toolbar", Toast.LENGTH_LONG).show();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(90, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(90);
                    }
                }
            });
            return false;
        } else return super.onKeyDown(keyCode, event);
    }
    private void notifyUser(final String message)
    {
        Toast.makeText(SuMReader.this, message, Toast.LENGTH_SHORT).show();
    }

    public void ExpandSuMReadToolBar(View view) {

        runOnUiThread(new Runnable() {
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void run() {
                boolean expanded = false;
                if(findViewById(R.id.SuMCloseReader).getVisibility() == View.VISIBLE) expanded = true;
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(60);
                }
                if(expanded){
                    findViewById(R.id.ExpandSuMReadToolBar_BTN).setRotation(0);
                    findViewById(R.id.SuMCloseReader).setVisibility(View.GONE);
                    findViewById(R.id.SuMNextInReader).setVisibility(View.GONE);
                    findViewById(R.id.SuMReaderToolBar).setAlpha((float) 0.32);
                } else {
                    findViewById(R.id.ExpandSuMReadToolBar_BTN).setRotation(180);
                    findViewById(R.id.SuMCloseReader).setVisibility(View.VISIBLE);
                    findViewById(R.id.SuMNextInReader).setVisibility(View.VISIBLE);
                    findViewById(R.id.SuMReaderToolBar).setAlpha((float) 1.0);
                }
            }
        });

    }

    @SuppressLint("ObsoleteSdkInt")
    public void ExitFullScreen(View view) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(60);
        }
        SuMReader.this.finish();
    }

    @SuppressLint("ObsoleteSdkInt")
    public void Next(View view) {
        SuMStaticVs.SUMREADER_MOVEON_MBIT = 0;
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(60);
        }
        if(!SUM_NEXT) notifyUser("No more chapters are available!");
        else {
            Intent i = new Intent(SuMReader.this, SuMReader.class);
            i.putExtra("THEME_RBG", THEME_RBG);
            i.putExtra("FILES_LINK", FILES_LINK_NEXT);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if(SuMStaticVs.SUMREADER_MOVEON_MBIT == 2){
                        SuMStaticVs.SUMREADER_MOVEON_MBIT = 0;
                        timer.cancel();
                    }
                    if(SuMStaticVs.SUMREADER_MOVEON_MBIT == 1) {
                        SuMStaticVs.SUMREADER_MOVEON_MBIT = 0;
                        SuMReader.this.finish();
                        timer.cancel();
                    }


                }
            }, 0, 640);//wait 0 ms before doing the action and do it evry 1000ms (1second)

        }
    }
}
