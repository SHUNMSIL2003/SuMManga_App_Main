package com.summanga.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;


public class SuMSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SuMStaticVs.RedirectFromSuMNotiURL = "Settings";
        Intent intent = new Intent(this, SplashScreen.class);
        this.startActivity(intent);
        this.finishAffinity();
    }



}