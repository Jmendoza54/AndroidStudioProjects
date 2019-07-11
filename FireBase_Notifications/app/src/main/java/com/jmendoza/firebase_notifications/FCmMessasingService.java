package com.jmendoza.firebase_notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCmMessasingService extends FirebaseMessagingService {

    private static final String DESCUENTO = "descuento";
    public FCmMessasingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().size() > 0 && remoteMessage.getNotification() != null){
            sendNotification(remoteMessage);
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        float desc = Float.valueOf(remoteMessage.getData().get(DESCUENTO));

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(DESCUENTO, desc);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            notificationBuilder.setColor(desc > .4?
                    ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) :
                    ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = desc < .10? getString(R.string.low_channel_id) : getString(R.string.normal_channel_id);
            String channelName = desc < .10? getString(R.string.low_channel_name) : getString(R.string.normal_channel_name);

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 200 , 50});

            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }
            notificationBuilder.setChannelId(channelId);

        }


        if(notificationManager != null){
            notificationManager.notify("", 0, notificationBuilder.build());
        }
    }

    @Override
    public void onNewToken(String token) {

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.d("Refreshed token" , token);
    }
}
