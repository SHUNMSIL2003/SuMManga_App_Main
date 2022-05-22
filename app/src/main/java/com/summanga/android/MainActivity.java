package com.summanga.android;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricPrompt;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {


    //Declare Recyclerview , Adapter and ArrayList
    private RecyclerView recyclerView;
    private ScoutAdapter adapter;
    private ArrayList<Scout> scoutArrayList;

    ViewFlipper simpleViewFlipper;

    public int LastBSearchView = 0;



    int GlobalCurrCoinsCount = 0;

    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 20.0f;

    public String UserNameFC = "";


    public String RootHexColor = "";
    public int RootStateBit = 0;

    private RewardedAd rewardedAd;


    WebView WebView0RecentsCard;
    WebView webView2;
    WebView webView3AccountSettingsCard;
    WebView webView4;
    WebView webView5;
    WebView webViewX_SECURE;

    int Device_Width=0;
    int Device_Height=0;


    public ScrollView SuMExplore_Home_ScrollView_Main_ELM;

    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;

    public String[] url;

    boolean SUMAuthIsDone = false;
    boolean SuMAuthResult = false;
    boolean SuMAuthUnderPross = false;
    private CancellationSignal cancellationSignal = null;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;


    @SuppressLint("SetJavaScriptEnabled")
    public void GetThisWenViewReady(WebView webViewx, boolean ReqPer, boolean ReqFileUpload,boolean CanLoadMoreLinks,boolean ZoomEnabled) {


        webViewx.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Users will be notified in case there's an error (i.e. no internet connection)
                Toast.makeText(MainActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }

            public void onPageFinished(WebView view, String url) {
                //CookieSyncManager.getInstance().sync();
                CookieManager.getInstance().flush();
            }
        });
        if (ReqFileUpload) {
            webViewx.setWebChromeClient(new WebChromeClient() {


                // For 3.0+ Devices (Start)
                // onActivityResult attached before constructor
                protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
                }


                // For Lollipop 5.0+ Devices
                public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                    if (uploadMessage != null) {
                        uploadMessage.onReceiveValue(null);
                        uploadMessage = null;
                    }

                    uploadMessage = filePathCallback;

                    Intent intent = null;
                    intent = fileChooserParams.createIntent();
                    try {
                        startActivityForResult(intent, REQUEST_SELECT_FILE);
                    } catch (ActivityNotFoundException e) {
                        uploadMessage = null;
                        return false;
                    }
                    return true;
                }

                //For Android 4.1 only
                protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                    mUploadMessage = uploadMsg;
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
                }

                protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
                }




            });
        }
        WebSettings contentWebViewSettings = webViewx.getSettings();
        contentWebViewSettings.setJavaScriptEnabled(true);
        webViewx.addJavascriptInterface(MainActivity.this, "androidAPIs");
        //contentWebViewSettings.setDomStorageEnabled(true);
        contentWebViewSettings.setUseWideViewPort(true);
        contentWebViewSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //contentWebViewSettings.setAllowFileAccess(true);
        contentWebViewSettings.setAllowFileAccessFromFileURLs(true);
        contentWebViewSettings.setAllowUniversalAccessFromFileURLs(true);
        //contentWebViewSettings.setAllowContentAccess(true);
        //webViewx.getSettings().setBuiltInZoomControls(true);
        if(ReqPer) {
            webViewx.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            webViewx.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        if(ZoomEnabled){
            webViewx.getSettings().setBuiltInZoomControls(true);
            contentWebViewSettings.setSupportZoom(true);
            webViewx.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            contentWebViewSettings.setDisplayZoomControls(true);
        }else {
            webViewx.getSettings().setBuiltInZoomControls(false);
            contentWebViewSettings.setSupportZoom(false);
            contentWebViewSettings.setDisplayZoomControls(false);
        }
        contentWebViewSettings.setAppCacheEnabled(true);
        webViewx.setBackgroundColor(Color.TRANSPARENT);
        webViewx.loadUrl("about:blank");
        SuMPauseXWebViews(new WebView[]{ webViewx });
        webViewx.stopLoading();
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public Handler SuMExploreScrollViewFunc_Handler = new Handler();
    public Runnable SuMExploreScrollViewFunc_Runnable;
    public int SuMExploreScrollViewFunc_scrollY = 0;


    @SuppressLint({"SetJavaScriptEnabled", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "SuM Manga needs storage permission to function", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
            MainActivity.this.finish();
            return;
        }
        //CookieSyncManager.createInstance(MainActivity.this);
        //CookieManager.getInstance().setAcceptCookie(true);
        //CookieSyncManager.getInstance().startSync();
        String LOADING_MESSAGE;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                LOADING_MESSAGE= null;
            } else {
                LOADING_MESSAGE= extras.getString("LOADING_MESSAGE");
            }
        } else {
            LOADING_MESSAGE= (String) savedInstanceState.getSerializable("LOADING_MESSAGE");
        }
        if(LOADING_MESSAGE == null){
            MainActivity.this.finish();
        } else {
            if(!LOADING_MESSAGE.equals("LOADED")) MainActivity.this.finish();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Device_Height = displayMetrics.heightPixels;
        Device_Width = displayMetrics.widthPixels;
        setContentView(R.layout.activity_main);
        String BANNER_STRING64;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                BANNER_STRING64= null;
            } else {
                BANNER_STRING64= extras.getString("BANNER_BITMAP_STRING64");
            }
        } else {
            BANNER_STRING64= (String) savedInstanceState.getSerializable("BANNER_BITMAP_STRING64");
        }
        if(BANNER_STRING64 == null){
            MainActivity.this.finish();
        } else {
            byte[] decodedString = Base64.decode(BANNER_STRING64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ((ImageView)findViewById(R.id.SuMBackIMG)).setImageBitmap(decodedByte);
        }
        updateLayout();
        simpleViewFlipper = (ViewFlipper) findViewById(R.id.simpleViewFlipper);
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in); // load an animation
        in.setStartOffset(540);
        in.setDuration(380);
        simpleViewFlipper.setInAnimation(in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out); // load an animation
        out.setStartOffset(0);
        out.setDuration(320);
        simpleViewFlipper.setOutAnimation(out);

        DarkSBIcons();

        /*webView0LatestCard = (WebView) findViewById(R.id.SuMWebView_NewestCardSlideView);
        webView0LatestCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView0LatestCard, false, false, false,false);*/
        WebView0RecentsCard = (WebView) findViewById(R.id.SuMWebView_ResentsCard);
        WebView0RecentsCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(WebView0RecentsCard, false, false, false,false);
        //webView0FlexibleGenreCard = (WebView) findViewById(R.id.SuMWebView_FlexibleGenre);
        //webView0FlexibleGenreCard.setVisibility(View.VISIBLE);
        //GetThisWenViewReady(webView0FlexibleGenreCard, false, false, false,false);
        webView4 = (WebView) findViewById(R.id.SuMWebViewIndex4);
        webView4.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView4, false, false, false,false);
        webView2 = (WebView) findViewById(R.id.SuMWebViewIndex2);
        webView2.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView2, false, false, false,false);
        webView3AccountSettingsCard = (WebView) findViewById(R.id.SuMWebViewIndex3AccountSettingsCard);
        webView3AccountSettingsCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView3AccountSettingsCard, false, true, true,false);
        webView5 = (WebView) findViewById(R.id.SuMWebViewIndex5);
        webView5.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView5, false, false, false,false);
        webViewX_SECURE = (WebView) findViewById(R.id.webViewX_SECURE_JS);
        webViewX_SECURE.setVisibility(View.GONE);
        GetThisWenViewReady(webViewX_SECURE, false, false, false,false);
        webViewX_SECURE.loadUrl("https://sum-manga.azurewebsites.net/storeitems/MangaExplorerCardHolder.aspx");


        Boolean FoundAnIndex = false;
        if (SuMStaticVs.RedirectFromSuMNotiURL == null) {
            LoadExplore(null);
        } else {
            if (SuMStaticVs.RedirectFromSuMNotiURL.toString() == "Hits") {
                LoadHit(null);
                FoundAnIndex = true;
            }
            if (SuMStaticVs.RedirectFromSuMNotiURL == "Library") {
                LoadLibrary(null);
                FoundAnIndex = true;
            }
            if (SuMStaticVs.RedirectFromSuMNotiURL == "Search") {
                LoadSearch(null);
                FoundAnIndex = true;
            }
            if (SuMStaticVs.RedirectFromSuMNotiURL == "Settings") {
                LoadSettings(null);
                FoundAnIndex = true;
            }
            if (!FoundAnIndex && SuMStaticVs.RedirectFromSuMNotiURL != null) {

            }
            if(!FoundAnIndex && SuMStaticVs.RedirectFromSuMNotiURL == null){
                LoadExplore(null);
            }
        }
        //createNotificationChannel();
        DarkSBIcons();

        /*Spinner spinner_SuMExplore_FlexibleGenreCard_GenreChooser=findViewById(R.id.SuMExplore_FlexibleGenreCard_GenreChooser);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.sumgenreflexmenu, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner_SuMExplore_FlexibleGenreCard_GenreChooser.setAdapter(adapter);*/


        int SBH00 = 0;
        int resourceId00 = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId00 > 0) {
            SBH00 = getResources().getDimensionPixelSize(resourceId00);
        }
        ViewGroup.LayoutParams params00 = findViewById(R.id.SuMStatusBar).getLayoutParams();
        params00.height = SBH00;

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

        RootHexColor = String.format("#%02X%02X%02X", Integer.parseInt(RS.split(",")[0]), Integer.parseInt(RS.split(",")[1]), Integer.parseInt(RS.split(",")[2]));

        String RSBit = "";
        if (cookies != null) {
            if (cookies.toString().contains("SuMUserThemeState=")) {
                String[] AA = cookies.toString().replace(" ", "").split(";");
                boolean foundAA = false;
                for (int i = 0; i < AA.length; i++) {
                    if (AA[i].contains("SuMUserThemeState=")) {
                        RSBit = AA[i].replace(" ", "").replace("SuMUserThemeState=", "").replace("'", "");
                        foundAA = true;
                        i = AA.length;
                    }
                }
                if (!foundAA) {
                    RSBit = "0";
                }
            } else RSBit = "0";
        } else RSBit = "0";

        String SecSelectColor = "#888888";
        if (RSBit.contains("1")) {
            findViewById(R.id.SuMBackCWA).setBackground(getDrawable(R.drawable.dark_gb));
            SecSelectColor = "#909090";
            findViewById(R.id.SuMWebNavColorShaper).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            RootStateBit = 1;
        } else {
            findViewById(R.id.SuMBackCWA).setBackground(getDrawable(R.drawable.light_bg));
            findViewById(R.id.SuMWebNavColorShaper).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            RootStateBit = 0;
        }
        findViewById(R.id.SuMBackCWA2).setBackgroundColor(Color.parseColor(RootHexColor));


        if (cookies != null) {
            if (cookies.toString().contains("UserName=")) {
                String[] AA = cookies.toString().replace(" ", "").split(";");
                boolean foundAA = false;
                for (int i = 0; i < AA.length; i++) {
                    if (AA[i].contains("UserName=")) {
                        String[] BBA = AA[i].split("&");
                        for (int ii = 0; ii < BBA.length; ii++) {
                            if (BBA[ii].contains("UserName=")) {
                                foundAA = true;
                                UserNameFC = BBA[ii].replace(" ", "").replace("UserName=", "").replace("SuMCurrentUser=", "");
                            }
                        }
                        foundAA = true;
                        i = AA.length;
                    }
                }
                if (!foundAA) {
                    UserNameFC = "Login is required";
                }
            } else UserNameFC = "Login is required";
        } else UserNameFC = "Login is required";

        String[] BB = RS.split(",");
        String hex = String.format("#%02X%02X%02X", Integer.parseInt(BB[0]), Integer.parseInt(BB[1]), Integer.parseInt(BB[2]));
        findViewById(R.id.SuMStatusBar).getBackground().setColorFilter(Color.parseColor(hex), PorterDuff.Mode.ADD);
        findViewById(R.id.SuMStatusBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
        findViewById(R.id.SuMNavBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
        findViewById(R.id.SuMNavBar).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
        findViewById(R.id.SuMUseNameTXT).getBackground().setColorFilter(Color.parseColor(hex), PorterDuff.Mode.ADD);


        TextView textvivesubt = (TextView) findViewById(R.id.SuMMangaTXT);
        textvivesubt.setTextColor(Color.parseColor(hex));
        textvivesubt = (TextView) findViewById(R.id.NavBackTXT);
        textvivesubt.setTextColor(Color.parseColor(hex));
        findViewById(R.id.SuMStatusBarExtendor).setVisibility(View.VISIBLE);
        findViewById(R.id.SuMNavBarExtendor).setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(0);
        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);
        getWindow().setNavigationBarDividerColor(Color.BLACK);
        final TextView textViewToChange = (TextView) findViewById(R.id.SuMUseNameTXT);
        textViewToChange.setText(UserNameFC);

        /*SuMExplore_Home_ScrollView_Main_ELM = ((ScrollView)findViewById(R.id.SuMExplore_Home_ScrollView_Main));
        SuMExploreScrollViewFunc_Runnable = new Runnable() {
            @Override
            public void run() {
                SuMExploreScrollViewFunc();
            }
        };*/

        /*SuMExplore_Home_ScrollView_Main_ELM.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SuMExploreScrollViewFunc_Handler.removeCallbacks(SuMExploreScrollViewFunc_Runnable);
                        SuMExploreScrollViewFunc_Handler.postDelayed(SuMExploreScrollViewFunc_Runnable, 100);
                    }
                });
            }

        });*/

        ((Switch)findViewById(R.id.SuMLockInOnOrOff)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences mPrefs = getSharedPreferences("summanga", 0);
                            SharedPreferences.Editor mEditor = mPrefs.edit();
                            mEditor.putString("sumlockbit", "1").apply();
                        }
                    });
                }
                else {
                    SharedPreferences mPrefs = getSharedPreferences("summanga", 0);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("sumlockbit", "0").apply();
                }
            }
        });

        ((Switch)findViewById(R.id.SuMDarkModeInOnOrOff)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences mPrefs = getSharedPreferences("summanga", 0);
                            SharedPreferences.Editor mEditor = mPrefs.edit();
                            mEditor.putString("sumthemebit", "1").apply();
                            //webView3AccountSettingsCard.loadUrl("javascript:SetSuMThemeBitStateIO(1);");
                            String cookieString = "SuMUserThemeState=1; path=/";
                            CookieManager.getInstance().setCookie("https://sum-manga.azurewebsites.net/", cookieString);
                        }
                    });
                }
                else {
                    SharedPreferences mPrefs = getSharedPreferences("summanga", 0);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("sumthemebit", "0").apply();
                    //webView3AccountSettingsCard.loadUrl("javascript:SetSuMThemeBitStateIO(0);");
                    String cookieString = "SuMUserThemeState=0; path=/";
                    CookieManager.getInstance().setCookie("https://sum-manga.azurewebsites.net/", cookieString);
                }
                LoadSettings(null);
                LoadSettings(null);
            }
        });
        initView();
        /*((Spinner)findViewById(R.id.SuMExplore_FlexibleGenreCard_GenreChooser)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String G_C = ((String)(((TextView)selectedItemView).getText())).replace(" ","").replace("-","");
                initView(G_C);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/

        MobileAds.initialize(this, initializationStatus -> {

        });
        LoadAdX();
        onRequestAd();


    }

    public boolean SuMExploreScrollViewFunc_Active = false;

    public void SuMExploreScrollViewFunc() {

        int scrollY = (int) convertPixelsToDp(SuMExplore_Home_ScrollView_Main_ELM.getScrollY(), MainActivity.this);
        ObjectAnimator xTranslate = ObjectAnimator.ofInt(SuMExplore_Home_ScrollView_Main_ELM, "scrollX", 0);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(SuMExplore_Home_ScrollView_Main_ELM, "scrollY", 0);
        AnimatorSet animators = new AnimatorSet();
        animators.setDuration(280L);
        animators.playTogether(xTranslate, yTranslate);
        animators.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub
            }
        });
        animators.start();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        SuMExploreScrollViewFunc_Active = false;
                    }
                },
                500);
    }

    public String SuMUltraCurrLink = "";
    public String SuMUltraCurrUrl = "";
    public String SuMUltraWannaJS = "";
    public String SuMUltraFavJS = "";

    public void VoidX(View view) {
        return;
    }
    public void VoidY(View view){

    }
    public void SuMPauseXWebViews(WebView[] WebViewsToFinish) {
        //CookieSyncManager.getInstance().stopSync();
        CookieManager.getInstance().flush();
        for (int i = 0; i < WebViewsToFinish.length; i++) {
            WebViewsToFinish[i].clearHistory();
            //WebViewsToFinish[i].destroyDrawingCache();
            //WebViewsToFinish[i].clearView();
            //WebViewsToFinish[i].removeAllViews();
            WebViewsToFinish[i].freeMemory();
            //WebViewsToFinish[i].pauseTimers();
            //WebViewsToFinish[i].stopLoading();
            WebViewsToFinish[i].onPause();
            //WebViewsToFinish[i].getSettings().setJavaScriptEnabled(false);
        }
    }
    public void SuMPauseXWebView(WebView WebViewsToFinish) {
        //CookieSyncManager.getInstance().stopSync();
        CookieManager.getInstance().flush();
        WebViewsToFinish.clearHistory();
        //WebViewsToFinish.destroyDrawingCache();
        //WebViewsToFinish.clearView();
        //WebViewsToFinish.removeAllViews();
        WebViewsToFinish.freeMemory();
        //WebViewsToFinish.pauseTimers();
        //WebViewsToFinish.stopLoading();
        WebViewsToFinish.onPause();
        //WebViewsToFinish.getSettings().setJavaScriptEnabled(false);
    }

    public void SuMResumeXWebViews(WebView[] WebViewsToFinish) {
        for (int i = 0; i < WebViewsToFinish.length; i++) {
            WebViewsToFinish[i].onResume();
            //WebViewsToFinish[i].reload();
            //WebViewsToFinish[i].resumeTimers();
            //WebViewsToFinish[i].getSettings().setJavaScriptEnabled(true);
        }
        //CookieSyncManager.getInstance().startSync();
    }
    public void SuMResumeXWebView(WebView WebViewsToFinish) {
        //WebViewsToFinish.resumeTimers();
        WebViewsToFinish.onResume();
        //WebViewsToFinish.getSettings().setJavaScriptEnabled(true);
        //WebViewsToFinish.reload();
        //CookieSyncManager.getInstance().startSync();
    }

    public void ShowExploreMangaInfoDisc(View view){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(320);
        findViewById(R.id.SuMMangaExploreInfo_MangaDiscABS).setVisibility(View.VISIBLE);
        findViewById(R.id.SuMMangaExploreInfo_MangaDiscABS).startAnimation(fadeIn);

    }
    public void HideMangaDisc(View view){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setStartOffset(0);
        fadeOut.setDuration(320);
        findViewById(R.id.SuMMangaExploreInfo_MangaDiscABS).startAnimation(fadeOut);
        final Handler handlerBlur = new Handler();
        handlerBlur.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.SuMMangaExploreInfo_MangaDiscABS).setVisibility(View.GONE);
            }
        }, 320);
    }
    public void SuMDarkModeToggle(View view){

    }
    public void SuMLockToggle(View view){

        if (((Switch)findViewById(R.id.SuMLockInOnOrOff)).isChecked()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((Switch)findViewById(R.id.SuMLockInOnOrOff)).setChecked(false);
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Was ON");
                    alertDialog.setMessage("Now OFF");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
        }
        else {
            ((Switch)findViewById(R.id.SuMLockInOnOrOff)).setChecked(true);
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Was OFF");
            alertDialog.setMessage("Now ON");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    public void More_Action(View view){

    }
    public void More_Drama(View view){

    }

    public void ClaimOneTokenByADS(View view){

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onShowRewardAd();
            }
        });

    }
    public void Claim8TokenByDolars(View view){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "will be available soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Claim18TokenByDolars(View view){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "will be available soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Claim124TokenByDolars(View view){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "will be available soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Claim1132TokenByDolars(View view){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "will be available soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void ClearCacheX(View view){

        //clearCache(MainActivity.this,0);
        //deleteDir(getCacheDir());
        //deleteDir(getExternalCacheDir());
        WebView[] WebViewsFoCache = new WebView[]{webView2,webView3AccountSettingsCard,webView4 };
        for (WebView webView : WebViewsFoCache) {
            webView.clearCache(true);
            webView.clearHistory();
        }
        MainActivity.this.deleteDatabase("webview.db");
        MainActivity.this.deleteDatabase("webviewCache.db");
        clearApplicationCache();
        for (WebView webView : WebViewsFoCache) {
            webView.clearCache(false);
        }
        webView4.clearFormData();
        findViewById(R.id.SuMClearCacheBTNBG).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c14dp), Color.parseColor("#8a8a8a")));
        ((TextView)findViewById(R.id.SuMSettingsTXT_CacheSizeValueTXT)).setText("0.00 Bytes");

    }
    private void clearApplicationCache() {
        File dir = getCacheDir();

        if (dir != null && dir.isDirectory()) {
            try {
                ArrayList<File> stack = new ArrayList<File>();

                // Initialise the list
                File[] children = dir.listFiles();
                for (File child : children) {
                    stack.add(child);
                }

                while (stack.size() > 0) {
                    File f = stack.get(stack.size() - 1);
                    if (f.isDirectory() == true) {
                        boolean empty = f.delete();

                        if (empty == false) {
                            File[] files = f.listFiles();
                            if (files.length != 0) {
                                for (File tmp : files) {
                                    stack.add(tmp);
                                }
                            }
                        } else {
                            stack.remove(stack.size() - 1);
                        }
                    } else {
                        f.delete();
                        stack.remove(stack.size() - 1);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
    //helper method for clearCache() , recursive
//returns number of deleted files
    static int clearCacheFolder(final File dir, final int numDays) {

        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {

                    //first delete subdirectories recursively
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }

                    //then delete the files and subdirectories in this dir
                    //only empty directories can be deleted, so subdirs have been done first
                    if (child.lastModified() < new Date().getTime() - numDays * DateUtils.DAY_IN_MILLIS) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            }
            catch(Exception e) {
            }
        }
        return deletedFiles;
    }

    /*
     * Delete the files older than numDays days from the application cache
     * 0 means all files.
     */
    public static void clearCache(final Context context, final int numDays) {
        int numDeletedFiles0 = clearCacheFolder(context.getCacheDir(), numDays);
        int numDeletedFiles1 = clearCacheFolder(context.getExternalCacheDir(), numDays);
    }

    public void ShareX(View view) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, SuMUltraCurrLink);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });
    }

    public void MoreX(View view) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                /*webView.loadUrl(SuMUltraCurrLink);
                simpleViewFlipper.setDisplayedChild(0);*/

            }
        });
    }

    public void ContinueX(View view) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                /*webView.loadUrl("javascript:" + SuMUltraCurrUrl);
                simpleViewFlipper.setDisplayedChild(0);*/

            }
        });
    }




    @SuppressLint("UseCompatLoadingForDrawables")
    public void LoadExplore(View view) {
        findViewById(R.id.SuMViewFilpperClickBlocker).setVisibility(View.GONE);
        if(simpleViewFlipper.getDisplayedChild()!=0) ((ScrollView)findViewById(R.id.SuMExplore_Home_ScrollView_Main)).scrollTo(0, 0);
        if(RootStateBit == 1){
            findViewById(R.id.SuMExplore_recentsCard_BG).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            //((TextView)findViewById(R.id.SuMExploreCard_GernCard_FlexibleGenre_TXT)).setTextColor(Color.WHITE);
            //((TextView)findViewById(R.id.SuMExploreCard_GernCard_FlexibleGenre_MoreTXT)).setTextColor(Color.WHITE);
            //findViewById(R.id.SuMExploreCard_GernCard_FlexibleGenre_MoreIMG).setBackground(setTint(getDrawable(R.drawable.ic_forword_black), Color.WHITE));
        }
        else {
            findViewById(R.id.SuMExplore_recentsCard_BG).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            //((TextView)findViewById(R.id.SuMExploreCard_GernCard_FlexibleGenre_TXT)).setTextColor(Color.BLACK);
            //((TextView)findViewById(R.id.SuMExploreCard_GernCard_FlexibleGenre_MoreTXT)).setTextColor(Color.BLACK);
            //findViewById(R.id.SuMExploreCard_GernCard_FlexibleGenre_MoreIMG).setBackground(setTint(getDrawable(R.drawable.ic_forword_black), Color.BLACK));
        }
        if(findViewById(R.id.SuMExploreInfo_ABS).getVisibility() == View.VISIBLE) {
            CloseSuMExploreInfo(null);
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
        if(UID>0){

            findViewById(R.id.SuMRecentsABSCard_ToHide).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.SuMRecentsABSCard_ToHide).setVisibility(View.GONE);
            Intent i = new Intent(MainActivity.this, SplashScreen.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
            finish();
        }
        LoadColors();
        findViewById(R.id.ExploreBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_dashboard_black_48dp), Color.parseColor(RootHexColor)));
        ((TextView)findViewById(R.id.ExploreBTNTXT)).setTextColor(Color.parseColor(RootHexColor));
        LastBSearchView = simpleViewFlipper.getDisplayedChild();
        ViewFlipper ViewFilpperCHelper = (ViewFlipper)findViewById(R.id.simpleViewFlipper);
        //if(ViewFilpperCHelper.getDisplayedChild() == 0 && !(webView0FlexibleGenreCard.getUrl()).contains("about:")) { return; }
        if(ViewFilpperCHelper.getDisplayedChild() == 5){
            ViewFilpperCHelper.setDisplayedChild(0);
            return;
        } else {
            LoadXView(
                    new String[]{"https://sum-manga.azurewebsites.net/ExploreRecentlyCard.aspx"/*,
                            "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Action"*/}
            );
        }
        //SuMPauseXWebView(webView0FlexibleGenreCard);
        /*RelativeLayout SuMExplore_FlexibleGenreCard_CardHeightElm = findViewById(R.id.SuMExplore_FlexibleGenreCard_CardHeightElm);
        double SuMExplore_FlexibleGenreCard_CardHeightElm_ReqNum = Math.floor((((double)convertPixelsToDp(Device_Width,MainActivity.this) - 24 -6) / (118+6)));
        SuMExplore_FlexibleGenreCard_CardHeightElm_ReqNum = Math.ceil(12/SuMExplore_FlexibleGenreCard_CardHeightElm_ReqNum);
        int HRS_FG = (int) convertDpToPixel((186+(int)(SuMExplore_FlexibleGenreCard_CardHeightElm_ReqNum*177)),MainActivity.this);
        ViewGroup.LayoutParams params = SuMExplore_FlexibleGenreCard_CardHeightElm.getLayoutParams();
        params.height = HRS_FG;
        SuMExplore_FlexibleGenreCard_CardHeightElm.setLayoutParams(params);*/
        SuMResumeXWebView(WebView0RecentsCard);
        ((ScrollView)findViewById(R.id.SuMExplore_Home_ScrollView_Main)).setVerticalScrollbarThumbDrawable(setTint(getDrawable(R.drawable.scrollbar_thum), Color.parseColor(RootHexColor)));
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void LoadHit(View view) {
        findViewById(R.id.SuMViewFilpperClickBlocker).setVisibility(View.GONE);
        if(findViewById(R.id.SuMExploreInfo_ABS).getVisibility() == View.VISIBLE) {
            CloseSuMExploreInfo(null);
        }
        LoadColors();
        findViewById(R.id.HitsBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_local_library_black_48dp), Color.parseColor(RootHexColor)));
        ((TextView)findViewById(R.id.HitsBTNTXT)).setTextColor(Color.parseColor(RootHexColor));
        LastBSearchView = simpleViewFlipper.getDisplayedChild();
        ViewFlipper ViewFilpperCHelper = (ViewFlipper)findViewById(R.id.simpleViewFlipper);
        if(ViewFilpperCHelper.getDisplayedChild() == 1) { return; }
    }
    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void LoadLibrary(View view) {
        findViewById(R.id.SuMViewFilpperClickBlocker).setVisibility(View.GONE);
        int UID = 0;
        Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");
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
        if(UID<1){
            Toast.makeText(MainActivity.this, "Login to access your library!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, SplashScreen.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
            finish();
            return;
        }
        if(findViewById(R.id.SuMExploreInfo_ABS).getVisibility() == View.VISIBLE) {
            CloseSuMExploreInfo(null);
        }
        LoadColors();
        findViewById(R.id.LibraryBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_bookmarks_black_48dp), Color.parseColor(RootHexColor)));
        ((TextView)findViewById(R.id.LibraryBTNTXT)).setTextColor(Color.parseColor(RootHexColor));
        LastBSearchView = simpleViewFlipper.getDisplayedChild();
        ViewFlipper ViewFilpperCHelper = (ViewFlipper)findViewById(R.id.simpleViewFlipper);
        if(ViewFilpperCHelper.getDisplayedChild() == 2) { return; }
        LoadXView(new String[]{"https://sum-manga.azurewebsites.net/UserLibrary.aspx?RT=Curr"});
        String SecColor = "#2b2b2b";
        TextView SuMDscInLyb = findViewById(R.id.SuMLibraryInfoTXT);
        if(RootStateBit==1) {
            SecColor = "#ffffff";
            findViewById(R.id.SuMWebViewIndex2BG).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            findViewById(R.id.SuMLibraryTopBarBG).setBackground(getDrawable(R.drawable.bg_dark_c25dp));
            findViewById(R.id.SuMLibraryInfoViewIMG).setBackground(setTint(getDrawable(R.drawable.ic_info_dark_36dp), Color.WHITE));
            SuMDscInLyb.setTextColor(Color.WHITE);
        } else {
            findViewById(R.id.SuMWebViewIndex2BG).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            findViewById(R.id.SuMLibraryTopBarBG).setBackground(getDrawable(R.drawable.bg_white_c25dp));
            findViewById(R.id.SuMLibraryInfoViewIMG).setBackground(setTint(getDrawable(R.drawable.ic_info_dark_36dp), Color.BLACK));
            SuMDscInLyb.setTextColor(Color.BLACK);
        }

        findViewById(R.id.LoadLibraryCurrBTN).setBackground(setTint(getResources().getDrawable(R.drawable.bg_x_x), Color.parseColor(RootHexColor)));
        findViewById(R.id.LoadLibraryWannaBTN).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.LoadLibraryFavBTN).setBackgroundColor(Color.TRANSPARENT);
        TextView TVWOI = (TextView)findViewById(R.id.LoadLibraryCurrTXT);
        TVWOI.setTextColor(Color.parseColor("#ffffff"));
        TVWOI = (TextView)findViewById(R.id.LoadLibraryFavTXT);
        TVWOI.setTextColor(Color.parseColor(SecColor));
        TVWOI = (TextView)findViewById(R.id.LoadLibraryWannaTXT);
        TVWOI.setTextColor(Color.parseColor(SecColor));
        SuMDscInLyb.setText("This library is to ease access to the mangas you are currently reading and track your process, you can add mangas by clicking the 'Start Reading' button in the desired manga page.");
    }
    @SuppressLint({"UseCompatLoadingForDrawables", "DefaultLocale"})
    public void LoadSettings(View view) {
        findViewById(R.id.SuMViewFilpperClickBlocker).setVisibility(View.GONE);
        if(simpleViewFlipper.getDisplayedChild()!=3) ((ScrollView)findViewById(R.id.SuMSettings_ScrollView_Main)).scrollTo(0, 0);
        if(RootStateBit == 1) {
            findViewById(R.id.SuMWebViewIndex3AccountSettingsCardBG).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            findViewById(R.id.SuMCoinnsABDCard_BG).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            findViewById(R.id.SuMSettingsCard_BG).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            ((TextView)findViewById(R.id.SuMCoinsCardTXT_SuMCoins)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinsCardTXT_ToUnlockANewWorld)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCard_CountTXT)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_OneCoin)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_OneCoinByADExtraInfo)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_8Coins)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_8CoinsByMonyInfo)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_18Coins)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_18CoinsByMonyInfo)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_124Coins)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_124CoinsByMonyInfo)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_1132Coins)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_1132CoinsByMonyInfo)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettings_SuMCoinsBuy_InfoTXT)).setTextColor(Color.WHITE);
            ((ImageView)findViewById(R.id.SuMSettings_SuMCoinsBuy_InfoIMG)).setImageResource(R.drawable.ic_info_white_36dp);
            ((TextView)findViewById(R.id.SuMSettingsCardTXT_SuMSteiings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettingsCardTXT_DoAsYouSeeFit)).setTextColor(Color.WHITE);
            findViewById(R.id.SuMLockIMG).setBackground(setTint(getDrawable(R.drawable.ic_lock_black_36dp), Color.WHITE));
            findViewById(R.id.SuMDrackModeIMG).setBackground(setTint(getDrawable(R.drawable.ic_dark_mode_black_36dp), Color.WHITE));
            ((TextView)findViewById(R.id.SuMLockTXT)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMLockDisc)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMDarkModeTXT)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMDarkModeDisc)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettingsTXT_CacheSizeValueTXT)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettingsTXT_CacheSizeTXT)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettingsTXT_SuMMoreInfo_ThisAppWebIsTemp)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettingsTXT_SuM__)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettingsTXT_WebVersionTXT)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettingsTXT_WebVersionValueTXT)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettingsTXT_ApplicationVersionTXT)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.SuMSettingsTXT_ApplicationVersionValue_AutoFilled)).setTextColor(Color.WHITE);
            findViewById(R.id.SuMSettingsHr0).setBackground(setTint(getDrawable(R.drawable.hr_black), Color.WHITE));
            findViewById(R.id.SuMSettingsHr1).setBackground(setTint(getDrawable(R.drawable.hr_black), Color.WHITE));
            findViewById(R.id.SuMSettingsHr2).setBackground(setTint(getDrawable(R.drawable.hr_black), Color.WHITE));

        } else {
            findViewById(R.id.SuMWebViewIndex3AccountSettingsCardBG).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            findViewById(R.id.SuMCoinnsABDCard_BG).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            findViewById(R.id.SuMSettingsCard_BG).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            ((TextView)findViewById(R.id.SuMCoinsCardTXT_SuMCoins)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinsCardTXT_ToUnlockANewWorld)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCard_CountTXT)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_OneCoin)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_OneCoinByADExtraInfo)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_8Coins)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_8CoinsByMonyInfo)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_18Coins)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_18CoinsByMonyInfo)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_124Coins)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_124CoinsByMonyInfo)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_1132Coins)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMCoinCardTXT_1132CoinsByMonyInfo)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettings_SuMCoinsBuy_InfoTXT)).setTextColor(Color.BLACK);
            ((ImageView)findViewById(R.id.SuMSettings_SuMCoinsBuy_InfoIMG)).setImageResource(R.drawable.ic_info_dark_36dp);
            ((TextView)findViewById(R.id.SuMSettingsCardTXT_SuMSteiings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettingsCardTXT_DoAsYouSeeFit)).setTextColor(Color.BLACK);
            findViewById(R.id.SuMLockIMG).setBackground(setTint(getDrawable(R.drawable.ic_lock_black_36dp), Color.BLACK));
            findViewById(R.id.SuMDrackModeIMG).setBackground(setTint(getDrawable(R.drawable.ic_dark_mode_black_36dp), Color.BLACK));
            ((TextView)findViewById(R.id.SuMLockTXT)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMLockDisc)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMDarkModeTXT)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMDarkModeDisc)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettingsTXT_CacheSizeValueTXT)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettingsTXT_CacheSizeTXT)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettingsTXT_SuMMoreInfo_ThisAppWebIsTemp)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettingsTXT_SuM__)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettingsTXT_WebVersionTXT)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettingsTXT_WebVersionValueTXT)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettingsTXT_ApplicationVersionTXT)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.SuMSettingsTXT_ApplicationVersionValue_AutoFilled)).setTextColor(Color.BLACK);
            findViewById(R.id.SuMSettingsHr0).setBackground(setTint(getDrawable(R.drawable.hr_black), Color.BLACK));
            findViewById(R.id.SuMSettingsHr1).setBackground(setTint(getDrawable(R.drawable.hr_black), Color.BLACK));
            findViewById(R.id.SuMSettingsHr2).setBackground(setTint(getDrawable(R.drawable.hr_black), Color.BLACK));
        }
        if(findViewById(R.id.SuMExploreInfo_ABS).getVisibility() == View.VISIBLE) {
            CloseSuMExploreInfo(null);
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
        if(UID>0){
            findViewById(R.id.SuMCoinnsABDCard_ToHide).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.SuMCoinnsABDCard_ToHide).setVisibility(View.GONE);
            Intent i = new Intent(MainActivity.this, SplashScreen.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
            finish();
        }
        LoadColors();
        findViewById(R.id.SettingsBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_settings_black_48dp), Color.parseColor(RootHexColor)));
        ((TextView)findViewById(R.id.SettingsBTNTXT)).setTextColor(Color.parseColor(RootHexColor));
        LastBSearchView = simpleViewFlipper.getDisplayedChild();
        ViewFlipper ViewFilpperCHelper = (ViewFlipper)findViewById(R.id.simpleViewFlipper);
        if(ViewFilpperCHelper.getDisplayedChild() == 3) { return; }
        if(UID>0) {
            LoadXView(new String[]{"https://sum-manga.azurewebsites.net/AccountETC/SettingsAccountCard.aspx"});
        }else {
            LoadXView(new String[]{"https://sum-manga.azurewebsites.net/AccountETC/LoginETC.aspx"});
        }
        findViewById(R.id.SuMSettingsCard_Icon).setBackground(setTint(getResources().getDrawable(R.drawable.ic_settings_black_48dp), Color.parseColor(RootHexColor)));
        findViewById(R.id.SuMCoinsCardBTN_ClaimOneByADBG).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c14dp), Color.parseColor(RootHexColor)));
        findViewById(R.id.SuMCoinsCardBTN_Claim8DolarByMony).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c14dp), Color.parseColor(RootHexColor)));
        findViewById(R.id.SuMCoinsCardBTN_Claim18DolarByMony).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c14dp), Color.parseColor(RootHexColor)));
        findViewById(R.id.SuMCoinsCardBTN_Claim124DolarByMony).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c14dp), Color.parseColor(RootHexColor)));
        findViewById(R.id.SuMCoinsCardBTN_Claim1132DolarByMony).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c14dp), Color.parseColor(RootHexColor)));
        findViewById(R.id.SuMClearCacheBTNBG).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c14dp), Color.parseColor(RootHexColor)));
        //((TextView)findViewById(R.id.SuMCoinCardTXT_OneCoin)).setTextColor(Color.parseColor(RootHexColor));
        ColorStateList thumbStates = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        Color.GRAY,
                        Color.parseColor(RootHexColor),
                        Color.DKGRAY
                }

        );
        SharedPreferences mPrefs = getSharedPreferences("summanga", 0);
        String mSuMlOCKBit = mPrefs.getString("sumlockbit", "0");
        if(mSuMlOCKBit.equals("1")) {
            ((Switch)findViewById(R.id.SuMLockInOnOrOff)).setChecked(true);
        }else {
            ((Switch)findViewById(R.id.SuMLockInOnOrOff)).setChecked(false);
        }
        String mThemeBit = mPrefs.getString("sumthemebit", "0");
        if(mThemeBit.equals("1")) {
            ((Switch)findViewById(R.id.SuMDarkModeInOnOrOff)).setChecked(true);
        }else {
            ((Switch)findViewById(R.id.SuMDarkModeInOnOrOff)).setChecked(false);
        }

        ((ScrollView)findViewById(R.id.SuMSettings_ScrollView_Main)).setVerticalScrollbarThumbDrawable(setTint(getDrawable(R.drawable.scrollbar_thum), Color.parseColor(RootHexColor)));

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch SuMLockToggle = (Switch)findViewById(R.id.SuMLockInOnOrOff);
        SuMLockToggle.setThumbTintList(thumbStates);
        ColorStateList trackStates = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled},
                        new int[]{}
                },
                new int[]{
                        Color.GRAY,
                        Color.LTGRAY
                }
        );
        SuMLockToggle.setTrackTintList(trackStates);
        SuMLockToggle.setTrackTintMode(PorterDuff.Mode.OVERLAY);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch SuMDarkModeToggle = (Switch)findViewById(R.id.SuMDarkModeInOnOrOff);
        SuMDarkModeToggle.setThumbTintList(thumbStates);
        SuMDarkModeToggle.setTrackTintList(trackStates);
        SuMDarkModeToggle.setTrackTintMode(PorterDuff.Mode.OVERLAY);
        long size = 0;
        size += getDirSize(this.getCacheDir());
        size += getDirSize(this.getExternalCacheDir());
        String cacheSizeValueTXT = "";

        double Size = (double) size;
        if(Size < 1000){
            cacheSizeValueTXT = size + "Bytes";
        }

        Size = Size / 1000;//KB
        if(Size < 1000){
            cacheSizeValueTXT = size + "KB";

        }
        Size = Size / 1000;//MB
        if(Size < 1000) {
            cacheSizeValueTXT = String.format("%.2f", Size) + "MB";
        }
        Size = Size / 1000;
        if(Size >= 1){
            cacheSizeValueTXT = String.format("%.2f", Size) + "GB";

        }
        ((TextView)findViewById(R.id.SuMSettingsTXT_CacheSizeValueTXT)).setText(cacheSizeValueTXT);
    }
    public long getDirSize(File dir){
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }
    public void LoadSearch(View view) {
        findViewById(R.id.SuMViewFilpperClickBlocker).setVisibility(View.GONE);
        if(findViewById(R.id.SuMExploreInfo_ABS).getVisibility() == View.VISIBLE) {
            CloseSuMExploreInfo(null);
        }
        LastBSearchView = simpleViewFlipper.getDisplayedChild();
        ViewFlipper ViewFilpperCHelper = (ViewFlipper)findViewById(R.id.simpleViewFlipper);
        if(ViewFilpperCHelper.getDisplayedChild() == 4) { return; }
        LoadXView(new String[]{"https://sum-manga.azurewebsites.net/Search.aspx"});
    }
    public void LoadExploreInfoPage(String ManagaURL) {
        LastBSearchView = simpleViewFlipper.getDisplayedChild();
        ViewFlipper ViewFilpperCHelper = (ViewFlipper)findViewById(R.id.simpleViewFlipper);
        if(ViewFilpperCHelper.getDisplayedChild() == 5) {
            if (webView5.getUrl().equals(ManagaURL)) {
                return;
            }
        }
        //LoadXView(new String[]{ManagaURL});
        webView5.loadUrl(ManagaURL);
    }

    public void GoBack(View view) {
        GoBackABS();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void LoadLibraryCurr(View view) {
        webView2.loadUrl("https://sum-manga.azurewebsites.net/UserLibrary.aspx?RT=Curr");
        String SecColor = "#2b2b2b";
        TextView SuMDscInLyb = findViewById(R.id.SuMLibraryInfoTXT);
        if(RootStateBit==1) {
            SecColor = "#ffffff";
            findViewById(R.id.SuMWebViewIndex2BG).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            findViewById(R.id.SuMLibraryTopBarBG).setBackground(getDrawable(R.drawable.bg_dark_c25dp));
            findViewById(R.id.SuMLibraryInfoViewIMG).setBackground(setTint(getDrawable(R.drawable.ic_info_dark_36dp), Color.WHITE));
            SuMDscInLyb.setTextColor(Color.WHITE);
        } else {
            findViewById(R.id.SuMWebViewIndex2BG).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            findViewById(R.id.SuMLibraryTopBarBG).setBackground(getDrawable(R.drawable.bg_white_c25dp));
            findViewById(R.id.SuMLibraryInfoViewIMG).setBackground(setTint(getDrawable(R.drawable.ic_info_dark_36dp), Color.BLACK));
            SuMDscInLyb.setTextColor(Color.BLACK);
        }
        findViewById(R.id.LoadLibraryCurrBTN).setBackground(setTint(getResources().getDrawable(R.drawable.bg_x_x), Color.parseColor(RootHexColor)));
        findViewById(R.id.LoadLibraryWannaBTN).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.LoadLibraryFavBTN).setBackgroundColor(Color.TRANSPARENT);
        TextView TVWOI = (TextView)findViewById(R.id.LoadLibraryCurrTXT);
        TVWOI.setTextColor(Color.parseColor("#ffffff"));
        TVWOI = (TextView)findViewById(R.id.LoadLibraryFavTXT);
        TVWOI.setTextColor(Color.parseColor(SecColor));
        TVWOI = (TextView)findViewById(R.id.LoadLibraryWannaTXT);
        TVWOI.setTextColor(Color.parseColor(SecColor));
        SuMDscInLyb.setText("This library is to ease access to the mangas you are currently reading and track your process, you can add mangas by clicking the 'Start Reading' button in the desired manga page.");
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void LoadLibraryFav(View view) {
        webView2.loadUrl("https://sum-manga.azurewebsites.net/UserLibrary.aspx?RT=Fav");
        String SecColor = "#2b2b2b";
        TextView SuMDscInLyb = findViewById(R.id.SuMLibraryInfoTXT);
        if(RootStateBit==1) {
            SecColor = "#ffffff";
            findViewById(R.id.SuMWebViewIndex2BG).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            findViewById(R.id.SuMLibraryTopBarBG).setBackground(getDrawable(R.drawable.bg_dark_c25dp));
            findViewById(R.id.SuMLibraryInfoViewIMG).setBackground(setTint(getDrawable(R.drawable.ic_info_dark_36dp), Color.WHITE));
            SuMDscInLyb.setTextColor(Color.WHITE);
        } else {
            findViewById(R.id.SuMWebViewIndex2BG).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            findViewById(R.id.SuMLibraryTopBarBG).setBackground(getDrawable(R.drawable.bg_white_c25dp));
            findViewById(R.id.SuMLibraryInfoViewIMG).setBackground(setTint(getDrawable(R.drawable.ic_info_dark_36dp), Color.BLACK));
            SuMDscInLyb.setTextColor(Color.BLACK);
        }
        findViewById(R.id.LoadLibraryFavBTN).setBackground(setTint(getResources().getDrawable(R.drawable.bg_x_x), Color.parseColor(RootHexColor)));
        findViewById(R.id.LoadLibraryWannaBTN).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.LoadLibraryCurrBTN).setBackgroundColor(Color.TRANSPARENT);
        TextView TVWOI = (TextView)findViewById(R.id.LoadLibraryFavTXT);
        TVWOI.setTextColor(Color.parseColor("#ffffff"));
        TVWOI = (TextView)findViewById(R.id.LoadLibraryCurrTXT);
        TVWOI.setTextColor(Color.parseColor(SecColor));
        TVWOI = (TextView)findViewById(R.id.LoadLibraryWannaTXT);
        TVWOI.setTextColor(Color.parseColor(SecColor));
        SuMDscInLyb.setText("This library is to ease access to your favorite mangas, you can add mangas by clicking the 'Heart' button  in the desired manga page.");
    }
    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void LoadLibraryWanna(View view) {
        String SecColor = "#2b2b2b";
        TextView SuMDscInLyb = findViewById(R.id.SuMLibraryInfoTXT);
        if(RootStateBit==1) {
            SecColor = "#ffffff";
            findViewById(R.id.SuMWebViewIndex2BG).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            findViewById(R.id.SuMLibraryTopBarBG).setBackground(getDrawable(R.drawable.bg_dark_c25dp));
            findViewById(R.id.SuMLibraryInfoViewIMG).setBackground(setTint(getDrawable(R.drawable.ic_info_dark_36dp), Color.WHITE));
            SuMDscInLyb.setTextColor(Color.WHITE);
        } else {
            findViewById(R.id.SuMWebViewIndex2BG).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            findViewById(R.id.SuMLibraryTopBarBG).setBackground(getDrawable(R.drawable.bg_white_c25dp));
            findViewById(R.id.SuMLibraryInfoViewIMG).setBackground(setTint(getDrawable(R.drawable.ic_info_dark_36dp), Color.BLACK));
            SuMDscInLyb.setTextColor(Color.BLACK);
        }
        webView2.loadUrl("https://sum-manga.azurewebsites.net/UserLibrary.aspx?RT=Wanna");
        findViewById(R.id.LoadLibraryWannaBTN).setBackground(setTint(getResources().getDrawable(R.drawable.bg_x_x), Color.parseColor(RootHexColor)));
        findViewById(R.id.LoadLibraryFavBTN).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.LoadLibraryCurrBTN).setBackgroundColor(Color.TRANSPARENT);
        TextView TVWOI = (TextView)findViewById(R.id.LoadLibraryWannaTXT);
        TVWOI.setTextColor(Color.parseColor("#ffffff"));
        TVWOI = (TextView)findViewById(R.id.LoadLibraryCurrTXT);
        TVWOI.setTextColor(Color.parseColor(SecColor));
        TVWOI = (TextView)findViewById(R.id.LoadLibraryFavTXT);
        TVWOI.setTextColor(Color.parseColor(SecColor));
        SuMDscInLyb.setText("This library is to access mangas that peaked your interest, you can add mangas by clicking the 'Plus' button in the desired manga page.");
    }

    public void SuMBTActTopNBottomVoid(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                int NBH = 1;
                Resources resources = MainActivity.this.getResources();
                int resourceId0 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId0 > 0) {
                    NBH = resources.getDimensionPixelSize(resourceId0);
                }
                int SBH = 1;
                int resourceId1 = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId1 > 0) {
                    SBH = getResources().getDimensionPixelSize(resourceId1);
                }
                if(SBH<1){SBH = 1;}
                if(NBH<1){NBH = 1;}
                ViewGroup.LayoutParams params0 = findViewById(R.id.SuMStatusBar).getLayoutParams();
                params0.height = SBH;
                findViewById(R.id.SuMStatusBar).setLayoutParams(params0);
                ViewGroup.LayoutParams params1 = findViewById(R.id.SuMNavBar).getLayoutParams();
                params1.height = NBH;
                findViewById(R.id.SuMNavBar).setLayoutParams(params1);

                findViewById(R.id.SuMStatusBar).setVisibility(View.VISIBLE);
                findViewById(R.id.SuMNavBar).setVisibility(View.VISIBLE);

            }
        });
    }
    public void SuMBTActNormalVoid(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                int NBH = 1;
                Resources resources = MainActivity.this.getResources();
                int resourceId0 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId0 > 0) {
                    NBH = resources.getDimensionPixelSize(resourceId0);
                }
                int SBH = 1;
                int resourceId1 = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId1 > 0) {
                    SBH = getResources().getDimensionPixelSize(resourceId1);
                }
                if(SBH<1){SBH = 1;}
                if(NBH<1){NBH = 1;}
                ViewGroup.LayoutParams params0 = findViewById(R.id.SuMStatusBar).getLayoutParams();
                params0.height = SBH;
                findViewById(R.id.SuMStatusBar).setLayoutParams(params0);
                ViewGroup.LayoutParams params1 = findViewById(R.id.SuMNavBar).getLayoutParams();
                params1.height = NBH;
                findViewById(R.id.SuMNavBar).setLayoutParams(params1);
                findViewById(R.id.SuMStatusBar).setVisibility(View.VISIBLE);
                findViewById(R.id.SuMNavBar).setVisibility(View.VISIBLE);

            }
        });
    }
    public void destroyWebViews(WebView[] WebViewsToFinish) {

        for (int i = 0; i < WebViewsToFinish.length; i++ ) {
            //WebViewsToFinish[i].loadUrl("about:blank");
            WebViewsToFinish[i].clearHistory();
            WebViewsToFinish[i].destroyDrawingCache();
            //WebViewsToFinish[i].clearView();
            //WebViewsToFinish[i].removeAllViews();
            WebViewsToFinish[i].onPause();
            //WebViewsToFinish[i].destroy(); breaks it!
        }
    }
    public void restoreWebViews(WebView[] WebViewsToFinish) {

        for (int i = 0; i < WebViewsToFinish.length; i++ ) {
            WebViewsToFinish[i].onResume();
        }
    }
    public boolean SuMSettingsLoadded = false;
    public boolean SuMLibraryLoadded = false;
    public boolean SuMHitLoadded = false;
    public boolean SuMExploreLoadded = false;
    public boolean SuMSearchLoadded = false;
    @SuppressLint("UseCompatLoadingForDrawables")
    public void LoadXView(String[] xurl) {
        int CurrIndexPTM = ((ViewFlipper)findViewById(R.id.simpleViewFlipper)).getDisplayedChild();
        WebView[] WebViewToDistroy = new WebView[]{
                WebView0RecentsCard};
        if(CurrIndexPTM == 2){
            WebViewToDistroy = new WebView[]{webView2};
        }
        if(CurrIndexPTM == 3){
            WebViewToDistroy = new WebView[]{webView3AccountSettingsCard};
        }
        if(CurrIndexPTM == 4){
            WebViewToDistroy = new WebView[]{webView4};
        }
        WebView[] webViewX = new WebView[]{
                WebView0RecentsCard};
        int IndexX = 0;
        if(xurl[0].contains("/Explore")){

        }
        if(xurl[0].contains("/MangaExplorer")) {
            SuMBTActTopNBottomVoid();
        } else {
            SuMBTActNormalVoid();
        }
        if(xurl[0].contains("/UserLibrary.aspx")) {
            IndexX = 2;
            webViewX = new WebView[]{ webView2 };
        }
        if(xurl[0].contains("/AccountETC/")) {
            IndexX = 3;
            webViewX = new WebView[]{ webView3AccountSettingsCard };
        }
        if(xurl[0].contains("/Search.aspx")) {
            IndexX = 4;
            webViewX = new WebView[]{ webView4 };;
            SuMBTActTopNBottomVoid();
        }
        SuMPauseXWebViews(WebViewToDistroy);
        SuMResumeXWebViews(webViewX);

        for (int i = 0; i < xurl.length; i++) {
            if (!webViewX[i].getUrl().equals(xurl[i])) {
                webViewX[i].loadUrl(xurl[i]);
            }
        }

        simpleViewFlipper.setDisplayedChild(IndexX);

        if(xurl[0].contains("/MangaExplorer.aspx")) {
            findViewById(R.id.SuMUseNameTXT).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.SuMUseNameTXT).setVisibility(View.VISIBLE);
        }
        if(xurl[0].contains("/Search.aspx")) {
            findViewById(R.id.SuMUseNameTXT).setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void LoadColors() {
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

        RootHexColor = String.format("#%02X%02X%02X", Integer.parseInt(RS.split(",")[0]), Integer.parseInt(RS.split(",")[1]), Integer.parseInt(RS.split(",")[2]));

        String RSBit = "";
        if (cookies != null) {
            if (cookies.toString().contains("SuMUserThemeState=")) {
                String[] AA = cookies.toString().replace(" ", "").split(";");
                boolean foundAA = false;
                for (int i = 0; i < AA.length; i++) {
                    if (AA[i].contains("SuMUserThemeState=")) {
                        RSBit = AA[i].replace(" ", "").replace("SuMUserThemeState=", "").replace("'", "");
                        foundAA = true;
                        i = AA.length;
                    }
                }
                if (!foundAA) {
                    RSBit = "0";
                }
            } else RSBit = "0";
        } else RSBit = "0";

        String SecSelectColor = "#888888";
        if (RSBit.contains("1")) {
            findViewById(R.id.SuMBackCWA).setBackground(getDrawable(R.drawable.dark_gb));
            SecSelectColor = "#909090";
            findViewById(R.id.SuMWebNavColorShaper).setBackground(getDrawable(R.drawable.gb_dark_c22dp));
            RootStateBit = 1;
        } else {
            findViewById(R.id.SuMBackCWA).setBackground(getDrawable(R.drawable.light_bg));
            findViewById(R.id.SuMWebNavColorShaper).setBackground(getDrawable(R.drawable.bg_white_c22dp));
            RootStateBit = 0;
        }

        String[] BB = RS.split(",");
        String hex = String.format("#%02X%02X%02X", Integer.parseInt(BB[0]), Integer.parseInt(BB[1]), Integer.parseInt(BB[2]));
        findViewById(R.id.SuMStatusBar).getBackground().setColorFilter(Color.parseColor(hex), PorterDuff.Mode.ADD);
        findViewById(R.id.SuMStatusBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
        findViewById(R.id.SuMNavBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
        findViewById(R.id.SuMNavBar).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
        findViewById(R.id.SuMUseNameTXT).getBackground().setColorFilter(Color.parseColor(hex), PorterDuff.Mode.ADD);

        TextView textvivesubt = (TextView) findViewById(R.id.SuMMangaTXT);
        textvivesubt.setTextColor(Color.parseColor(hex));
        textvivesubt = (TextView) findViewById(R.id.NavBackTXT);
        textvivesubt.setTextColor(Color.parseColor(hex));

        textvivesubt = (TextView) findViewById(R.id.ExploreBTNTXT);
        findViewById(R.id.ExploreBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_dashboard_black_48dp), Color.parseColor(SecSelectColor)));
        textvivesubt.setTextColor(Color.parseColor(SecSelectColor));

        findViewById(R.id.LibraryBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_bookmarks_black_48dp), Color.parseColor(SecSelectColor)));
        textvivesubt = (TextView) findViewById(R.id.LibraryBTNTXT);
        textvivesubt.setTextColor(Color.parseColor(SecSelectColor));

        findViewById(R.id.HitsBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_local_library_black_48dp), Color.parseColor(SecSelectColor)));
        textvivesubt = (TextView) findViewById(R.id.HitsBTNTXT);
        textvivesubt.setTextColor(Color.parseColor(SecSelectColor));

        findViewById(R.id.SettingsBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_settings_black_48dp), Color.parseColor(SecSelectColor)));
        textvivesubt = (TextView) findViewById(R.id.SettingsBTNTXT);
        textvivesubt.setTextColor(Color.parseColor(SecSelectColor));

        textvivesubt = (TextView) findViewById(R.id.SuMMangaTXT);
        textvivesubt.setTextColor(Color.parseColor(hex));
        textvivesubt = (TextView) findViewById(R.id.NavBackTXT);
        textvivesubt.setTextColor(Color.parseColor(hex));

        findViewById(R.id.SuMStatusBarExtendor).setVisibility(View.VISIBLE);
        findViewById(R.id.SuMNavBarExtendor).setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(0);
        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);
        getWindow().setNavigationBarDividerColor(Color.BLACK);
    }

    public void GoBackABS(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                onKeyDown(KeyEvent.KEYCODE_BACK,null);

            }
        });
    }

    public static Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
    public Drawable getColoredDrawable(@DrawableRes int resid, int color){
        Drawable drawable= ContextCompat.getDrawable(MainActivity.this, resid).mutate();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }


    public static int getDominantColor1(Bitmap bitmap) {

        if (bitmap == null)
            throw new NullPointerException();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = width * height;
        int pixels[] = new int[size];

        Bitmap bitmap2 = bitmap.copy(Bitmap.Config.ARGB_4444, false);

        bitmap2.getPixels(pixels, 0, width, 0, 0, width, height);

        final List<HashMap<Integer, Integer>> colorMap = new ArrayList<HashMap<Integer, Integer>>();
        colorMap.add(new HashMap<Integer, Integer>());
        colorMap.add(new HashMap<Integer, Integer>());
        colorMap.add(new HashMap<Integer, Integer>());

        int color = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        Integer rC, gC, bC;
        for (int i = 0; i < pixels.length; i++) {
            color = pixels[i];

            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            rC = colorMap.get(0).get(r);
            if (rC == null)
                rC = 0;
            colorMap.get(0).put(r, ++rC);

            gC = colorMap.get(1).get(g);
            if (gC == null)
                gC = 0;
            colorMap.get(1).put(g, ++gC);

            bC = colorMap.get(2).get(b);
            if (bC == null)
                bC = 0;
            colorMap.get(2).put(b, ++bC);
        }

        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            int max = 0;
            int val = 0;
            for (Map.Entry<Integer, Integer> entry : colorMap.get(i).entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    val = entry.getKey();
                }
            }
            rgb[i] = val;
        }

        int dominantColor = Color.rgb(rgb[0], rgb[1], rgb[2]);

        return dominantColor;
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
        /*Bitmap bm = outputBitmap;

        Canvas canvas = new Canvas(bm);
        canvas.drawARGB(80,0,0,0);
        canvas.drawBitmap(bm, new Matrix(), new Paint());
        return bm;*/

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
        canvas.drawARGB(192,r,g,b);
        canvas.drawBitmap(bm, new Matrix(), new Paint());
        return bm;

    }



    private class yWebViewClient extends WebViewClient {

        /*@SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("sum-manga.azurewebsites.net")) {

                LoadXView(url);
                // This is my web site, so do not override; let my WebView load the page
                return true;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }*/
    }


    public void DarkSBIcons(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                //int flags = getWindow().getDecorView().getSystemUiVisibility();
                //flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                //getWindow().getDecorView().setSystemUiVisibility(flags);
                //getWindow().setStatusBarColor(Color.WHITE);
            }

        });
    }



    public void LoadAdX(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                /*AdRequest adRequest = new AdRequest.Builder().build();


                RewardedAd.load(MainActivity.this, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error.
                                Log.d(TAG, loadAdError.getMessage());
                                mRewardedAd = null;

                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                mRewardedAd = rewardedAd;


                                mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when ad is shown.
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when ad fails to show.
                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when ad is dismissed.
                                        // Set the ad reference to null so you don't show the ad a second time.
                                        //mRewardedAd = null;
                                    }
                                });


                            }
                        });*/

            }

        });
    }
    /*public void getNotification () {


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.setData((Uri.parse("custom://"+System.currentTimeMillis())));

        alarmManager.cancel(pendingIntent);

        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 55);
        calendar.set(Calendar.SECOND, 00);
        if (now.after(calendar)) {
            Log.d("Hey","Added a day");
            calendar.add(Calendar.DATE, 0);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }*/

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SuM Manga";
            String description = "reader haven -SuM Manga";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("SuM Manga", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        //CookieSyncManager.getInstance().sync();
        CookieManager.getInstance().flush();
        WebView0RecentsCard.saveState(outState);
        //webView0FlexibleGenreCard.saveState(outState);
        webView2.saveState(outState);
        webView4.saveState(outState);
        webView5.saveState(outState);
        webView3AccountSettingsCard.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        WebView0RecentsCard.restoreState(savedInstanceState);
        //webView0FlexibleGenreCard.restoreState(savedInstanceState);
        webView2.restoreState(savedInstanceState);
        webView4.restoreState(savedInstanceState);
        webView5.restoreState(savedInstanceState);
        webView3AccountSettingsCard.restoreState(savedInstanceState);
    }

    public void updateLayout(){

        // Display content under the status bar
        View appView = getWindow().getDecorView();
        appView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }

    public void showWebView(){

        // Change the visibility of the WebView
        //webView.setVisibility(View.VISIBLE);
        //SplashScreen.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }


    }


    private class xWebViewClient extends WebViewClient {


        /*@SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("sum-manga.azurewebsites.net")) {

                LoadXView(url);
                // This is my web site, so do not override; let my WebView load the page
                return true;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }*/
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(simpleViewFlipper.getDisplayedChild() == 4){
                //if(LastBSearchView==0) LoadExplore(null);
                //if(LastBSearchView==1) LoadHit(null);
                //if(LastBSearchView==2) LoadLibrary(null);
                //if(LastBSearchView==3) LoadSettings(null);
                //if(LastBSearchView>3) LoadExplore(null);
                simpleViewFlipper.setDisplayedChild(LastBSearchView);
                return false;
            }
            if (findViewById(R.id.SuMExploreInfo_ABS).getVisibility() != View.VISIBLE) {
                if (simpleViewFlipper.getDisplayedChild() != 0) {
                    LoadExplore(null);
                    return false;
                }
            } else {
                CloseSuMExploreInfo(null);
                //LoadExplore(null);
                return false;
            }
        }
        if(event!=null) {
            return super.onKeyDown(keyCode, event);
        }
        return false;
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
                        MainActivity.this.finish();
                    }
                });
        return cancellationSignal;
    }

    public void SUMAppLock()
    {
        // This creates a dialog of biometric
        // auth and it requires title , subtitle
        // , and description In our case there
        // is a cancel button by clicking it, it
        // will cancel the process of
        // fingerprint authentication

        SuMAuthUnderPross = true;

        BiometricPrompt biometricPrompt = new BiometricPrompt
                .Builder(getApplicationContext())
                .setTitle("SUM Verification!")
                .setSubtitle("verify your identity")
                .setDescription("after all we care about your SUM world")
                .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void
                    onClick(DialogInterface dialogInterface, int i)
                    {
                        notifyUser("Authentication Cancelled");
                        MainActivity.this.finish();
                    }
                }).build();


        // start the authenticationCallback in
        // mainExecutor
        biometricPrompt.authenticate(
                getCancellationSignal(),
                getMainExecutor(),
                authenticationCallback);


        //recreate();
    }


    private Boolean checkBiometricSupport()
    {
        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            notifyUser("Fingerprint authentication has not been enabled in settings - clear app data to reset sumlock");
            MainActivity.this.finish();
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled");
            MainActivity.this.finish();
            //SplashScreen.setVisibility(View.VISIBLE);
            return false;
        }
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true;
        }
        else
            return true;
    }

    @JavascriptInterface
    public double getStatusBarHeight() {

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        statusBarHeight = statusBarHeight / 3;
        return statusBarHeight;

    }

    //New Bars heights get func
    @JavascriptInterface
    public int GetStatusBarHeightV2(){
        // status bar height
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        statusBarHeight = statusBarHeight / 3;
        return statusBarHeight;
    }

    @JavascriptInterface
    public void SemiTranStatusBar() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                getWindow().getDecorView().setSystemUiVisibility(0);
                //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            }

        });
    }
    @JavascriptInterface
    public void FullyTransStatusBar() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

            }

        });
    }


    // A function that tells the website if the system is using dark mode
    @JavascriptInterface
    public boolean isUsingDarkMode(){

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;

    }

    // A function that the website can use to tell the app that it's finished loading
    @JavascriptInterface
    public void layoutDoneLoading(){

        // android.layoutDoneLoading();

        /*DarkSBIcons();

        // Show the WebView
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (!SlpashSuMGone) {

                    int SBH = 0;
                    int resourceId1 = getResources().getIdentifier("status_bar_height", "dimen", "android");
                    if (resourceId1 > 0) {
                        SBH = getResources().getDimensionPixelSize(resourceId1);
                    }

                    int NBH = 0;
                    Resources resources = MainActivity.this.getResources();
                    int resourceId0 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                    if (resourceId0 > 0) {
                        NBH = resources.getDimensionPixelSize(resourceId0);
                    }
                    ViewGroup.LayoutParams params0 = findViewById(R.id.SuMStatusBar).getLayoutParams();
                    params0.height = SBH;
                    findViewById(R.id.SuMStatusBar).setLayoutParams(params0);
                    ViewGroup.LayoutParams params1 = findViewById(R.id.SuMNavBar).getLayoutParams();
                    params1.height = NBH;
                    findViewById(R.id.SuMNavBar).setLayoutParams(params1);

                    findViewById(R.id.SuMStatusBar).setVisibility(View.VISIBLE);
                    findViewById(R.id.SuMNavBar).setVisibility(View.VISIBLE);

                    Animation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setStartOffset(200);
                    fadeOut.setDuration(320);
                    SplashScreen.startAnimation(fadeOut);
                    final Handler handler0 = new Handler();
                    handler0.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SplashScreen.setVisibility(View.INVISIBLE);
                            webView.setVisibility(View.VISIBLE);
                            Animation fadeIn = new AlphaAnimation(0, 1);
                            fadeIn.setDuration(460);
                            webView.setAnimation(fadeIn);
                            SplashScreen.setVisibility(View.GONE);
                            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                            SplashScreen.setImageResource(0);
                            SplashScreen.setImageDrawable(null);
                            SlpashSuMGone = true;
                            DarkSBIcons();
                            findViewById(R.id.SuMBottomWebNavBarABS).setVisibility(View.VISIBLE);
                            findViewById(R.id.SuMBottomWebNavBarABS).startAnimation(fadeIn);

                        }
                    }, 520);
                }
                DarkSBIcons();

            }

        });*/

    }
    //To prevent screenshots in non sum tabs
    @JavascriptInterface
    public void SetSuMSecureFlag() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

            }

        });
    }
    @JavascriptInterface
    public void SuM0Manager() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);

            }

        });
    }
    //To vibrate
    @SuppressLint("ObsoleteSdkInt")
    @JavascriptInterface
    public void VIBRATEPhone(){

        // androidAPIs.VIBRATEPhone();

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(90, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(90);
        }

    }
    //Make Status Bar Icons Dark
    @JavascriptInterface
    public void SetLightStatusBarColor() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                int flags = getWindow().getDecorView().getSystemUiVisibility();
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                getWindow().getDecorView().setSystemUiVisibility(flags);
                getWindow().setStatusBarColor(Color.WHITE);*/
            }

        });
    }
    @JavascriptInterface
    public void SetDarkStatusBarColor() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                getWindow().getDecorView().setSystemUiVisibility(0);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);*/
            }

        });
    }
    //Full Screen Mode settings
    @JavascriptInterface
    public void ActivateFullScreenMode(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {


                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                |View.SYSTEM_UI_FLAG_FULLSCREEN
                                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

                );

            }

        });
    }
    @JavascriptInterface
    public void DeactivateFullScreenMode(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
                getWindow().getDecorView().setSystemUiVisibility(0);
                getWindow().setStatusBarColor(Color.BLACK);
                getWindow().setNavigationBarColor(Color.BLACK);
                getWindow().setNavigationBarDividerColor(Color.BLACK);

            }

        });
    }
    //Share Links
    @JavascriptInterface
    public void ShareThisPage() {
        //final String appPackageName = context.getPackageName();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //String webUrl = webView5.getUrl().replace("ContantExplorerCard.aspx","ContantExplorer.aspx");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out: " + "Disabled");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }

        });
    }


    @JavascriptInterface
    public void SuMShareThisLink(String SuMLink) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (SuMLink != null) {

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out: " + SuMLink.toString());
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

                }

            }

        });

    }


    @JavascriptInterface
    public void DeleteSuMCache(){

        deleteCache(this);

    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    //Detect if fullscreen
    @JavascriptInterface
    public boolean SuMIsFullScreen(){
        int flg = getWindow().getAttributes().flags;
        boolean flag = false;
        if ((flg & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            flag = true;
        }
        return flag;
    }
    //get navigation bar height
    @JavascriptInterface
    public int SuMGetNavigationBarHeight(){
        int navigationBarHeight = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        navigationBarHeight = navigationBarHeight / 3;
        return navigationBarHeight;
    }
    //Show a text in a bubble
    @JavascriptInterface
    public void ShowSuMToastsOverview(String TXT) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Context context = getApplicationContext();
                CharSequence text = TXT;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        });
    }

    @JavascriptInterface
    public void ShowSuMToastsOverviewWV3(String TXT,int UID) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Context context = getApplicationContext();
                CharSequence text = TXT;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //webView3AccountSettingsCard.loadUrl("javascript:SuMCoinsCount("+UID+")");
            }

        });
    }

    @JavascriptInterface
    public void SuMRestart(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finishAffinity();
    }

    @JavascriptInterface
    public void SuMRestartV2(){
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
        //startActivity(getIntent());
        //overridePendingTransition(0, 0);
    }

    @JavascriptInterface
    public void SuMRestartTPageX(String RelPath){
        SuMStaticVs.RedirectFromSuMNotiURL = "http://sum-manga.azurewebsites.net"+RelPath;
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finishAffinity();
    }


    @JavascriptInterface
    public void SetSuMHardwareExl() {
        /*runOnUiThread(new Runnable() {

            @Override
            public void run() {
                webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                if (Build.VERSION.SDK_INT >= 19) {
                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }
                else {
                    webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }

        });*/
    }

    @JavascriptInterface
    public void SuMPushThis(String SuMSenderInfo,String SuMContent,String SuMRedirectRelativeURL) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Intent intentNot = new Intent(MainActivity.this, MainActivity.class);
                intentNot.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intentNot, PendingIntent.FLAG_IMMUTABLE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "SuM Manga")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(SuMSenderInfo)
                        .setContentText(SuMContent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(1453453245, builder.build());
            }

        });

        SuMStaticVs.RedirectFromSuMNotiURL = "https://sum-manga.azurewebsites.net/" + SuMRedirectRelativeURL;
    }

    @JavascriptInterface
    public boolean SUMAuthProssIsDone()
    {
        return SUMAuthIsDone;
    }

    @JavascriptInterface
    public boolean SUMAuthProssResult(){
        return SuMAuthResult;
    }

    @JavascriptInterface
    public boolean SUMAuthIsUnderPross(){
        return SuMAuthUnderPross;
    }

    @JavascriptInterface
    public void SUMAuthReset(){
        SUMAuthIsDone = false;
        SuMAuthResult = false;
        SuMAuthUnderPross= false;
    }

    @JavascriptInterface
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void SUMVerification() {
        // This creates a dialog of biometric
        // auth and it requires title , subtitle
        // , and description In our case there
        // is a cancel button by clicking it, it
        // will cancel the process of
        // fingerprint authentication
        SuMAuthUnderPross = true;

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
                        MainActivity.this.finish();
                    }
                }).build();


        // start the authenticationCallback in
        // mainExecutor
        biometricPrompt.authenticate(
                getCancellationSignal(),
                getMainExecutor(),
                authenticationCallback);


        //recreate();
    }

    //show adx
    @JavascriptInterface
    public void ShowXAD() {
        //RewardItem ReqR=new RewardItem();
        SuMStaticVs.ADRewaredEarned = false;
        //final String appPackageName = context.getPackageName();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {



            }

        });

    }

    @JavascriptInterface
    public boolean SuMAdStateReward(){
        if (SuMStaticVs.ADRewaredEarned){
            SuMStaticVs.ADRewaredEarned = false;
            return true;
        }
        else { return false; }
    }

    @JavascriptInterface
    public boolean SuMAdProssState(){
        if (SuMStaticVs.ADProssEnded){
            SuMStaticVs.ADProssEnded = false;
            return true;
        }
        else { return false; }
    }

    @JavascriptInterface
    public boolean AnADIsReady(){

        /*if (mRewardedAd == null){
            return false;
        }
        else {
            return true;
        }*/
        return false;

    }

    @JavascriptInterface
    public void SuMGetAnADReady(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                LoadAdX();

            }
        });
    }

    @JavascriptInterface
    public void SuMADStart(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                onShowRewardAd();

                webView3AccountSettingsCard.loadUrl("javascript:document.getElementById('SuMOneCoinBTN').click()");

            }
        });
    }


    public void LoadAD(View view){



    }

    void onRequestAd()
    {
        rewardedAd = new RewardedAd(this,"ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback =new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded()
            {
                super.onRewardedAdLoaded();
                Toast.makeText(MainActivity.this, "new AD loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError loadAdError)
            {
                super.onRewardedAdFailedToLoad(loadAdError);
                onRequestAd();
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    void onShowRewardAd()
    {
        if(!rewardedAd.isLoaded()) {
            Toast.makeText(MainActivity.this, "Loading an AD", Toast.LENGTH_SHORT).show();
            LoadAdX();
            return;
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
        if(UID == 0){
            Toast.makeText(MainActivity.this, "Login is required", Toast.LENGTH_LONG).show();
            return;
        }
        final int UIDFinal = UID;
        RewardedAdCallback adCallback = new RewardedAdCallback()
        {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem)
            {
                Toast.makeText(MainActivity.this, "Earned it", Toast.LENGTH_SHORT).show();
                webView3AccountSettingsCard.loadUrl("javascript:SuMUpdateCoinsCount("+UIDFinal+",1);");
                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                webView3AccountSettingsCard.loadUrl("javascript:SuMCoinsCountUltMoving("+UIDFinal+");");
                            }
                        },
                        540);
                SuMStaticVs.ADRewaredEarned = true;
                SuMStaticVs.ADProssEnded = true;

            }

            @Override
            public void onRewardedAdOpened()
            {
                super.onRewardedAdOpened();
            }

            @Override
            public void onRewardedAdClosed()
            {
                super.onRewardedAdClosed();
                onRequestAd();
            }

            @Override
            public void onRewardedAdFailedToShow(AdError adError)
            {
                super.onRewardedAdFailedToShow(adError);
                SuMStaticVs.ADRewaredEarned = false;
                SuMStaticVs.ADProssEnded = true;
            }
        };
        rewardedAd.show(MainActivity.this, adCallback);
    }

    @JavascriptInterface
    public void SuMBTActNormal(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                int NBH = 1;
                Resources resources = MainActivity.this.getResources();
                int resourceId0 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId0 > 0) {
                    NBH = resources.getDimensionPixelSize(resourceId0);
                }
                int SBH = 1;
                int resourceId1 = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId1 > 0) {
                    SBH = getResources().getDimensionPixelSize(resourceId1);
                }
                if(SBH<1){SBH = 1;}
                if(NBH<1){NBH = 1;}
                ViewGroup.LayoutParams params0 = findViewById(R.id.SuMStatusBar).getLayoutParams();
                params0.height = SBH;
                findViewById(R.id.SuMStatusBar).setLayoutParams(params0);
                ViewGroup.LayoutParams params1 = findViewById(R.id.SuMNavBar).getLayoutParams();
                params1.height = NBH;
                findViewById(R.id.SuMNavBar).setLayoutParams(params1);

                findViewById(R.id.SuMStatusBar).setVisibility(View.VISIBLE);
                findViewById(R.id.SuMNavBar).setVisibility(View.VISIBLE);

            }
        });

    }

    @JavascriptInterface
    public void SuMBTActTopNBottom(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                int NBH = 1;
                Resources resources = MainActivity.this.getResources();
                int resourceId0 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId0 > 0) {
                    NBH = resources.getDimensionPixelSize(resourceId0);
                }
                int SBH = 1;
                int resourceId1 = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId1 > 0) {
                    SBH = getResources().getDimensionPixelSize(resourceId1);
                }
                if(SBH<1){SBH = 1;}
                if(NBH<1){NBH = 1;}
                ViewGroup.LayoutParams params0 = findViewById(R.id.SuMStatusBar).getLayoutParams();
                params0.height = SBH;
                findViewById(R.id.SuMStatusBar).setLayoutParams(params0);
                ViewGroup.LayoutParams params1 = findViewById(R.id.SuMNavBar).getLayoutParams();
                params1.height = NBH;
                findViewById(R.id.SuMNavBar).setLayoutParams(params1);

                findViewById(R.id.SuMStatusBar).setVisibility(View.VISIBLE);
                findViewById(R.id.SuMNavBar).setVisibility(View.VISIBLE);

            }
        });

    }
    @JavascriptInterface
    public void SuMBTActNoTopNNoBottom(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ViewGroup.LayoutParams params0 = findViewById(R.id.SuMStatusBar).getLayoutParams();
                params0.height = 1;
                findViewById(R.id.SuMStatusBar).setLayoutParams(params0);
                ViewGroup.LayoutParams params1 = findViewById(R.id.SuMNavBar).getLayoutParams();
                params1.height = 1;
                findViewById(R.id.SuMNavBar).setLayoutParams(params1);

                findViewById(R.id.SuMStatusBar).setVisibility(View.VISIBLE);
                findViewById(R.id.SuMNavBar).setVisibility(View.VISIBLE);

            }
        });

    }
    @JavascriptInterface
    public void SuMBackLight(){

        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {


                findViewById(R.id.SuMBackCWA).setBackground(getDrawable(R.drawable.light_bg));
                findViewById(R.id.SuMWebNavColorShaper).setBackground(getDrawable(R.drawable.bg_white_c22dp));

            }
        });

    }
    @JavascriptInterface
    public void SuMBackDark(){

        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {

                findViewById(R.id.SuMBackCWA).setBackground(getDrawable(R.drawable.dark_gb));
                findViewById(R.id.SuMWebNavColorShaper).setBackground(getDrawable(R.drawable.gb_dark_c22dp));

            }
        });

    }

    @JavascriptInterface
    public void SuMCoinsShowITTA(String CoinsC){

        GlobalCurrCoinsCount = Integer.parseInt(CoinsC);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String HelperX = "";
                HelperX = CoinsC + " Coins";
                String HelperAABC = UserNameFC+ " · " + HelperX;
                final TextView textViewToChange = (TextView) findViewById(R.id.SuMUseNameTXT);
                textViewToChange.setText(HelperAABC);
                ((TextView)findViewById(R.id.SuMCoinCard_CountTXT)).setText(CoinsC+"");

            }
        });

    }


    @JavascriptInterface
    public void GoBack(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                GoBackABS();

            }
        });

    }

    @JavascriptInterface
    public void HideSuMwebNav(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setStartOffset(0);
                fadeOut.setDuration(320);
                findViewById(R.id.SuMBottomWebNavBarABS).startAnimation(fadeOut);
                final Handler handlerBlur = new Handler();
                handlerBlur.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        findViewById(R.id.SuMBottomWebNavBarABS).setVisibility(View.INVISIBLE);

                    }
                }, 320);

            }
        });

    }
    @JavascriptInterface
    public void ShowSuMwebNav(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(320);
                findViewById(R.id.SuMBottomWebNavBarABS).setVisibility(View.VISIBLE);
                findViewById(R.id.SuMBottomWebNavBarABS).startAnimation(fadeIn);

            }
        });

    }
    @JavascriptInterface
    public void ShowSuMwebNavInstent(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                findViewById(R.id.SuMBottomWebNavBarABS).setVisibility(View.VISIBLE);

            }
        });

    }

    public String UltraThemeHexRoot = "";

    @JavascriptInterface
    public void SetUltraShareLink(Object A){

        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {

                SuMUltraCurrLink = A.toString();
                //SuMUltraCurrUrl = "";
                //SuMUltraWannaJS = "";
                //SuMUltraFavJS = "";


            }
        });

    }
    @JavascriptInterface
    public void ResizewebView3AccountSettingsCard(final int HDFSJSFromCardIsHeight){

        runOnUiThread(new Runnable() {
            @SuppressLint("CutPasteId")
            @Override
            public void run() {
                double b = HDFSJSFromCardIsHeight;
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
                if(UID>0) {
                    if (b < 190.26) {
                        b = 190.26;
                    }
                } else {
                    b = 580;
                }

                @SuppressLint("CutPasteId") ValueAnimator slideAnimator = ValueAnimator
                        .ofInt(findViewById(R.id.SuMWebViewIndex3AccountSettingsCardBG).getMeasuredHeight(), (int) (b * (getResources().getDisplayMetrics().density)))
                        .setDuration(180);

                slideAnimator.addUpdateListener(animation1 -> {
                    Integer value = (Integer) animation1.getAnimatedValue();
                    findViewById(R.id.SuMWebViewIndex3AccountSettingsCardBG).getLayoutParams().height = value;
                    findViewById(R.id.SuMWebViewIndex3AccountSettingsCardBG).requestLayout();
                });
                AnimatorSet animationSet = new AnimatorSet();
                animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
                animationSet.play(slideAnimator);
                animationSet.start();

                if(b>230) {
                    final double c = b;
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {
                                    WebView layout = findViewById(R.id.SuMWebViewIndex3AccountSettingsCard);
                                    ViewGroup.LayoutParams params = layout.getLayoutParams();
                                    params.height = (int) (c * (getResources().getDisplayMetrics().density));
                                    layout.setLayoutParams(params);
                                }
                            },
                            180);
                } else {
                    WebView layout = findViewById(R.id.SuMWebViewIndex3AccountSettingsCard);
                    ViewGroup.LayoutParams params = layout.getLayoutParams();
                    params.height = (int) (b * (getResources().getDisplayMetrics().density));
                    layout.setLayoutParams(params);
                }

            }
        });

    }

    @JavascriptInterface
    public void LoadWebVersionValue(String Value){
        final String ValueFinal = Value;

        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {


                ((TextView)(findViewById(R.id.SuMSettingsTXT_WebVersionValueTXT))).setText(ValueFinal);

            }
        });

    }

    public void SuMExploreInfoTitle(String ManagaTitle){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.SuMExploreInfo_MangaTitle)).setText(ManagaTitle);
            }
        });
    }

    public void SuMExploreInfoAuthor(String ManagaAuthor){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.SuMExploreInfo_MangaAuthors)).setText("By: "+ManagaAuthor);
            }
        });
    }

    public void SuMExploreInfoGerns(String ManagaGerns){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final String MangaGernsToPross = ManagaGerns.toLowerCase(Locale.ROOT).replace(" ","").replace("-","");
                if(MangaGernsToPross.contains("action")){
                    findViewById(R.id.SuMExploreIndo_Gern_Action).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.SuMExploreIndo_Gern_Action).setVisibility(View.GONE);
                }
                if(MangaGernsToPross.contains("drama")){
                    findViewById(R.id.SuMExploreIndo_Gern_Drama).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.SuMExploreIndo_Gern_Drama).setVisibility(View.GONE);
                }
                if(MangaGernsToPross.contains("fantasy")){
                    findViewById(R.id.SuMExploreIndo_Gern_Fantasy).setVisibility(View.VISIBLE);
                }
                else {
                    findViewById(R.id.SuMExploreIndo_Gern_Fantasy).setVisibility(View.GONE);
                }
                if(MangaGernsToPross.contains("comedy")){
                    findViewById(R.id.SuMExploreIndo_Gern_Comedy).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.SuMExploreIndo_Gern_Comedy).setVisibility(View.GONE);
                }
                if(MangaGernsToPross.contains("sliceoflife")){
                    findViewById(R.id.SuMExploreIndo_Gern_SliceofLife).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.SuMExploreIndo_Gern_SliceofLife).setVisibility(View.GONE);
                }
                if(MangaGernsToPross.contains("scifi")){
                    findViewById(R.id.SuMExploreIndo_Gern_SciFi).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.SuMExploreIndo_Gern_SciFi).setVisibility(View.GONE);
                }
                if(MangaGernsToPross.contains("supernatural")){
                    findViewById(R.id.SuMExploreIndo_Gern_Supernatural).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.SuMExploreIndo_Gern_Supernatural).setVisibility(View.GONE);
                }
                if(MangaGernsToPross.contains("mystery")){
                    findViewById(R.id.SuMExploreIndo_Gern_Mystery).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.SuMExploreIndo_Gern_Mystery).setVisibility(View.GONE);
                }
            }
        });
    }

    @JavascriptInterface
    public void SuMExploreInfoViews(String ManagaViews){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.SuMExploreInfo_MangaViewsTXT)).setText(ManagaViews);
            }
        });
    }

    public void SuMExploreInfoBG(Bitmap MangaCoverBitmap){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap ResizedIMGForFrame = Bitmap.createScaledBitmap(MangaCoverBitmap, 360, 540, false);//Bitmap.createScaledBitmap(MangaCoverBitmap, 60, 90, false);
                //double aspectRatio = (double) findViewById(R.id.MainLayout).getHeight() / (double) findViewById(R.id.MainLayout).getWidth();
                //int targetHeight = (int) (180 * aspectRatio);
                int b = (260*(Device_Height/Device_Width));
                if(b>540){ b = 540; }
                Bitmap ResizedIMGForBlur = Bitmap.createBitmap(ResizedIMGForFrame, 0, 0, 260, b);
                ResizedIMGForBlur = Bitmap.createScaledBitmap(ResizedIMGForBlur, 180, (180*(Device_Height/Device_Width)), false);
                final Bitmap BluredCover = blur(MainActivity.this, ResizedIMGForBlur);
                //findViewById(R.id.SuMExploreInfo_MangaBluredBG).setBackground(new BitmapDrawable(getApplicationContext().getResources(), BluredCover));
                findViewById(R.id.SuMExploreInfo_MangaBGABSBlured).setBackground(new BitmapDrawable(getApplicationContext().getResources(), BluredCover));
                //Bitmap ResizedIMGForShow = Bitmap.createScaledBitmap(MangaCoverBitmap, 260, 390, false);
                ((ImageView)findViewById(R.id.SuMExploreInfo_MangaIMG)).setImageBitmap(ResizedIMGForFrame);
            }
        });
    }

    @JavascriptInterface
    public void SuMExploreInfoMangaDisc(String MangaDisc){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.SuMMangaExploreInfo_MangaDisc)).setText(MangaDisc);
            }
        });
    }
    @JavascriptInterface
    public void SuMExploreMangaViews(String MangaViews){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.SuMExploreInfo_MangaViewsTXT)).setText(MangaViews);
            }
        });
    }

    /*public void AnimateLoading(final View ReqView){
        final int newLeftMargin = 210;
        Animation a = new Animation() {

        };
        a.setDuration(500); // in ms
        ReqView.startAnimation(a);
    }*/

    WebView[] WebViewToDistroyABS;

    @SuppressLint("UseCompatLoadingForDrawables")
    @JavascriptInterface
    public void SuMExploreInfoStart(String SuMExploreURL,String ThemeColor,String SuMExploreTitle,String SuMExploreAuthor,String SuMExploreGerns,String MangaAgeRating,String MangaCoverLink) throws IOException {
        try {
            SuMExploreInfoStart_Native(SuMExploreURL,ThemeColor,SuMExploreTitle,SuMExploreAuthor, SuMExploreGerns, MangaAgeRating, MangaCoverLink);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void SuMExploreInfoStart_Native(String SuMExploreURL, String ThemeColor, String SuMExploreTitle, String SuMExploreAuthor, String SuMExploreGerns, String MangaAgeRating, String MangaCoverLink) throws IOException, InterruptedException {

        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        findViewById(R.id.SuMViewFilpperClickBlocker).setVisibility(View.VISIBLE);
                    }
                },
                32);

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
        if(UID>0) {
            URL MangaCoverLinkURL = new URL("https://sum-manga.azurewebsites.net" + MangaCoverLink);
            final Bitmap[] image = {null};
            Thread secondThread = new Thread(() -> {
                try {
                    image[0] = BitmapFactory.decodeStream(MangaCoverLinkURL.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            secondThread.start();
            secondThread.join();
            SuMExploreInfoTitle(SuMExploreTitle);
            SuMExploreInfoAuthor(SuMExploreAuthor);
            SuMExploreInfoGerns(SuMExploreGerns);
            final Bitmap aabc = image[0];
            runOnUiThread(new Runnable() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void run() {
                    int CurrIndexPTM = ((ViewFlipper)findViewById(R.id.simpleViewFlipper)).getDisplayedChild();
                    /*WebViewToDistroyABS = new WebView[]{
                            WebView0RecentsCard};
                    if(CurrIndexPTM == 1){
                        WebViewToDistroyABS = new WebView[]{webView1};
                    }
                    if(CurrIndexPTM == 2){
                        WebViewToDistroyABS = new WebView[]{webView2};
                    }
                    if(CurrIndexPTM == 3){
                        WebViewToDistroyABS = new WebView[]{webView3AccountSettingsCard};
                    }
                    if(CurrIndexPTM == 4){
                        WebViewToDistroyABS = new WebView[]{webView4};
                    }
                    if(CurrIndexPTM>4){
                        WebViewToDistroyABS = null;
                    }
                    if(WebViewToDistroyABS != null) {
                        destroyWebViews(WebViewToDistroyABS);
                    }*/
                    if (!isConnectedToInternet()) {
                        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
                        @SuppressLint("MissingPermission") final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                        final Bitmap image = Bitmap.createScaledBitmap(((BitmapDrawable) wallpaperDrawable).getBitmap(), 260, 390, false);
                    } else {
                        SuMExploreInfoBG(aabc);
                    }
                    LoadColors();
                    String[] rgbrootpross = ThemeColor.replace(" ", "").replace("rgb", "").replace("a", "").replace("(", "").replace(")", "").split(",");
                    String hex = String.format("#%02x%02x%02x", Integer.parseInt(rgbrootpross[0]), Integer.parseInt(rgbrootpross[1]), Integer.parseInt(rgbrootpross[2]));
                    findViewById(R.id.SuMEXPLOREiNFO_MangaDiscBG).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_c22dp), Color.parseColor(hex)));
                    findViewById(R.id.ExploreBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_dashboard_black_48dp), Color.parseColor(hex)));
                    ((TextView) findViewById(R.id.ExploreBTNTXT)).setTextColor(Color.parseColor(hex));
                    TextView textvivesubt = (TextView) findViewById(R.id.SuMMangaTXT);
                    textvivesubt.setTextColor(Color.parseColor(hex));
                    textvivesubt = (TextView) findViewById(R.id.NavBackTXT);
                    textvivesubt.setTextColor(Color.parseColor(hex));
                    ((TextView) findViewById(R.id.SuMExploreInfo_MangaAgeRating)).setText(MangaAgeRating);
                    webView5.onResume();
                    webView5.loadUrl("https://sum-manga.azurewebsites.net" + SuMExploreURL.replace("ContantExplorer.aspx", "ContantExplorerCard.aspx"));
                    //findViewById(R.id.SuMExploreInfo_MangaBGColor).setClipToOutline(true);
                    findViewById(R.id.SuMExploreInfo_MangaIMG_CornerFix).setClipToOutline(true);
                    Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setDuration(320);
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {
                                    //findViewById(R.id.SuMNavBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
                                    //findViewById(R.id.SuMNavBar).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
                                    findViewById(R.id.SuMNavBarExtendor).setAlpha((float) 0.0);
                                    findViewById(R.id.SuMNavBar).setAlpha((float) 0.0);
                                    findViewById(R.id.SuMExploreInfo_ABS).setVisibility(View.VISIBLE);
                                    findViewById(R.id.SuMExploreInfo_ABS).startAnimation(fadeIn);
                                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    findViewById(R.id.SuMViewFilpperClickBlocker).setVisibility(View.GONE);
                                                }
                                            },
                                            320);

                                }
                            },
                            320);
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Login in SETTINGS to start reading now!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
    public void CloseSuMExploreInfo(View view){
        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {
                if(WebViewToDistroyABS!=null){
                    for(int i =0;i<WebViewToDistroyABS.length;i++){
                        WebViewToDistroyABS[i].onResume();
                    }
                }
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setStartOffset(0);
                fadeOut.setDuration(320);
                findViewById(R.id.SuMExploreInfo_ABS).startAnimation(fadeOut);
                findViewById(R.id.ExploreBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_dashboard_black_48dp), Color.parseColor(RootHexColor)));
                ((TextView)findViewById(R.id.ExploreBTNTXT)).setTextColor(Color.parseColor(RootHexColor));
                findViewById(R.id.SuMNavBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(RootHexColor)));
                findViewById(R.id.SuMNavBarExtendor).setAlpha((float)1);
                findViewById(R.id.SuMNavBar).setAlpha((float)1);
                findViewById(R.id.SuMNavBar).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(RootHexColor)));
                final Handler handlerBlur = new Handler();
                handlerBlur.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.SuMExploreInfo_ABS).setVisibility(View.GONE);
                        webView5.loadUrl("about:blank");
                        SuMPauseXWebView(webView5);
                        findViewById(R.id.SuMViewFilpperClickBlocker).setVisibility(View.GONE);
                    }
                }, 320);
            }
        });
    }

    private void SuMExploreLoadReader_Native(String ReadingURL){
        Intent i = new Intent(MainActivity.this, SuMReader.class);
        i.putExtra("THEME_RBG", RootHexColor);
        i.putExtra("FILES_LINK", ReadingURL);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
    }

    @JavascriptInterface
    public void SuMExploreLoadReader(String ReadingURL) {
        SuMExploreLoadReader_Native(ReadingURL);
    }
    @JavascriptInterface
    public void GoToSettingsSuMExplore(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadSettings(null);
            }
        });
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewListClicked(View v, int position);
    }



    private void initView() {

        // Initialize RecyclerView and set Adapter
        SuMStaticVs.MainC = MainActivity.this;
        recyclerView = findViewById(R.id.scout_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        scoutArrayList = new ArrayList<>();
        adapter = new ScoutAdapter(this,scoutArrayList,this);
        recyclerView.setAdapter(adapter);

        final float scale = MainActivity.this.getResources().getDisplayMetrics().density;
        //int pixels = (int) (640 * scale + 0.5f);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.scout_recycler_view_Width);
        if(relativeLayout.getWidth()>(int) (500 * scale + 0.5f)){
            relativeLayout.getLayoutParams().width = (int) (500 * scale + 0.5f);
        }


        createList();
    }

    private void createList() {
        new Thread(new Runnable() {
            public void run() {

                String CURL = "https://sum-manga.azurewebsites.net/ExploreGetByGarnAPI.aspx";
                File httpCacheDirectory = new File(MainActivity.this.getCacheDir(), "http-cache");
                int cacheSize = 10 * 1024 * 1024; // 10 MiB
                Cache cache = new Cache(httpCacheDirectory, cacheSize);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addNetworkInterceptor(new CacheInterceptor())
                        .cache(cache)
                        .build();
                Request request = new Request.Builder()
                        .url(CURL)
                        .build();
                Response responses = null;
                try {
                    responses = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String resStr = null;
                try {
                    resStr = responses.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                responses.close();
                String finalResStr = resStr;
                runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {

                        JSONArray jsonArr = null;
                        try {
                            jsonArr = new JSONArray(finalResStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = jsonArr.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println(jsonObj);
                        }

                        for (int i = 0; i < jsonArr.length(); ++i) {
                            JSONObject rec = null;
                            String[] A = new String[7];
                            try {
                                rec = jsonArr.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                A[0] = rec.getString("CardBG");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                A[1] = rec.getString("cardtitle");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                A[2] = rec.getString("theme").replace("(", "").replace(")", "").replace("rgb", "").replace("a", "".replace(",0.74", ""));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                A[3] = rec.getString("Link");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                A[4] = rec.getString("Auther");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                A[5] = rec.getString("GernsString");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                A[6] = rec.getString("AgeRating");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //SuMExploreAuthor,SuMExploreGerns,MangaAgeRating,SuMExploreURL
                            scoutArrayList.add(new Scout(A[1], A[0], A[2],A[4],A[5],A[6],A[3]));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }


}

/*class GetExample {
    final OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        GetExample example = new GetExample();
        String response = example.run("https://raw.github.com/square/okhttp/master/README.md");
        System.out.println(response);
    }
}*/


