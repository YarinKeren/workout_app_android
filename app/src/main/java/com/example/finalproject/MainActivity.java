package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView timer ;
    ImageButton startPauseBtn, reset;
    Button chestPage,backPage,legsPage;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler timerHandler;
    int Seconds, Minutes, MilliSeconds ;
    Intent moveToChest, moveToBack, moveToLegs;
    MediaPlayer mp;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Initialize view vars
        timer = findViewById(R.id.tvTimer);
        startPauseBtn = findViewById(R.id.btStart);
        reset = findViewById(R.id.btReset);
        chestPage = findViewById(R.id.chestPage);
        backPage = findViewById(R.id.backPage);
        legsPage = findViewById(R.id.legsPage);

        //Create Intents to move to the workout pages
        moveToChest = new Intent(this, chestActivity.class);
        moveToBack = new Intent(this, backActivity.class);
        moveToLegs = new Intent(this, legsActivity.class);

        //Create a handler
        timerHandler = new Handler() ;

        //Start button onclick
        startPauseBtn.setOnClickListener(view -> {
            if(!timerHandler.hasCallbacks(timerThread)) {//Checks if the timer is running
                if(timer.getText().equals("00:00:00")){
                    //noinspection CallToThreadRun
                    playBell.run();
                }
                StartTime = SystemClock.uptimeMillis();
                timerHandler.postDelayed(timerThread, 0);//Calls thread
                reset.setEnabled(false);// Makes reset button unclickable
                //Toggle reset button & play buttons
                reset.setImageDrawable(getDrawable(R.drawable.lockreset));
                startPauseBtn.setImageDrawable(getDrawable(R.drawable.pausebtn));
            }
            else{//If it's running.. then pause
                TimeBuff += MillisecondTime;
                timerHandler.removeCallbacks(timerThread);//Remove all calls to thread
                reset.setEnabled(true);// Makes reset button clickable
                //Toggle reset button & play buttons
                reset.setImageDrawable(getDrawable(R.drawable.resetbtn));
                startPauseBtn.setImageDrawable(getDrawable(R.drawable.playbtn));
            }
        });
        //Reset button onclick
        reset.setOnClickListener(view -> {
            //Initialize timer back to 0 (L keeps long values)
            MillisecondTime = 0L ;
            StartTime = 0L ;
            TimeBuff = 0L ;
            UpdateTime = 0L ;
            Seconds = 0 ;
            Minutes = 0 ;
            MilliSeconds = 0 ;
            timer.setText(R.string.timeWatch);//Gets string from strings.xml
        });
        //Moves to chestActivity
        chestPage.setOnClickListener(v -> startActivity(moveToChest));
        //Moves to backActivity
        backPage.setOnClickListener(v -> startActivity(moveToBack));
        //Moves to legsActivity
        legsPage.setOnClickListener(v -> startActivity(moveToLegs));

    }//Closes onCreate

    //---GLOBAL SCOPE---
    //Thread for the timer to run in
    Thread timerThread = new Thread() {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            String st = String.valueOf(MilliSeconds);
            st = st.length() > 2 ? st.substring(0,2) : st; // Get only 2 digits
            timer.setText("" + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%02d", Integer.valueOf(st)));
            timerHandler.postDelayed(this, 0);
        }
    };
    //Thread to make bell sound when starting timer
    Thread playBell = new Thread(){
        @Override
        public void run() {
            super.run();
            mp = MediaPlayer.create(getBaseContext(), R.raw.boxingbell);
            mp.start();
            //Releases the mp instance
            mp.setOnCompletionListener(MediaPlayer::release);
        }
    };
}