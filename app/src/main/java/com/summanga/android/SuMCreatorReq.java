package com.summanga.android;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import okhttp3.Response;

public class SuMCreatorReq extends AppCompatActivity {
    private Animation animation_card_click;
    private String LOADING_MESSAGE;
    private int THEMECOLOR;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");

        if(cookies==null) SuMCreatorReq.this.finish();
        else if(!cookies.toString().toLowerCase(Locale.ROOT).contains("creatorname=")) SuMCreatorReq.this.finish();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                LOADING_MESSAGE= null;
            } else {
                LOADING_MESSAGE= extras.getString("THEME_RBG");
            }
        } else {
            LOADING_MESSAGE= (String) savedInstanceState.getSerializable("THEME_RBG");
        }
        if(LOADING_MESSAGE == null){
            SuMCreatorReq.this.finish();
        } else {
            THEMECOLOR = Color.parseColor(LOADING_MESSAGE);
        }
        setContentView(R.layout.sumcreatorreq);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(THEMECOLOR);
        window.setNavigationBarColor(THEMECOLOR);
        window.setNavigationBarDividerColor(THEMECOLOR);
        findViewById(R.id.SuMCreatorPanel_BG_Color).setBackgroundColor(THEMECOLOR);
        animation_card_click = AnimationUtils.loadAnimation(SuMCreatorReq.this, R.anim.card_click);
        GetThisWenViewReady(((WebView)findViewById(R.id.SuMCreatorPanel_WebView)));
    }
    public void CloseSuMCreatorPanel(View view){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(animation_card_click == null) animation_card_click = AnimationUtils.loadAnimation(SuMCreatorReq.this, R.anim.card_click);
                view.startAnimation(animation_card_click);
                SuMCreatorReq.this.finish();
            }
        });
    }
    public void AddAReq_SuMCreator(View view){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(animation_card_click == null) animation_card_click = AnimationUtils.loadAnimation(SuMCreatorReq.this, R.anim.card_click);
                view.startAnimation(animation_card_click);
            }
        });
    }
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void GetThisWenViewReady(WebView webViewx) {
        webViewx.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                //CookieSyncManager.getInstance().sync();
                CookieManager.getInstance().flush();
                if(!url.contains("/CreatorMangaPanel.aspx")) SuMCreatorReq.this.finish();
            }
        });
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
        webViewx.addJavascriptInterface(SuMCreatorReq.this, "androidAPIs");
        webViewx.loadUrl("https://sum-manga.azurewebsites.net/SuMCreator/CreatorMangaPanel.aspx");
        webViewx.setVisibility(View.VISIBLE);
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

    @JavascriptInterface
    public void ViewCreatorItemX(){

    }
    @JavascriptInterface
    public void SendACreatorReq(){

    }
    @JavascriptInterface
    public void CurrPageURL(String URL){
        if(!URL.contains("/CreatorMangaPanel.aspx")) SuMCreatorReq.this.finish();
    }

}
