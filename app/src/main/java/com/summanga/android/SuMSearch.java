package com.summanga.android;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class SuMSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SuMStaticVs.RedirectFromSuMNotiURL = "Search";
        Intent intent = new Intent(this, SplashScreen.class);
        this.startActivity(intent);
        this.finishAffinity();
    }



}