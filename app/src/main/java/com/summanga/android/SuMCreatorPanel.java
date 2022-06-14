package com.summanga.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SuMCreatorPanel extends AppCompatActivity {
    private Animation animation_card_click;
    private String LOADING_MESSAGE;
    private int THEMECOLOR;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");

        if(cookies==null) SuMCreatorPanel.this.finish();
        else if(!cookies.toString().toLowerCase(Locale.ROOT).contains("creatorname=")) SuMCreatorPanel.this.finish();

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
            SuMCreatorPanel.this.finish();
        } else {
            THEMECOLOR = Color.parseColor(LOADING_MESSAGE);
        }
        setContentView(R.layout.sumcreatopanel);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(THEMECOLOR);
        window.setNavigationBarColor(THEMECOLOR);
        window.setNavigationBarDividerColor(THEMECOLOR);
        findViewById(R.id.SuMCreatorPanel_BG_Color).setBackgroundColor(THEMECOLOR);
        animation_card_click = AnimationUtils.loadAnimation(SuMCreatorPanel.this, R.anim.card_click);
        GetThisWenViewReady(((WebView)findViewById(R.id.SuMCreatorPanel_WebView)));
    }
    public void CloseSuMCreatorPanel(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(animation_card_click == null) animation_card_click = AnimationUtils.loadAnimation(SuMCreatorPanel.this, R.anim.card_click);
                view.startAnimation(animation_card_click);
                SuMCreatorPanel.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
    public void AddAReq_SuMCreator(View view){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(animation_card_click == null) animation_card_click = AnimationUtils.loadAnimation(SuMCreatorPanel.this, R.anim.card_click);
                view.startAnimation(animation_card_click);
                Intent i = new Intent(SuMCreatorPanel.this, SuMCreatorReq.class);
                i.putExtra("THEME_RBG", LOADING_MESSAGE);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
            }
        });
    }
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void GetThisWenViewReady(WebView webViewx) {
        webViewx.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                //CookieSyncManager.getInstance().sync();
                CookieManager.getInstance().flush();
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
        webViewx.addJavascriptInterface(SuMCreatorPanel.this, "androidAPIs");
        webViewx.loadUrl("https://sum-manga.azurewebsites.net/SuMCreator/CreatorPanel.aspx");
        webViewx.setVisibility(View.VISIBLE);
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
    public void SuMCreatorAddAChapter(int MID_FNR){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SuMCreatorPanel.this, SuMCreatorCHReq.class);
                i.putExtra("THEME_RBG", LOADING_MESSAGE);
                i.putExtra("MSG_MID", MID_FNR+"");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Improves Perf
            }
        });
    }

    @JavascriptInterface
    public void ViewCreatorItemX(){

    }
    @JavascriptInterface
    public void SendACreatorReq(){

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            runOnUiThread(new Runnable() {
                @SuppressLint("ObsoleteSdkInt")
                @Override
                public void run() {
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
            return false;
        } else return super.onKeyDown(keyCode, event);
    }

}
