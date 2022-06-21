package com.summanga.android;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.KeyEvent;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Locale;

public class SuMCreatorCHReq extends AppCompatActivity {
    private Animation animation_card_click;
    private String LOADING_MESSAGE;
    private int THEMECOLOR;
    private static final int FILECHOOSER_RESULTCODE   = 1;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessages;
    private Uri mCapturedImageURI = null;
    private int MSG_MID = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object cookies = CookieManager.getInstance().getCookie("https://sum-manga.azurewebsites.net/");

        if(cookies==null) SuMCreatorCHReq.this.finish();
        else if(!cookies.toString().toLowerCase(Locale.ROOT).contains("creatorname=")) SuMCreatorCHReq.this.finish();

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
            SuMCreatorCHReq.this.finish();
        } else {
            THEMECOLOR = Color.parseColor(LOADING_MESSAGE);
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                MSG_MID= 0;
            } else {
                MSG_MID= Integer.parseInt(extras.getString("MSG_MID"));
            }
        } else {
            MSG_MID= (int) savedInstanceState.getSerializable("MSG_MID");
        }
        if(MSG_MID < 1) {
            SuMCreatorCHReq.this.finish();
        } else {
            THEMECOLOR = Color.parseColor(LOADING_MESSAGE);
        }

        setContentView(R.layout.sumcreatorchreq);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(THEMECOLOR);
        window.setNavigationBarColor(THEMECOLOR);
        window.setNavigationBarDividerColor(THEMECOLOR);
        findViewById(R.id.SuMCreatorPanel_BG_Color).setBackgroundColor(THEMECOLOR);
        animation_card_click = AnimationUtils.loadAnimation(SuMCreatorCHReq.this, R.anim.card_click);
        GetThisWenViewReady(((WebView)findViewById(R.id.SuMCreatorPanel_WebView)));
    }
    public void CloseSuMCreatorPanel(View view){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(animation_card_click == null) animation_card_click = AnimationUtils.loadAnimation(SuMCreatorCHReq.this, R.anim.card_click);
                view.startAnimation(animation_card_click);
                SuMCreatorCHReq.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
    public void AddAReq_SuMCreator(View view){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(animation_card_click == null) animation_card_click = AnimationUtils.loadAnimation(SuMCreatorCHReq.this, R.anim.card_click);
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
                if(!url.contains("/CreatorChapterPanel.aspx")) {
                    SuMCreatorCHReq.this.finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });
        webViewx.setWebChromeClient(new WebChromeClient() {

            // openFileChooser for Android 3.0+

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType){
                mUploadMessage = uploadMsg;
                openImageChooser();
            }

            // For Lollipop 5.0+ Devices

            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                mUploadMessages = filePathCallback;
                openImageChooser();
                return true;
            }

            // openFileChooser for Android < 3.0

            public void openFileChooser(ValueCallback<Uri> uploadMsg){
                openFileChooser(uploadMsg, "");
            }

            //openFileChooser for other Android versions

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }


            private void openImageChooser() {
                try {
                    File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), /*"FolderName"*/String.valueOf(System.currentTimeMillis()));
                    if (!imageStorageDir.exists()) {
                        imageStorageDir.mkdirs();
                    }
                    File file = new File(String.valueOf(System.currentTimeMillis())+imageStorageDir + File.separator +String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mCapturedImageURI = Uri.fromFile(file);

                    final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                    Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

                    startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        /*webViewx.setWebChromeClient(new WebChromeClient() {

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
                intent.setType("image/*");
                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                //intent.setAction(Intent.ACTION_GET_CONTENT);
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


        });*/
        WebSettings contentWebViewSettings = webViewx.getSettings();
        contentWebViewSettings.setUseWideViewPort(true);
        contentWebViewSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        contentWebViewSettings.setAllowFileAccessFromFileURLs(true);
        contentWebViewSettings.setAllowUniversalAccessFromFileURLs(true);
        webViewx.getSettings().setBuiltInZoomControls(false);
        contentWebViewSettings.setSupportZoom(false);
        contentWebViewSettings.setDisplayZoomControls(false);
        contentWebViewSettings.setAppCacheEnabled(true);
        webViewx.setBackgroundColor(Color.TRANSPARENT);
        webViewx.addJavascriptInterface(SuMCreatorCHReq.this, "androidAPIs");
        //webViewx.setWebViewClient(new CustomWebViewClient());
        webViewx.getSettings().setJavaScriptEnabled(true);
        webViewx.getSettings().setGeolocationEnabled(true);
        webViewx.getSettings().setAllowFileAccess(true);
        webViewx.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewx.loadUrl("https://sum-manga.azurewebsites.net/SuMCreator/CreatorChapterPanel.aspx?MID="+MSG_MID);
        webViewx.setVisibility(View.VISIBLE);
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {

            if (null == mUploadMessage && null == mUploadMessages) {
                return;
            }

            if (null != mUploadMessage) {
                handleUploadMessage(requestCode, resultCode, data);

            } else if (mUploadMessages != null) {
                handleUploadMessages(requestCode, resultCode, data);
            }
        }





    }

    private void handleUploadMessage(final int requestCode, final int resultCode, final Intent data) {
        Uri result = null;
        try {
            if (resultCode != RESULT_OK) {
                result = null;
            } else {
                // retrieve from the private variable if the intent is null

                result = data == null ? mCapturedImageURI : data.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;

        // code for all versions except of Lollipop
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            result = null;

            try {
                if (resultCode != RESULT_OK) {
                    result = null;
                } else {
                    // retrieve from the private variable if the intent is null
                    result = data == null ? mCapturedImageURI : data.getData();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "activity :" + e, Toast.LENGTH_LONG).show();
            }

            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }

    } // end of code for all versions except of Lollipop






    private void handleUploadMessages(final int requestCode, final int resultCode, final Intent data) {
        Uri[] results = null;
        try {
            if (resultCode != RESULT_OK) {
                results = null;
            } else {
                if (data != null) {
                    String dataString = data.getDataString();
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        results = new Uri[clipData.getItemCount()];
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            results[i] = item.getUri();
                        }
                    }
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                } else {
                    results = new Uri[]{mCapturedImageURI};
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mUploadMessages.onReceiveValue(results);
        mUploadMessages = null;
    }

    /*@Override
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


    }*/

    @JavascriptInterface
    public void ViewCreatorItemX(){

    }
    @JavascriptInterface
    public void SendACreatorReq(){

    }
    @JavascriptInterface
    public void CurrPageURL(String URL){
        if(!URL.contains("/CreatorChapterPanel.aspx")) {
            SuMCreatorCHReq.this.finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
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
