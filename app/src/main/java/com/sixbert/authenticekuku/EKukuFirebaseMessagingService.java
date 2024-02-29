package com.sixbert.authenticekuku;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class EKukuFirebaseMessagingService extends FirebaseMessagingService {
    NotificationManager notificationManager;
    private static final String TAG = "MyToken";

    public void onNewToken(@NonNull String token){
        Log.d(TAG, "Refreshed Token: " + token);
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "Message data payload" + remoteMessage.getFrom());

        //Check if message contains a data payload
        if(remoteMessage.getData().size()>0){
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }
        //check if it contains notification payload
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // TODO(developer): Handle FCM messages here.

        if (remoteMessage.getNotification() != null) {

            String title = remoteMessage.getNotification().getTitle();
            String description = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Description: " + description);


            showNotification(remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    private RemoteViews getCustomDesign(String title, String body) {

            RemoteViews remoteViews = new RemoteViews(
                    getApplicationContext().getPackageName(),
                    R.layout.notification);
            remoteViews.setTextViewText(R.id.title, title);
            remoteViews.setTextViewText(R.id.body, body);
            remoteViews.setImageViewResource(R.id.icon, R.mipmap.ic_ekuku_launcher_round);

            return remoteViews;

        }

        //Method to display the notifications

        public void showNotification(String title, String body){
             Intent intent = new Intent(this, MainActivity.class);

             //assign channel Id
            /*FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
             * the activities present in the activity stack
             * on the top of the activity that is to be launched*/
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             //Pass the intent to pendingIntent to start
            //the next activity
             PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                     PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);


        String channelId = "fcm_default_channel";
        //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_ekuku_launcher_round)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000,1000})
                        .setOnlyAlertOnce(true)
                        .setContentIntent(pendingIntent);

       // NotificationManager notificationManager =
       //         (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

       // Since android Oreo notification channel is needed.
            notificationBuilder = notificationBuilder.setContent(
                    getCustomDesign(title, body));

            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}


