package com.summanga.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;


public class SuMHits extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SuMStaticVs.RedirectFromSuMNotiURL = "Hits";
        Intent intent = new Intent(this, SplashScreen.class);
        this.startActivity(intent);
        this.finishAffinity();
    }


}