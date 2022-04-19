package com.summanga.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        startHeavyProcessing();

    }

    private void startHeavyProcessing(){
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //some heavy processing resulting in a Data String
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "whatever result you have";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            i.putExtra("data", result);
            startActivity(i);
            finish();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
