package com.summanga.android;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SuMContantExplorer extends AppCompatActivity {

    int Device_Width;
    int Device_Height;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Device_Height = displayMetrics.heightPixels;
        Device_Width = displayMetrics.widthPixels;
        setContentView(R.layout.activity_main);
    }
}
