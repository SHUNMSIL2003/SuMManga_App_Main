package com.summanga.android;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] Theme;
    private final String[] web;
    private final String[] Imageurl;

    public CustomGrid(Context c, String[] web, String[] Imageurl, String[] ThemeColorRGB) {
        mContext = c;
        this.Imageurl = Imageurl;
        this.web = web;
        this.Theme = ThemeColorRGB;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.card_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.idMangaTitle);
            RelativeLayout textViewBG = ((RelativeLayout)grid.findViewById(R.id.idMangaTitle_Co));
            ImageView imageView = (ImageView) grid.findViewById(R.id.idMangaCover);
            textView.setText(web[position]);
            String RootColor = String.format("#%02X%02X%02X", Integer.parseInt(Theme[position].split(",")[0]), Integer.parseInt(Theme[position].split(",")[1]), Integer.parseInt(Theme[position].split(",")[2]));
            textViewBG.setBackgroundColor(Color.parseColor(RootColor));
            String Image_URL = "https://sum-manga.azurewebsites.net" + Imageurl[position];

            final Bitmap[] MangaCoverBitmap = {null};
            Thread ThreadP0 = new Thread(() -> {
                MangaCoverBitmap[0] = getBitmapFromURL(Image_URL);
                //System.out.println("Second thread is running.");
            });
            ThreadP0.start();
            try {
                ThreadP0.join();
                Bitmap ResizedIMGForFrame = Bitmap.createScaledBitmap(MangaCoverBitmap[0], 232, 348, false);
                imageView.setImageBitmap(ResizedIMGForFrame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*Bitmap ResizedIMGForFrame = Bitmap.createScaledBitmap(MangaCoverBitmap, 232, 348, false);
            imageView.setImageBitmap(ResizedIMGForFrame);*/
            //MangaCoverBitmap.recycle();
            //ResizedIMGForFrame.recycle();

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
