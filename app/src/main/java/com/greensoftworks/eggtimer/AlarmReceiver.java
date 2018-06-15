package com.greensoftworks.eggtimer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyMgr;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Build.VERSION.SDK_INT >= 26) {
            int notificationImportance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("myChannelId", "My Channel", notificationImportance);
            channel.setDescription("EggNotification");
            mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            if (mNotifyMgr != null) {
                mNotifyMgr.createNotificationChannel(channel);
            }
             mBuilder = new NotificationCompat.Builder(context, "myChannelId");
        }
        else {
            mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(context);
        }
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.chickenx4);

            mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
            mBuilder.setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false));
            mBuilder.setContentTitle("Egg Timer");
            mBuilder.setContentText(intent.getStringExtra("EGG_TYPE"));
            mBuilder.setOngoing(false);
            mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            mBuilder.setAutoCancel(true);

            Intent i = new Intent(context, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 88, i, PendingIntent.FLAG_ONE_SHOT);

            try {
                Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/"
                        + R.raw.egg);
                mBuilder.setSound(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mBuilder.setContentIntent(pendingIntent);
            assert mNotifyMgr != null;
            mNotifyMgr.notify(1, mBuilder.build());
    }
}
