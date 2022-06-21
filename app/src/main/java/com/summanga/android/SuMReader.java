package com.summanga.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.interfaces.GestureView;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
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
    private int USERCOINS_COUNT = 0;
    private Bitmap VIEWBG;
    private int REQ_COINS = -1;
    private Animation animation_card_click;
    private Animation animation_toolbar_click;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sumreader_popup);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        animation_card_click = AnimationUtils.loadAnimation(SuMReader.this, R.anim.card_click);
        animation_toolbar_click = AnimationUtils.loadAnimation(SuMReader.this, R.anim.toolbar_click);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }
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
            FinishSuMReader();
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
            notifyUser("404");
            FinishSuMReader();
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
            notifyUser("404");
            FinishSuMReader();
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                USERCOINS_COUNT = 0;
            } else {
                String aancc = extras.getString("USERCOINS_COUNT");
                if(!aancc.contains("[") && !aancc.contains("_")) {
                    USERCOINS_COUNT = Integer.parseInt(aancc);
                }
            }
        } else {
            String aancc = (String) savedInstanceState.getSerializable("USERCOINS_COUNT");
            if(!aancc.contains("[") && !aancc.contains("_")) {
                USERCOINS_COUNT = Integer.parseInt(aancc);
            }
        }


        findViewById(R.id.SuMPOPUP_SUMREADER_BG_Tr).setBackgroundColor(Color.parseColor(THEME_RBG));

        ((GestureView)findViewById(R.id.SuMReder_GesVFL)).getController().getSettings()
                .setMaxZoom(2f)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(false)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(false)
                .setFitMethod(Settings.Fit.INSIDE)
                .setGravity(Gravity.CENTER);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                VIEWBG = null;
            } else {
                String aancc = extras.getString("VIEWBITMAP");
                VIEWBG = ImageUtil.convert(aancc);
            }
        } else {
            String aancc = (String) savedInstanceState.getSerializable("VIEWBITMAP");
            VIEWBG = ImageUtil.convert(aancc);
        }

        if(VIEWBG==null)notifyUser("VIWE-BG-BLUR:404");
        else {
            VIEWBG = blur(SuMReader.this,VIEWBG,0.64f,18.0f);
            ((ImageView)findViewById(R.id.SuMPOPUP_SUMREADER_BG_Tr_BLUR)).setImageBitmap(VIEWBG);
        }

        assert cookies != null;
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
                                findViewById(R.id.LoadChapter_BTN_Cancel).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c18dp), Color.parseColor("#007a7a7a")));
                                findViewById(R.id.LoadChapter_BTN).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c18dp), Color.parseColor("#ffffff"/*THEME_RBG*/)));
                                findViewById(R.id.SuMReadeInfinateLoading_anim).setVisibility(View.GONE);
                                findViewById(R.id.SuMAgreementPOPUP).setVisibility(View.VISIBLE);
                                if(ResBody.contains("[")||ResBody.contains("_")||ResBody.contains("<")||ResBody.contains(">")||ResBody.contains('"'+"")){
                                    REQ_COINS = -1;
                                    notifyUser("Loading failed!");
                                    FinishSuMReader();
                                }else {
                                    REQ_COINS = Integer.parseInt(ResBody);
                                }
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
                        FinishSuMReader();
                    }
                });
            }

        });

    }
    public void Cancel(View view){
        SuMStaticVs.SUMREADER_MOVEON_MBIT = 2;
        FinishSuMReader();
    }
    public static Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
    public void LoadChapter(View view) {
        if(REQ_COINS > USERCOINS_COUNT){
            notifyUser("SuM-Coins: insufficient balance!");
            return;
        }
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
                final String ResBody = Objects.requireNonNull(response.body()).string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
                            @Override
                            public void run() {
                                if(!ResBody.contains("#File;Split&")){
                                    notifyUser("SuM-Reader: Chapter is not available!");
                                    FinishSuMReader();
                                } else initView(ResBody);
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
                        FinishSuMReader();
                    }
                });
            }

        });

    }
    private void FinishSuMReader(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SuMStaticVs.lastPositionIMG = -1;
                SuMReader.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
            }
        });
    }
    public static Bitmap blur(Context context, Bitmap image ,float BITMAP_SCALE,float BLUR_RADIUS) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return  outputBitmap;
        /*Bitmap bm = outputBitmap;

        Canvas canvas = new Canvas(bm);
        canvas.drawARGB(80,0,0,0);
        canvas.drawBitmap(bm, new Matrix(), new Paint());
        return bm;*/

    }
    private void initView(String RS) {

        // Initialize RecyclerView and set Adapter
        //SuMStaticVs.MainC = SuMReader.this;
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

    @Override
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
        } else {
            if(keyCode == KeyEvent.KEYCODE_BACK){
                SuMReader.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
        return super.onKeyDown(keyCode, event);
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
                findViewById(R.id.SuMReaderToolBar).startAnimation(animation_toolbar_click);
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
        if(view != null) view.startAnimation(animation_card_click);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(60);
        }
        FinishSuMReader();
    }

    @SuppressLint("ObsoleteSdkInt")
    public void Next(View view) {
        view.startAnimation(animation_card_click);
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
            Bitmap vbg = getScreenShot(findViewById(R.id.ReaderLayout_POPUP));
            i.putExtra("THEME_RBG", THEME_RBG);
            i.putExtra("FILES_LINK", FILES_LINK_NEXT);
            i.putExtra("USERCOINS_COUNT", USERCOINS_COUNT+"");
            i.putExtra("VIEWBITMAP",ImageUtil.convert(getResizedBitmap(vbg,vbg.getWidth()/8,vbg.getHeight()/8)));
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
                        FinishSuMReader();
                        timer.cancel();
                    }


                }
            }, 0, 640);//wait 0 ms before doing the action and do it evry 1000ms (1second)

        }
    }

    private Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
