package com.greensoftworks.eggtimer;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class ForegroundService extends Service {

    private static final String LOG_TAG = "ForegroundService";


    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";


    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Long time = intent.getLongExtra("TIMER", 0);

        System.out.println("String time in Service " + time);
        switch (Objects.requireNonNull(intent.getAction())) {
            case Constants.Action.STARTFOREGROUND_ACTION:

                startService(time);
                Toast.makeText(getApplicationContext(), "service started", Toast.LENGTH_LONG).show();
                break;

            case ACTION_STOP_FOREGROUND_SERVICE:
                Log.i(LOG_TAG, "Received Stop Intent");
                Toast.makeText(getApplicationContext(), "service stopped", Toast.LENGTH_LONG).show();
                stopService();
                break;
        }
        return START_STICKY;
    }

    private void startService(Long time){
        Log.i(LOG_TAG, "service started " + time);

        // create notification intent
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.chickenx4);

        // Notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Log.i(LOG_TAG, "after builder " + time);

        // big text
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Eggs");
        builder.setStyle(bigTextStyle);

        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false));
        builder.setContentIntent(pendingIntent);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setFullScreenIntent(pendingIntent, true);

        //Stop button
        Intent stopIntent = new Intent(this, ForegroundService.class);
        stopIntent.setAction(ACTION_STOP_FOREGROUND_SERVICE);
        PendingIntent pendingIntentStop = PendingIntent.getService(this, 0, stopIntent, 0);
        NotificationCompat.Action stopAction = new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Stop", pendingIntentStop);
        builder.addAction(stopAction);

        // start service
        startForeground(1, builder.build());
    }

    private void stopService(){
        Log.i(LOG_TAG, "service stopped");

        //stop service and remove notification
        stopForeground(true);

        stopSelf();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
