package com.summanga.android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

//Created By Prabhat Dwivedi
public class Notification_receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        PendingIntent pendingIntent;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SuM Manga",
                    "com.summanga.android",
                    NotificationManager.IMPORTANCE_HIGH);
            String channel_Id = channel.getId();
            CharSequence channel_name = channel.getName();
            Log.e("Notification_receiver", "SuM Manga :" + channel_Id);
            Log.e("SuM Manga", "SuM Manga :" + channel_name);

            channel.setDescription("Make entry of today's spending now");
            notificationManager.createNotificationChannel(channel);
        }

        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId("SuM Manga")
                .setContentTitle("SuM Manga")
                .setContentText("Let's do SuM reading!")
                .setAutoCancel(true);

//nder this you will find intent it is going to define after clicking notification which activity you want to redirect
        Intent repeatingIntent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 100, repeatingIntent,    PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(100, builder.build());
    }
}
