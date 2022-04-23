package com.summanga.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SplashScreen extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
            SplashScreen.this.finish();
            return;
        }
        setContentView(R.layout.splashscreen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setStartOffset(0);
                fadeOut.setDuration(380);
                findViewById(R.id.SplashScreenLayout).startAnimation(fadeOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        findViewById(R.id.SplashScreenLayout).setVisibility(View.GONE);
                        SplashScreen.this.finish();

                    }
                }, 380);
            }
        }, SPLASH_TIME_OUT);
    }
}