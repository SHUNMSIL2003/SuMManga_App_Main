package com.summanga.android;

import android.Manifest;
import android.animation.AnimatorSet;
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
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;*/


public class MainActivity extends AppCompatActivity {


    public int SuMMangaCoinsConsumeValue = 0;

    boolean FirstLoad = true;

    ViewFlipper simpleViewFlipper;

    public int LastBSearchView = 0;


    int GlobalCurrCoinsCount = 0;

    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 20.0f;

    public Color DoCo;

    public String UserNameFC = "";

    public int StatusBarH = 1;
    public int NavBarH = 1;

    public String RootHexColor = "";
    public int RootStateBit = 0;

    private RewardedAd rewardedAd;

    boolean MenuonLongClick = false;
    boolean MenuonLongClickIsGoing = false;

    WebView webView0LatestCard;
    WebView WebView0RecentsCard;
    WebView webView0ActionCard;
    WebView WebView0DramaCard;
    WebView webView0FantasyCard;
    WebView webView0ComedyCard;
    WebView webView0SliceofLifeCard;
    WebView webView0SciFiCard;
    WebView webView0SupernaturalCard;
    WebView webView0MysteryCard;
    WebView webView1;
    WebView webView2;
    WebView webView3AccountSettingsCard;
    WebView webView4;
    WebView webView5;
    WebView webView6_SECURE;
    WebView webViewX_SECURE;



    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;

    public String[] url;

    //ImageView SplashScreen;
    int statusBarHeight,
            currentAppVersionCode = -15;
    boolean SUMAuthIsDone = false;
    boolean SuMAuthResult = false;
    boolean SuMAuthUnderPross = false;
    private CancellationSignal cancellationSignal = null;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;

    boolean SlpashSuMGone = false;


    @SuppressLint("SetJavaScriptEnabled")
    public void GetThisWenViewReady(WebView webViewx, boolean ReqPer, boolean ReqFileUpload,boolean CanLoadMoreLinks) {


        if (!CanLoadMoreLinks) {
            webViewx.setWebViewClient(new xWebViewClient());
        } else {
            webViewx.setWebViewClient(new yWebViewClient());
        }
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
        contentWebViewSettings.setDomStorageEnabled(true);
        //contentWebViewSettings.setBuiltInZoomControls(true);
        contentWebViewSettings.setDisplayZoomControls(false);
        //contentWebViewSettings.setSupportMultipleWindows(true);
        contentWebViewSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        contentWebViewSettings.setUseWideViewPort(true);
        contentWebViewSettings.setSaveFormData(true);
        contentWebViewSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webViewx.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        contentWebViewSettings.setAllowFileAccess(true);
        contentWebViewSettings.setAllowFileAccessFromFileURLs(true);
        contentWebViewSettings.setAllowUniversalAccessFromFileURLs(true);
        contentWebViewSettings.setAllowContentAccess(true);
        contentWebViewSettings.setSupportZoom(false);
        //webViewx.getSettings().setBuiltInZoomControls(true);
        if(ReqPer) {
            webViewx.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            contentWebViewSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webViewx.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webViewx.setScrollbarFadingEnabled(true);
            webViewx.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            contentWebViewSettings.setEnableSmoothTransition(true);
        }

        contentWebViewSettings.setAppCacheEnabled(true);
        contentWebViewSettings.setSavePassword(true);
        contentWebViewSettings.setSaveFormData(true);

        webViewx.setBackgroundColor(Color.TRANSPARENT);
        webViewx.loadUrl("about:blank");
        webViewx.clearHistory();
        webViewx.destroyDrawingCache();
        webViewx.clearView();
        webViewx.removeAllViews();
        //webViewx.onPause();
    }



    @SuppressLint({"SetJavaScriptEnabled", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int Device_Height = displayMetrics.heightPixels;
        int Device_Width = displayMetrics.widthPixels;
        setContentView(R.layout.activity_main);
        updateLayout();
        simpleViewFlipper = (ViewFlipper) findViewById(R.id.simpleViewFlipper);
        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.fade_in); // load an animation
        in.setStartOffset(360);
        in.setDuration(360);
        simpleViewFlipper.setInAnimation(in);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.fade_out); // load an animation
        out.setDuration(280);
        simpleViewFlipper.setOutAnimation(out);

        DarkSBIcons();

        url = new String[]{"https://sum-manga.azurewebsites.net/ExploreMainCard.aspx",
                "https://sum-manga.azurewebsites.net/ExploreRecentlyCard.aspx",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Action"
                ,"https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Drama",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Fantasy",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Comedy",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=SliceofLife",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=SciFi",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Supernatural",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Mystery"};

        webView0LatestCard = (WebView) findViewById(R.id.SuMWebView_NewestCardSlideView);
        webView0LatestCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView0LatestCard,false,false,false);
        WebView0RecentsCard = (WebView) findViewById(R.id.SuMWebView_ResentsCard);
        WebView0RecentsCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(WebView0RecentsCard,false,false,false);
        webView0ActionCard = (WebView) findViewById(R.id.SuMWebView_ActionCard);
        webView0ActionCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView0ActionCard,false,false,false);
        WebView0DramaCard = (WebView) findViewById(R.id.SuMWebView_DramaCard);
        WebView0DramaCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(WebView0DramaCard,false,false,false);
        webView0FantasyCard = (WebView) findViewById(R.id.SuMWebView_FantasyCard);
        webView0FantasyCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView0FantasyCard,false,false,false);
        webView0ComedyCard = (WebView) findViewById(R.id.SuMWebView_ComedyCard);
        webView0ComedyCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView0ComedyCard,false,false,false);
        webView0SliceofLifeCard = (WebView) findViewById(R.id.SuMWebView_SliceofLifeCard);
        webView0SliceofLifeCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView0SliceofLifeCard,false,false,false);
        webView0SciFiCard = (WebView) findViewById(R.id.SuMWebView_SciFiCard);
        webView0SciFiCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView0SciFiCard,false,false,false);
        webView0SupernaturalCard = (WebView) findViewById(R.id.SuMWebView_SupernaturalCard);
        webView0SupernaturalCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView0SupernaturalCard,false,false,false);
        webView0MysteryCard = (WebView) findViewById(R.id.SuMWebView_MysteryCard);
        webView0MysteryCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView0MysteryCard,false,false,false);
        webView4 = (WebView) findViewById(R.id.SuMWebViewIndex4);
        webView4.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView4,false,false,false);
        webView2 = (WebView) findViewById(R.id.SuMWebViewIndex2);
        webView2.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView2,false,false,false);
        webView3AccountSettingsCard = (WebView) findViewById(R.id.SuMWebViewIndex3AccountSettingsCard);
        webView3AccountSettingsCard.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView3AccountSettingsCard,false,true,true);
        webView1 = (WebView) findViewById(R.id.SuMWebViewIndex1);
        webView1.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView1,false,false,false);
        webView5 = (WebView) findViewById(R.id.SuMWebViewIndex5);
        webView5.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView5,false,false,false);
        webView6_SECURE = (WebView) findViewById(R.id.SuMWebViewIndex6_MangaReader);
        webView6_SECURE.setVisibility(View.VISIBLE);
        GetThisWenViewReady(webView6_SECURE,false,false,false);
        webViewX_SECURE = (WebView) findViewById(R.id.webViewX_SECURE_JS);
        webViewX_SECURE.setVisibility(View.GONE);
        GetThisWenViewReady(webViewX_SECURE,false,false,false);
        webViewX_SECURE.loadUrl("https://sum-manga.azurewebsites.net/storeitems/MangaExplorerCardHolder.aspx");


        //Set a chosen URL to load
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();


        LoadXView(new String[]{"https://sum-manga.azurewebsites.net/ExploreMainCard.aspx",
                "https://sum-manga.azurewebsites.net/ExploreRecentlyCard.aspx",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Action"
                ,"https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Drama",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Fantasy",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Comedy",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=SliceofLife",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=SciFi",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Supernatural",
                "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Mystery"});



        Boolean FoundAnIndex = false;
        if(SuMStaticVs.RedirectFromSuMNotiURL == null){
            LoadExplore(null);
        } else {
            if(SuMStaticVs.RedirectFromSuMNotiURL.toString() == "Hits"){
                LoadHit(null);
                FoundAnIndex = true;
            }
            if(SuMStaticVs.RedirectFromSuMNotiURL == "Library"){
                LoadLibrary(null);
                FoundAnIndex = true;
            }
            if(SuMStaticVs.RedirectFromSuMNotiURL == "Search"){
                LoadSearch(null);
                FoundAnIndex = true;
            }
            if(SuMStaticVs.RedirectFromSuMNotiURL == "Settings"){
                LoadSettings(null);
                FoundAnIndex = true;
            }
            if(!FoundAnIndex && SuMStaticVs.RedirectFromSuMNotiURL != null) {

            }
        }

        createNotificationChannel();

        authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            // here we need to implement two methods
            // onAuthenticationError and
            // onAuthenticationSucceeded If the
            // fingerprint is not recognized by the
            // app it will call onAuthenticationError
            // and show a toast
            @Override
            public void onAuthenticationError(
                    int errorCode, CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
                notifyUser("Authentication Error : " + errString);
                SUMAuthIsDone = true;
                SuMAuthResult = false;
                SuMAuthUnderPross = false;
            }
            // If the fingerprint is recognized by the
            // app then it will call
            // onAuthenticationSucceeded and show a
            // toast that Authentication has Succeed
            // Here you can also start a new activity
            // after that
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result)
            {
                super.onAuthenticationSucceeded(result);
                notifyUser("Authentication Succeeded");
                SUMAuthIsDone = true;
                SuMAuthResult = true;
                SuMAuthUnderPross = false;
                // or start a new Activity
            }
        };

        checkBiometricSupport();

        DarkSBIcons();


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
                        RSBit = AA[i].replace(" ", "").replace("SuMUserThemeState=", "").replace("'","");
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



        if (cookies != null) {
            if (cookies.toString().contains("UserName=")) {
                String[] AA = cookies.toString().replace(" ", "").split(";");
                boolean foundAA = false;
                for (int i = 0; i < AA.length; i++) {
                    if (AA[i].contains("UserName=")) {
                        String[] BBA = AA[i].split("&");
                                for(int ii = 0; ii<BBA.length;ii++) {
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

        /*DetectedSuMURL = DetectedSuMURL.replace(" ","");
        if (DetectedSuMURL.contains("TC=") && DetectedSuMURL.contains("rgb")) {
            String[] BBAC = DetectedSuMURL.split("aspx")[1].split("&");
            for (int i = 0; i < BBAC.length; i++){
                if(BBAC[i].contains("TC=")) {
                    RS = BBAC[i].replace("TC=rgb","").replace("a","").replace("(","").replace(")","");
                }
            }
        }*/

        String[] BB = RS.split(",");
        String hex = String.format("#%02X%02X%02X", Integer.parseInt(BB[0]), Integer.parseInt(BB[1]), Integer.parseInt(BB[2]));
        findViewById(R.id.SuMStatusBar).getBackground().setColorFilter(Color.parseColor(hex), PorterDuff.Mode.ADD);
        findViewById(R.id.SuMStatusBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
        findViewById(R.id.SuMNavBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
        findViewById(R.id.SuMNavBar).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
        findViewById(R.id.SuMUseNameTXT).getBackground().setColorFilter(Color.parseColor(hex), PorterDuff.Mode.ADD);


        TextView textvivesubt = (TextView)findViewById(R.id.ExploreBTNTXT);
        /*if(DetectedSuMURL.contains("Explore")) {
            findViewById(R.id.ExploreBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_dashboard_black_48dp), Color.parseColor(hex)));
            textvivesubt.setTextColor(Color.parseColor(hex));
        } else {
            findViewById(R.id.ExploreBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_dashboard_black_48dp), Color.parseColor(SecSelectColor)));
            textvivesubt.setTextColor(Color.parseColor(SecSelectColor));
        }
        if(DetectedSuMURL.contains("/UserLibrary.aspx")) {
            findViewById(R.id.LibraryBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_bookmarks_black_48dp), Color.parseColor(hex)));
            textvivesubt = (TextView)findViewById(R.id.LibraryBTNTXT);
            textvivesubt.setTextColor(Color.parseColor(hex));
        } else {
            findViewById(R.id.LibraryBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_bookmarks_black_48dp), Color.parseColor(SecSelectColor)));
            textvivesubt = (TextView)findViewById(R.id.LibraryBTNTXT);
            textvivesubt.setTextColor(Color.parseColor(SecSelectColor));
        }
        if(DetectedSuMURL.contains("/Hits.aspx")) {
            findViewById(R.id.HitsBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_local_library_black_48dp), Color.parseColor(hex)));
            textvivesubt = (TextView)findViewById(R.id.HitsBTNTXT);
            textvivesubt.setTextColor(Color.parseColor(hex));
        } else {
            findViewById(R.id.HitsBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_local_library_black_48dp), Color.parseColor(SecSelectColor)));
            textvivesubt = (TextView)findViewById(R.id.HitsBTNTXT);
            textvivesubt.setTextColor(Color.parseColor(SecSelectColor));
        }
        if(DetectedSuMURL.contains("/AccountETC/")) {
            findViewById(R.id.SettingsBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_settings_black_48dp), Color.parseColor(hex)));
            textvivesubt = (TextView)findViewById(R.id.SettingsBTNTXT);
            textvivesubt.setTextColor(Color.parseColor(hex));
        } else {
            findViewById(R.id.SettingsBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_settings_black_48dp), Color.parseColor(SecSelectColor)));
            textvivesubt = (TextView)findViewById(R.id.SettingsBTNTXT);
            textvivesubt.setTextColor(Color.parseColor(SecSelectColor));
        }*/
        textvivesubt = (TextView)findViewById(R.id.SuMMangaTXT);
        textvivesubt.setTextColor(Color.parseColor(hex));
        textvivesubt = (TextView)findViewById(R.id.NavBackTXT);
        textvivesubt.setTextColor(Color.parseColor(hex));


        findViewById(R.id.SuMStatusBarExtendor).setVisibility(View.VISIBLE);
        findViewById(R.id.SuMNavBarExtendor).setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(0);
        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);
        getWindow().setNavigationBarDividerColor(Color.BLACK);


        final TextView textViewToChange = (TextView) findViewById(R.id.SuMUseNameTXT);
        textViewToChange.setText(UserNameFC);//IMPORTANAT

                    /*int SBH = 0;
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
                    Animation fadeOut1 = new AlphaAnimation(1, 0);
                    fadeOut1.setStartOffset(1640);
                    fadeOut1.setDuration(320);
                    SplashScreen.startAnimation(fadeOut1);
                    final Handler handler0 = new Handler();
                    handler0.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SplashScreen.setVisibility(View.INVISIBLE);
                            webView.setVisibility(View.VISIBLE);
                            Animation fadeIn = new AlphaAnimation(0, 1);
                            fadeIn.setDuration(320);
                            webView.setAnimation(fadeIn);
                            SplashScreen.setVisibility(View.GONE);
                            SplashScreen.setImageResource(0);
                            SplashScreen.setImageDrawable(null);
                            SlpashSuMGone = true;
                            DarkSBIcons();
                            findViewById(R.id.SuMBottomWebNavBarABS).setVisibility(View.VISIBLE);
                            findViewById(R.id.SuMBottomWebNavBarABS).startAnimation(fadeIn);
                        }
                    }, 320+1640);
                DarkSBIcons();*/




        findViewById(R.id.SuMMenuLayoutBG).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MenuonLongClick && !MenuonLongClickIsGoing) {
                    MenuonLongClickIsGoing = true;
                    Animation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setStartOffset(0);
                    fadeOut.setDuration(320);
                    View A = findViewById(R.id.SuMMenuLayout);
                    View B = findViewById(R.id.SuMMenuLayoutBG);
                    B.startAnimation(fadeOut);
                    A.startAnimation(fadeOut);
                    final Handler handler0 = new Handler();
                    handler0.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            A.setVisibility(View.INVISIBLE);
                            B.setVisibility(View.INVISIBLE);
                            MenuonLongClick = false;
                            MenuonLongClickIsGoing = false;
                        }
                    }, 320);
                }
            }
        });

        SetLongPressToViewX(findViewById(R.id.SuMBackCWA));
        //SetLongPressToViewX(webView1);
        //SetLongPressToViewX(webView2);
        //SetLongPressToViewX(webView3AccountSettingsCard);
        //SetLongPressToViewX(webView4);
        /*RW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Not Long Enough :(",
                        Toast.LENGTH_LONG).show();
            }
        });*/


        LoadAdX();

        MobileAds.initialize(this, initializationStatus -> {

        });

        final Handler handlerBlur = new Handler();
        handlerBlur.postDelayed(new Runnable() {
            @Override
            public void run() {

                final WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
                //Toke PerAsking from here
                @SuppressLint("MissingPermission") final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable)wallpaperDrawable).getBitmap(), 480, ((480)*(Device_Height/Device_Width)), false);
                bitmap = blur(MainActivity.this, bitmap);
                findViewById(R.id.MainLayout).setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                findViewById(R.id.SuMMangaReader).setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));


            }
        }, 0);

        onRequestAd();




    }

    public String SuMUltraCurrLink = "";
    public String SuMUltraCurrUrl = "";
    public String SuMUltraWannaJS = "";
    public String SuMUltraFavJS = "";

    public void VoidX(View view) {
        return;
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
    public void ClearCacheX(View view){

        //clearCache(MainActivity.this,0);
        //deleteDir(getCacheDir());
        //deleteDir(getExternalCacheDir());
        WebView[] WebViewsFoCache = new WebView[]{ webView0LatestCard,webView0LatestCard,webView0ActionCard, webView1,webView2,webView3AccountSettingsCard,webView4 };
        for(int i = 0; i <WebViewsFoCache.length; i++){
            WebViewsFoCache[i].clearCache(true);
            WebViewsFoCache[i].clearHistory();
        }
        MainActivity.this.deleteDatabase("webview.db");
        MainActivity.this.deleteDatabase("webviewCache.db");
        clearApplicationCache();
        for(int i = 0; i <WebViewsFoCache.length; i++){
            WebViewsFoCache[i].clearCache(false);
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


    public void SetLongPressToViewX(View RW){
        RW.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!MenuonLongClick) {
                    Animation fadeIn = new AlphaAnimation(0, 1);
                    View A = findViewById(R.id.SuMMenuLayout);
                    View B = findViewById(R.id.SuMMenuLayoutBG);
                    fadeIn.setDuration(460);
                    A.setVisibility(View.VISIBLE);
                    B.setVisibility(View.VISIBLE);
                    A.startAnimation(fadeIn);
                    B.startAnimation(fadeIn);
                    MenuonLongClick = true;
                }
                return true;
            }
        });
    }


    public void HideSuMUltraCard(View view) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setStartOffset(0);
                fadeOut.setDuration(320);
                findViewById(R.id.SuMUltarCardPort).startAnimation(fadeOut);

                final Handler handler0 = new Handler();
                handler0.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        findViewById(R.id.SuMUltarCardPort).setVisibility(View.INVISIBLE);

                    }
                }, 320);
            }
        });
    }

    public void ShowSuMUltraCard() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setStartOffset(0);
                fadeIn.setDuration(320);
                findViewById(R.id.SuMUltarCardPort).setVisibility(View.VISIBLE);
                findViewById(R.id.SuMUltarCardPort).startAnimation(fadeIn);

            }
        });
    }

    public void GetSuMUltraReady(String RootJSFunc,Object CurrChapterNum,Object ChaptersNum,Object ProdYear,Object AgeRating,Object MangaDisc,Object MangaTitle,Object IsFav,Object IsWanna) {

        //Do Sth

        ShowSuMUltraCard();

    }

    public void LoadExplore(View view) {
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
                                UID = Integer.parseInt(BBA[ii].replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
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
        }
        LoadColors();
        findViewById(R.id.ExploreBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_dashboard_black_48dp), Color.parseColor(RootHexColor)));
        ((TextView)findViewById(R.id.ExploreBTNTXT)).setTextColor(Color.parseColor(RootHexColor));
        LastBSearchView = simpleViewFlipper.getDisplayedChild();
        ViewFlipper ViewFilpperCHelper = (ViewFlipper)findViewById(R.id.simpleViewFlipper);
        if(ViewFilpperCHelper.getDisplayedChild() == 0) { return; }
        if(ViewFilpperCHelper.getDisplayedChild() == 5){
            ViewFilpperCHelper.setDisplayedChild(0);
            return;
        } else {
            LoadXView(
                    new String[]{"https://sum-manga.azurewebsites.net/ExploreMainCard.aspx",
                            "https://sum-manga.azurewebsites.net/ExploreRecentlyCard.aspx",
                            "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Action",
                            "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Drama",
                            "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Fantasy",
                            "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Comedy",
                            "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=SliceofLife",
                            "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=SciFi",
                            "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Supernatural",
                            "https://sum-manga.azurewebsites.net/ExploreGetByGarn.aspx?GR=Mystery"}
            );
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void LoadHit(View view) {
        if(findViewById(R.id.SuMExploreInfo_ABS).getVisibility() == View.VISIBLE) {
            CloseSuMExploreInfo(null);
        }
        LoadColors();
        findViewById(R.id.HitsBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_local_library_black_48dp), Color.parseColor(RootHexColor)));
        ((TextView)findViewById(R.id.HitsBTNTXT)).setTextColor(Color.parseColor(RootHexColor));
        LastBSearchView = simpleViewFlipper.getDisplayedChild();
        ViewFlipper ViewFilpperCHelper = (ViewFlipper)findViewById(R.id.simpleViewFlipper);
        if(ViewFilpperCHelper.getDisplayedChild() == 1) { return; }
        LoadXView(new String[]{"https://sum-manga.azurewebsites.net/Hits.aspx"});
    }
    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void LoadLibrary(View view) {
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
                                UID = Integer.parseInt(BBA[ii].replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
                                ii = BBA.length;
                            }
                        }
                        i = AA.length;
                    }
                }
            }
        }
        if(UID<1){
            Toast.makeText(MainActivity.this, "Login to access your library in SETTINGS!", Toast.LENGTH_LONG).show();
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
                                UID = Integer.parseInt(BBA[ii].replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
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
        findViewById(R.id.SuMClearCacheBTNBG).setBackground(setTint(getResources().getDrawable(R.drawable.bg_btn_c14dp), Color.parseColor(RootHexColor)));
        ((TextView)findViewById(R.id.SuMCoinCardTXT_OneCoin)).setTextColor(Color.parseColor(RootHexColor));
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
    @SuppressLint("UseCompatLoadingForDrawables")
    public void LoadXView(String[] xurl) {
        int CurrIndexPTM = ((ViewFlipper)findViewById(R.id.simpleViewFlipper)).getDisplayedChild();
        WebView[] WebViewToDistroy = new WebView[]{webView0LatestCard,
                WebView0RecentsCard,
                webView0ActionCard,
                WebView0DramaCard,
                webView0FantasyCard,
                webView0ComedyCard,
                webView0SliceofLifeCard,
                webView0SciFiCard,
                webView0SupernaturalCard,
                webView0MysteryCard};
        if(CurrIndexPTM == 1){
            WebViewToDistroy = new WebView[]{webView1};
        }
        if(CurrIndexPTM == 2){
            WebViewToDistroy = new WebView[]{webView2};
        }
        if(CurrIndexPTM == 3){
            WebViewToDistroy = new WebView[]{webView3AccountSettingsCard};
        }
        if(CurrIndexPTM == 4){
            WebViewToDistroy = new WebView[]{webView4};
        }
        if(!FirstLoad) {
            destroyWebViews(WebViewToDistroy);
        }
        WebView[] webViewX = new WebView[]{
                webView0LatestCard,
                WebView0RecentsCard,
                webView0ActionCard,
                WebView0DramaCard,
                webView0FantasyCard,
                webView0ComedyCard,
                webView0SliceofLifeCard,
                webView0SciFiCard,
                webView0SupernaturalCard,
                webView0MysteryCard};
        int IndexX = 0;
        if(xurl[0].contains("/Explore")){
            //GetThisWenViewReady(webView,false,false,false);
        }
        if(xurl[0].contains("/MangaExplorer")) {
            SuMBTActTopNBottomVoid();
        } else {
            SuMBTActNormalVoid();
        }
        if(xurl[0].contains("/Hits")) {
            IndexX = 1;
            webViewX = new WebView[]{ webView1 };
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
        //LoadColors(xurl[0]);
        for(int i = 0; i < webViewX.length; i++) {
            if(!FirstLoad) {
                webViewX[i].onResume();
            }
            if(!webViewX[i].getUrl().equals(xurl[i])) {
                webViewX[i].loadUrl(xurl[i]);
            }
        }
        if(FirstLoad){ FirstLoad = false;}
        simpleViewFlipper.setDisplayedChild(IndexX);

        if(xurl[0].contains("/MangaExplorer.aspx")) {
            findViewById(R.id.SuMUseNameTXT).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.SuMUseNameTXT).setVisibility(View.VISIBLE);
        }
        if(xurl[0].contains("/Hits.aspx")) {
        }
        if(xurl[0].contains("/UserLibrary.aspx")) {
        }
        if(xurl[0].contains("/AccountETC/")) {
        }
        if(xurl[0].contains("/Search.aspx")) {
            findViewById(R.id.SuMUseNameTXT).setVisibility(View.INVISIBLE);
        }
        //findViewById(R.id.SuMStatusBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(RootHexColor)));
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

        TextView textvivesubt = (TextView) findViewById(R.id.ExploreBTNTXT);
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
        textvivesubt.setTextColor(Color.parseColor(SecSelectColor));
        textvivesubt = (TextView) findViewById(R.id.NavBackTXT);
        textvivesubt.setTextColor(Color.parseColor(SecSelectColor));

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

                simpleViewFlipper.setDisplayedChild(LastBSearchView);

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
        WebView0RecentsCard.saveState(outState);
        webView0ActionCard.saveState(outState);
        webView0LatestCard.saveState(outState);
        webView3AccountSettingsCard.saveState(outState);
        webView2.saveState(outState);
        webView1.saveState(outState);
        webView4.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        WebView0RecentsCard.restoreState(savedInstanceState);
        webView0ActionCard.restoreState(savedInstanceState);
        webView0LatestCard.restoreState(savedInstanceState);
        webView3AccountSettingsCard.restoreState(savedInstanceState);
        webView2.restoreState(savedInstanceState);
        webView1.restoreState(savedInstanceState);
        webView4.restoreState(savedInstanceState);
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

        if(findViewById(R.id.SuMMangaReader).getVisibility()==View.VISIBLE){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "To exit reading mode PRESS ON THE BOOK ICON", Toast.LENGTH_LONG).show();
                }
            });
            return false;
        }
        if(LastBSearchView != -1) {
            simpleViewFlipper.setDisplayedChild(LastBSearchView);
            return false;
        }

        return super.onKeyDown(keyCode, event);
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
                        SUMAuthIsDone = true;
                        SuMAuthResult = false;
                        SuMAuthUnderPross = false;
                        //SplashScreen.setVisibility(View.VISIBLE);
                        //SUMAppLock();
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
                        SUMAuthIsDone = true;
                        SuMAuthResult = false;
                        SuMAuthUnderPross = false;
                        //SplashScreen.setVisibility(View.VISIBLE);
                        //SUMAppLock();
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
            notifyUser("Fingerprint authentication has not been enabled in settings");
            SUMAuthIsDone = true;
            SuMAuthResult = false;
            SuMAuthUnderPross = false;
            //SplashScreen.setVisibility(View.VISIBLE);
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled");
            SUMAuthIsDone = true;
            SuMAuthResult = false;
            SuMAuthUnderPross = false;
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
    @JavascriptInterface
    public void VIBRATEPhone(){

        // androidAPIs.VIBRATEPhone();

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(180, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(180);
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
                        SUMAuthIsDone = true;
                        SuMAuthResult = false;
                        SuMAuthUnderPross = false;
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
                                UID = Integer.parseInt(BBA[ii].replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
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
    public void SetSuMUltraColor(Object A){

        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {

                String[] RGBXR = A.toString().replace(" ","").replace("rgb","").replace("a","").replace("(","").replace(")","").split(",");
                String hex = String.format("#%02X%02X%02X", Integer.parseInt(RGBXR[0]), Integer.parseInt(RGBXR[1]), Integer.parseInt(RGBXR[2]));
                UltraThemeHexRoot = hex;
                findViewById(R.id.SuMUltraBackColor).setBackground(setTint(getDrawable(R.drawable.bg_x_x_ul_ca),Color.parseColor(hex)));
                findViewById(R.id.UltraCuurBookIc).setBackground(setTint(getDrawable(R.drawable.ic_menu_book_black_48dp),Color.parseColor(hex)));
                findViewById(R.id.UltraWannaAddIc).setBackground(setTint(getDrawable(R.drawable.ic_add_black_48dp),Color.parseColor(hex)));
                findViewById(R.id.UltraFavAddIc).setBackground(setTint(getDrawable(R.drawable.ic_favorite_border_black_48dp),Color.parseColor(hex)));


            }
        });

    }
    @JavascriptInterface
    public void SetUltraDisc(Object A){

        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {

                TextView B = (TextView) findViewById(R.id.SuMUltraDisc);
                B.setText(A.toString());


            }
        });

    }

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
    public void SetFavState(Boolean A,String JSRemove,String JSAdd){

        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {

                if(A){

                    findViewById(R.id.UltraFavAddIc).setBackground(setTint(getDrawable(R.drawable.ic_favorite_black_48dp),Color.parseColor(UltraThemeHexRoot)));
                }
                else {


                    findViewById(R.id.UltraFavAddIc).setBackground(setTint(getDrawable(R.drawable.ic_favorite_border_black_48dp),Color.parseColor(UltraThemeHexRoot)));

                }

            }
        });

    }
    @JavascriptInterface
    public void ShowUltraCardX(){

        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {

                ShowSuMUltraCard();

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
                                        UID = Integer.parseInt(BBA[ii].replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
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
                ((TextView)findViewById(R.id.SuMExploreInfo_MangaAuthors)).setText(ManagaAuthor);
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
                Bitmap ResizedIMGForBlur = Bitmap.createScaledBitmap(MangaCoverBitmap, 260, 390, false);//Bitmap.createScaledBitmap(MangaCoverBitmap, 60, 90, false);
                final Bitmap BluredCover = blur(MainActivity.this, ResizedIMGForBlur);
                //findViewById(R.id.SuMExploreInfo_MangaBluredBG).setBackground(new BitmapDrawable(getApplicationContext().getResources(), BluredCover));
                findViewById(R.id.SuMExploreInfo_MangaBGABSBlured).setBackground(new BitmapDrawable(getApplicationContext().getResources(), BluredCover));
                //Bitmap ResizedIMGForShow = Bitmap.createScaledBitmap(MangaCoverBitmap, 260, 390, false);
                ((ImageView)findViewById(R.id.SuMExploreInfo_MangaIMG)).setImageBitmap(ResizedIMGForBlur);
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @JavascriptInterface
    public void SuMExploreInfoStart(String SuMExploreURL,String ThemeColor,String SuMExploreTitle,String SuMExploreAuthor,String SuMExploreGerns,String MangaAgeRating,String MangaCoverLink) throws IOException {
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
                                UID = Integer.parseInt(BBA[ii].replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
                                ii = BBA.length;
                            }
                        }
                        i = AA.length;
                    }
                }
            }
        }
        if(UID>0) {
            URL MangaCoverLinkURL = null;// = new URL("https://sum-manga.azurewebsites.net" + MangaCoverLink);
            Bitmap image = null;// = BitmapFactory.decodeStream(MangaCoverLinkURL.openConnection().getInputStream());
            if (isConnectedToInternet()) {
                MangaCoverLinkURL = new URL("https://sum-manga.azurewebsites.net" + MangaCoverLink);
                image = BitmapFactory.decodeStream(MangaCoverLinkURL.openConnection().getInputStream());
            }
            SuMExploreInfoTitle(SuMExploreTitle);
            SuMExploreInfoAuthor(SuMExploreAuthor);
            SuMExploreInfoGerns(SuMExploreGerns);
            final Bitmap aabc = image;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
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
                    findViewById(R.id.SuMNavBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
                    findViewById(R.id.SuMNavBarExtendor).setAlpha((float) 0.74);
                    findViewById(R.id.SuMNavBar).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(hex)));
                    ((TextView) findViewById(R.id.SuMExploreInfo_MangaAgeRating)).setText(MangaAgeRating);
                    webView5.onResume();
                    webView5.loadUrl("https://sum-manga.azurewebsites.net" + SuMExploreURL.replace("ContantExplorer.aspx", "ContantExplorerCard.aspx"));
                    //findViewById(R.id.SuMExploreInfo_MangaBGColor).setClipToOutline(true);
                    findViewById(R.id.SuMExploreInfo_MangaIMG_CornerFix).setClipToOutline(true);
                    Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setDuration(320);
                    findViewById(R.id.SuMExploreInfo_ABS).setVisibility(View.VISIBLE);
                    findViewById(R.id.SuMExploreInfo_ABS).startAnimation(fadeIn);
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
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setStartOffset(0);
                fadeOut.setDuration(320);
                findViewById(R.id.SuMExploreInfo_ABS).startAnimation(fadeOut);
                findViewById(R.id.ExploreBTN).setBackground(setTint(getResources().getDrawable(R.drawable.ic_dashboard_black_48dp), Color.parseColor(RootHexColor)));
                ((TextView)findViewById(R.id.ExploreBTNTXT)).setTextColor(Color.parseColor(RootHexColor));
                findViewById(R.id.SuMNavBarExtendor).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(RootHexColor)));
                findViewById(R.id.SuMNavBarExtendor).setAlpha((float)1);
                findViewById(R.id.SuMNavBar).setBackground(setTint(getResources().getDrawable(R.drawable.bg_xcolor_nb_c0dp), Color.parseColor(RootHexColor)));
                final Handler handlerBlur = new Handler();
                handlerBlur.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.SuMExploreInfo_ABS).setVisibility(View.GONE);
                    }
                }, 320);
            }
        });
    }

    @JavascriptInterface
    public void SuMExploreLoadReader(String ReadingURL,int CoinsRequred){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
                                        UID = Integer.parseInt(BBA[ii].replace(" ", "").replace("ID=", "").replace("SuMCurrentUser=", "").replace("&","").replace("C",""));
                                        ii = BBA.length;
                                    }
                                }
                                i = AA.length;
                            }
                        }
                    }
                }
                if(GlobalCurrCoinsCount > CoinsRequred){
                    webView6_SECURE.loadUrl("https://sum-manga.azurewebsites.net"+ReadingURL.replace("MangaExplorer.aspx","MangaExplorerCard.aspx"));
                    Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setDuration(320);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                    findViewById(R.id.SuMMangaReader).setVisibility(View.VISIBLE);
                    findViewById(R.id.SuMMangaReader).startAnimation(fadeIn);
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
                    final int UIDFinalVar = UID;
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {
                                    webViewX_SECURE.loadUrl("javascript:SuMUpdateCoinsCount(" + UIDFinalVar + "," + ((-1)*CoinsRequred) + ");");
                                }
                            },
                            3200);
                    Toast.makeText(MainActivity.this, CoinsRequred+" coins are consumed"+" & Reading mode is activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, CoinsRequred+" coins are required you can get some in SETTINGS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @JavascriptInterface
    public void SuMExploreLoadReaderForSucInJS(String ReadingURL){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView6_SECURE.loadUrl("https://sum-manga.azurewebsites.net/storeitems"+ReadingURL.replace("MangaExplorer.aspx","MangaExplorerCard.aspx"));
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(320);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                findViewById(R.id.SuMMangaReader).setVisibility(View.VISIBLE);
                findViewById(R.id.SuMMangaReader).startAnimation(fadeIn);
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

                Toast.makeText(MainActivity.this, "Reading mode is activated", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @JavascriptInterface
    public void CloseSuMReaderMode(){
        runOnUiThread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
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
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setStartOffset(0);
                fadeOut.setDuration(320);
                findViewById(R.id.SuMMangaReader).startAnimation(fadeOut);
                final Handler handlerBlur = new Handler();
                handlerBlur.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.SuMMangaReader).setVisibility(View.GONE);
                        webView6_SECURE.loadUrl("about:blank");
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                        Toast.makeText(MainActivity.this, "Reading mode is deactivated", Toast.LENGTH_SHORT).show();
                    }
                }, 320);
            }
        });
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
}




/*class TouchyWebView extends WebView {

    public TouchyWebView(Context context) {
        super(context);
    }

    public TouchyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }
}*/

