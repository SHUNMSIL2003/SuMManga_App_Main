package com.summanga.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.biometrics.BiometricPrompt;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 20.0f;
    private static final int SPLASH_TIME_OUT = 200;
    private CancellationSignal cancellationSignal = null;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;
    private static String UB = null;
    private int r = 0;
    private int g = 0;
    private int b = 0;

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) SplashScreen.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }


    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }

    public static Bitmap blur(Context context, Bitmap image) {
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

    }

    protected boolean PermIsGra(String PID){
        return ActivityCompat.checkSelfPermission(SplashScreen.this, PID) == PackageManager.PERMISSION_GRANTED;
    }

    private static String uri_AL_STRING;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!PermIsGra(Manifest.permission.READ_EXTERNAL_STORAGE)||!PermIsGra(Manifest.permission.READ_PHONE_STATE)) {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE}, 225);
            notifyUser("permissions are required: READ_EXTERNAL_STORAGE & READ_PHONE_STATE");
            SplashScreen.this.finish();
            return;
        }
        Uri uri_AL = getIntent().getData();
        if (uri_AL!=null){
            uri_AL_STRING = uri_AL.toString();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.splashscreen);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(10);
        Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");
        int UID = 0;
        if (cookies != null) {
            if (cookies.toString().contains("SuMCurrentUser")) {
                String[] AA = cookies.toString().replace(" ", "").split(";");
                for (int i = 0; i < AA.length; i++) {
                    if (AA[i].contains("ID=") && !AA[i].contains("SuMurrentLoginWorkerache")) {
                        String[] BBA = AA[i].split("&");
                        for(int ii = 0; ii<BBA.length;ii++) {
                            if (BBA[ii].contains("ID=")&& !BBA[ii].contains("SID")&&!BBA[ii].contains("SuMurrentLoginWorkerache")) {
                                UID = Integer.parseInt(BBA[ii].replaceAll("[^\\d.]", "").replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
                                ii = BBA.length;
                            }
                        }
                        i = AA.length;
                    }
                }
            }
        }
        ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(15);


        if(!isConnectedToInternet()){
            Bitmap AppBG_Bitmap = getBitmapFromAsset(SplashScreen.this,"SuM-Reader.jpg");//SuM-ReadingStart.jpg
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int Device_Height = displayMetrics.heightPixels;
            int Device_Width = displayMetrics.widthPixels;
            ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(5);
            AppBG_Bitmap = Bitmap.createScaledBitmap(AppBG_Bitmap, 180, (180*(Device_Height/Device_Width)), false);
            AppBG_Bitmap = blur(SplashScreen.this,AppBG_Bitmap);
            findViewById(R.id.SplashScreenLayout).setBackground(new BitmapDrawable(getResources(), AppBG_Bitmap));
            ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(100);
            findViewById(R.id.SuMSplashIMG).setVisibility(View.GONE);
            findViewById(R.id.Offline_Options).setVisibility(View.VISIBLE);
            LoadDinoGame();
        } else {
            if (UID > 0) {
                //cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");
                if (cookies.toString().contains("SuMCurrentUser")) {
                    String[] AA = cookies.toString().replace(" ", "").split(";");
                    for (int i = 0; i < AA.length; i++) {
                        if (AA[i].contains("ID=")) {
                            String[] BBA = AA[i].split("&");
                            for (int ii = 0; ii < BBA.length; ii++) {
                                if (BBA[ii].contains("UB=")) {
                                    UB = BBA[ii].replace(" ", "").replace("UB=", "").replace("SuMCurrentUser=", "").replace(" ", "");
                                    ii = BBA.length;
                                }
                            }
                            i = AA.length;
                        }
                    }
                } else return;
                ((LinearProgressIndicator) findViewById(R.id.SuMSplashProssBar)).setProgress(20);
                SuMSecureLoading();
            } else {
                Bitmap AppBG_Bitmap = getBitmapFromAsset(SplashScreen.this,"SuM-Reader.jpg");//SuM-ReadingStart.jpg
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int Device_Height = displayMetrics.heightPixels;
                int Device_Width = displayMetrics.widthPixels;
                ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(5);
                if(Device_Height>Device_Width) AppBG_Bitmap = Bitmap.createScaledBitmap(AppBG_Bitmap, 180, (180*(Device_Height/Device_Width)), false);
                else AppBG_Bitmap = Bitmap.createScaledBitmap(AppBG_Bitmap, 180*(Device_Width/Device_Height), 180, false);
                AppBG_Bitmap = blur(SplashScreen.this,AppBG_Bitmap);
                findViewById(R.id.SplashScreenLayout).setBackground(new BitmapDrawable(getResources(), AppBG_Bitmap));
                ((LinearProgressIndicator) findViewById(R.id.SuMSplashProssBar)).setProgress(100);
                WebView WebView0RecentsCard = (WebView) findViewById(R.id.SuMLogInWebView);
                WebView0RecentsCard.setVisibility(View.VISIBLE);
                GetThisWenViewReady(WebView0RecentsCard);
                findViewById(R.id.SuMSplashIMG).setVisibility(View.GONE);
            }
        }


    }
    private void SuMSecureLoading() {
        Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");
        String RS = "";
        if (cookies != null) {
            if (cookies.toString().contains("RGBRoot=")) {
                String[] AA = cookies.toString().replace(" ", "").split(";");
                boolean foundAA = false;
                for (int i = 0; i < AA.length; i++) {
                    if (AA[i].contains("RGBRoot=")) {
                        RS = AA[i].replace(" ", "").replace("SuMUserThemeColor=RGBRoot=", "");
                        foundAA = true;
                        i = AA.length;
                    }
                }
                if (!foundAA) {
                    RS = "104,64,217";
                }
            } else RS = "104,64,217";
        } else RS = "104,64,217";
        r = Integer.parseInt(RS.split(",")[0]);
        g = Integer.parseInt(RS.split(",")[1]);
        b = Integer.parseInt(RS.split(",")[2]);
        String RootHexColor0 = String.format("#%02X%02X%02X", r, g, b);
        findViewById(R.id.SplashScreenLayout).setBackgroundColor(Color.parseColor(RootHexColor0));
        //CookieSyncManager.getInstance().sync();


        ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(10);


        SharedPreferences mPrefs = getSharedPreferences("summanga", 0);
        String mSuMlOCKBit = mPrefs.getString("sumlockbit", "0");
        if(mSuMlOCKBit.equals("1")) {

            authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                // here we need to implement two methods
                // onAuthenticationError and
                // onAuthenticationSucceeded If the
                // fingerprint is not recognized by the
                // app it will call onAuthenticationError
                // and show a toast
                @Override
                public void onAuthenticationError(
                        int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    notifyUser("Authentication Error : " + errString);
                    ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(0);
                    SplashScreen.this.finish();
                }

                // If the fingerprint is recognized by the
                // app then it will call
                // onAuthenticationSucceeded and show a
                // toast that Authentication has Succeed
                // Here you can also start a new activity
                // after that
                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    notifyUser("Authentication Succeeded");
                    ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(15);
                    StartLoading();
                }
            };

            checkBiometricSupport();

            BiometricPrompt biometricPrompt = new BiometricPrompt
                    .Builder(getApplicationContext())
                    .setTitle("SUM Verification!")
                    .setSubtitle("verify your identity")
                    .setDescription("after all we care about your SUM world")
                    .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                        @Override
                        public void
                        onClick(DialogInterface dialogInterface, int i) {
                            notifyUser("Authentication Cancelled");
                            ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(0);
                            SplashScreen.this.finish();
                        }
                    }).build();
            biometricPrompt.authenticate(
                    getCancellationSignal(),
                    getMainExecutor(),
                    authenticationCallback);

        } else {
            ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(15);
            StartLoading();
        }
    }
    private void notifyUser(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private CancellationSignal getCancellationSignal()
    {
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(
                new CancellationSignal.OnCancelListener() {
                    @RequiresApi(api = Build.VERSION_CODES.P)
                    @Override public void onCancel()
                    {
                        notifyUser("Authentication was Cancelled by the user");
                        SplashScreen.this.finish();
                    }
                });
        return cancellationSignal;
    }
    private Boolean checkBiometricSupport()
    {
        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            notifyUser("Fingerprint authentication has not been enabled in settings - clear app data to reset sumlock");
            SplashScreen.this.finish();
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled");
            SplashScreen.this.finish();
            //SplashScreen.setVisibility(View.VISIBLE);
            return false;
        }
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true;
        }
        else
            return true;
    }
    private void StartLoading() {
        StringBuilder CURL = new StringBuilder("https://sum-manga.azurewebsites.net");
        Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");
        if (cookies != null) {
            if (cookies.toString().contains("SuMCurrentUser")) {
                String[] AA = cookies.toString().replace(" ", "").split(";");
                for (int i = 0; i < AA.length; i++) {
                    if (AA[i].contains("UB=")) {
                        String[] BBA = AA[i].split("&");
                        for(int ii = 0; ii<BBA.length;ii++) {
                            if (BBA[ii].contains("UB=")) {
                                CURL.append(BBA[ii].replace(" ", "").replace("UB=", "").replace("SuMCurrentUser=", "").replace("&", ""));
                                ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(20);
                                ii = BBA.length;
                            }
                        }
                        i = AA.length;
                    }
                }
            }
        }



        Glide.with(SplashScreen.this)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(CURL.toString())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                        if (resource == null) SplashScreen.this.finish();

                        ((LinearProgressIndicator) findViewById(R.id.SuMSplashProssBar)).setProgress(30);
                        Bitmap bb = Bitmap.createScaledBitmap(resource, resource.getWidth() / 8, resource.getHeight() / 8, false);
                        bb = blurDark(SplashScreen.this, bb, 20.0f, r, g, b);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        if (CURL.toString().contains(".png")) {
                            bb.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        } else {
                            bb.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        }
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        ((LinearProgressIndicator) findViewById(R.id.SuMSplashProssBar)).setProgress(60);
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        if (encoded == null) {
                            notifyUser("encoded msg is null!");
                            SplashScreen.this.finish();
                        } else ABSStart(encoded);
                    }
                });




    }
    public static Bitmap blurDark(Context context, Bitmap image,float BLUR_RADIUS,int r,int g,int b) {
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

        //return  outputBitmap;
        Bitmap bm = outputBitmap;

        Canvas canvas = new Canvas(bm);
        canvas.drawARGB(64,r,g,b);
        canvas.drawBitmap(bm, new Matrix(), new Paint());
        return bm;

    }
    public static String encode(String url)
    {
        try {
            String encodeURL= URLEncoder.encode( url, "UTF-8" );
            return encodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while encoding" +e.getMessage();
        }
    }
    public static String decode(String url)
    {
        try {
            String prevURL="";
            String decodeURL=url;
            while(!prevURL.equals(decodeURL))
            {
                prevURL=decodeURL;
                decodeURL= URLDecoder.decode( decodeURL, "UTF-8" );
            }
            return decodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while decoding" +e.getMessage();
        }
    }

    public String SuMLinkParsers(String LINK_S,Boolean CODE) {
        if(LINK_S==null) return null;
        String P_RS = "";
        if(CODE) {
            P_RS = "https://sum-manga.azurewebsites.net/SuM.html?MPS=" + encode(rot13(LINK_S));
            return P_RS;
        } else {
            if (!LINK_S.contains("=")) return null;
            P_RS = rot13(decode(LINK_S.split("=")[1]));
            return P_RS;
        }
    }

    public static String rot13(String value) {

        char[] values = value.toCharArray();
        for (int i = 0; i < values.length; i++) {
            char letter = values[i];

            if (letter >= 'a' && letter <= 'z') {
                // Rotate lowercase letters.

                if (letter > 'm') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            } else if (letter >= 'A' && letter <= 'Z') {
                // Rotate uppercase letters.

                if (letter > 'M') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            }
            values[i] = letter;
        }
        // Convert array to a new String.
        return new String(values);
    }
    public static String rot13_decode(String value) {

        char[] values = value.toCharArray();
        for (int i = 0; i < values.length; i++) {
            char letter = values[i];

            if (letter >= 'a' && letter <= 'z') {
                // Rotate lowercase letters.

                if (letter > 'm') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            } else if (letter >= 'A' && letter <= 'Z') {
                // Rotate uppercase letters.

                if (letter > 'M') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            }
            values[i] = letter;
        }
        // Convert array to a new String.
        return new String(values);
    }
    private void ABSStart(String bm){
        ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(75);
        if (bm != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i = new Intent(SplashScreen.this, MainActivity.class);
                            i.putExtra("LOADING_MESSAGE", "LOADED");
                            i.putExtra("BANNER_BITMAP_STRING64", bm);
                            if(uri_AL_STRING!=null) {
                                String RS = SuMLinkParsers(uri_AL_STRING, false);
                                if(RS!=null) i.putExtra("LINKOPPENSER_DECODDED_URL",RS);
                            }
                            ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(80);
                            startActivity(i);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
                            ((LinearProgressIndicator)findViewById(R.id.SuMSplashProssBar)).setProgress(100);
                            SplashScreen.this.finish();
                        }
                    }, 380);
                }
            }, SPLASH_TIME_OUT);
        } else SplashScreen.this.finish();
    }

    public void OpenDownloads(View view) {
    }

    private class yWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            if(!url.contains("/AccountETC/") || url.contains("Tr-Test-SuT.html") || url.contains("/SettingsAccountCard.aspx") || url.contains("SuMMangaInstallAPP.aspx")){
                Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");
                int UID = 0;
                if (cookies != null) {
                    if (cookies.toString().contains("SuMCurrentUser")) {
                        String[] AA = cookies.toString().replace(" ", "").split(";");
                        for (int i = 0; i < AA.length; i++) {
                            if (AA[i].contains("ID=") && !AA[i].contains("SuMurrentLoginWorkerache")) {
                                String[] BBA = AA[i].split("&");
                                for(int ii = 0; ii<BBA.length;ii++) {
                                    if (BBA[ii].contains("ID=")&& !BBA[ii].contains("SID")&&!BBA[ii].contains("SuMurrentLoginWorkerache")) {
                                        UID = Integer.parseInt(BBA[ii].replaceAll("[^\\d.]", "").replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
                                        ii = BBA.length;
                                    }
                                }
                                i = AA.length;
                            }
                        }
                    }
                }
                if(UID>0) {
                    CookieManager.getInstance().flush();
                    SharedPreferences mPrefs = getSharedPreferences("summanga", 0);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("sumid", (UID + "").replaceAll("[^\\d.]", "").replace(" ", "")).apply();
                    view.setVisibility(View.GONE);
                    view.destroy();
                    findViewById(R.id.SuMSplashIMG).setVisibility(View.VISIBLE);
                    Intent i = new Intent(SplashScreen.this, SplashScreen.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
                    SplashScreen.this.finish();
                }
            }
            super.onPageFinished(view, url);
        }
    }
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void GetThisWenViewReady(WebView webViewx) {
        webViewx.setWebViewClient(new SplashScreen.yWebViewClient());
        WebSettings contentWebViewSettings = webViewx.getSettings();
        contentWebViewSettings.setJavaScriptEnabled(true);
        contentWebViewSettings.setUseWideViewPort(true);
        contentWebViewSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        contentWebViewSettings.setAllowFileAccessFromFileURLs(true);
        contentWebViewSettings.setAllowUniversalAccessFromFileURLs(true);
        webViewx.getSettings().setBuiltInZoomControls(false);
        contentWebViewSettings.setSupportZoom(false);
        contentWebViewSettings.setDisplayZoomControls(false);
        contentWebViewSettings.setAppCacheEnabled(true);
        webViewx.setBackgroundColor(Color.TRANSPARENT);
        webViewx.addJavascriptInterface(SplashScreen.this, "androidAPIs");
        webViewx.loadUrl("https://sum-manga.azurewebsites.net/AccountETC/LoginETC.aspx");
        webViewx.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void LoadDinoGame(){

        String url = "file:///android_asset/index.html";
        WebView DGWV = findViewById(R.id.SuMOffline_WebView_DinoGame);
        DGWV.getSettings().setJavaScriptEnabled(true);
        DGWV.setBackgroundColor(Color.TRANSPARENT);
        DGWV.loadUrl(url);

    }


}
