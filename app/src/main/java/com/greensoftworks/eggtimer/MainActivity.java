package com.greensoftworks.eggtimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    TextView timer;
    Button start, stop, reset;
    CharSequence eggs[] = new CharSequence[]{"Soft", "Mid Soft", "Soft Set", "Hard Boiled"};
    Boolean cluckSound = true;
    private static String eggType;
    private static final String LOG_TAG = "OnStop log";
    AlertDialog.Builder builder;
    MediaPlayer cluck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chooseEgg();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        startAlarmManager();
    }

    @Override
    protected void onPause() {
        cluck.stop();
        super.onPause();
        startAlarmManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        cluck.stop();
        super.onDestroy();
    }

    private void startAlarmManager(){
        long when = System.currentTimeMillis() + (Long.parseLong((String) timer.getText()) * 1000);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);

        intent.putExtra("EGG_TYPE", eggType);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Toast.makeText(this, "Alarm set you will receive a notification when egg is ready", Toast.LENGTH_LONG).show();

        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);
        }
    }

    private void init() {
        timer = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        reset = (Button) findViewById(R.id.reset);
        builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        cluck = MediaPlayer.create(this, R.raw.egg);
    }

    private void countdown(final long seconds) {

        countDownTimer = new CountDownTimer(seconds, 1000) {

            @Override
            public void onTick(long timeUntilFinished) {
                timer.setText(String.valueOf(timeUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timer.setText(String.valueOf("Egg Ready!"));
            }
        }.start();
    }

    private void chooseEgg() {

        init();

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Something here to stop timer
                cluck.pause();
                cluckSound = true;
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cluckSound = true;
                countDownTimer.cancel();
                timer.setText(String.valueOf(0));
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Pick a boiled egg option");
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setItems(eggs, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {
                            case 0:
                                countdown(10000);
                                eggType = "Soft";
                                break;
                            case 1:
                                countdown(20000);
                                eggType = "Medium Soft";
                                break;
                            case 2:
                                countdown(30000);
                                eggType = "Hard Soft";
                                break;
                            case 3:
                                countdown(300000);
                                eggType = "Hard";
                                break;
                        }
                        cluckSound = true;
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
