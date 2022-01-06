package com.example.easyplan.Model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;


import com.example.easyplan.R;
import com.example.easyplan.Controller.SplashScreenActivity;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

     NotificationManager mNotificationManager;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        // playing audio and vibration when user send request
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            r.setLooping(false);
        }

        // vibration
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 300, 300, 300};
        v.vibrate(pattern, -1);


        Intent resultIntent = new Intent(this, SplashScreenActivity.class);
        int requestCode = new Random().nextInt();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID");
        builder.setSmallIcon(R.drawable.fitness_logo); // the logo of the ui notification
        builder.setContentTitle(remoteMessage.getNotification().getTitle()); // title
        builder.setContentText(remoteMessage.getNotification().getBody()); // text body (the content)
        builder.setContentIntent(pendingIntent); // pending token to foreign apps, when click on notification it goes to SplashScreenActivity
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody())); // box style
        builder.setAutoCancel(true); // if user click somewhere in the screen the notification will be gone
        builder.setPriority(Notification.PRIORITY_MAX); // show the notification at the top of the notifications list of the user
        builder.setOnlyAlertOnce(true);

        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }


    // notificationId is a unique int for each notification that you must define
        requestCode = new Random().nextInt();
        mNotificationManager.notify(requestCode, builder.build());
    }

}


