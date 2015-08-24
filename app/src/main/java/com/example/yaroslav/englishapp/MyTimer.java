package com.example.yaroslav.englishapp;

import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

/**
 * Created by yaroslav on 14.07.15.
 */
public class MyTimer {
    TextView time;
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int t = 1;
    int secs = 0;
    int mins = 0;
    int milliseconds = 0;
    public Runnable updateTimer;
    Handler handler = new Handler();
    public MyTimer(TextView tvTime){
        time = tvTime;
          updateTimer = new Runnable() {
            public void run() {
                timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
                updatedtime = timeSwapBuff + timeInMilliseconds;
                secs = (int) (updatedtime / 1000);
                mins = secs / 60;
                secs = secs % 60;
                milliseconds = (int) (updatedtime % 1000);
                time.setText("" + mins + ":" + String.format("%02d", secs) + ":"
                        + String.format("%03d", milliseconds));
                time.setTextColor(Color.RED);
                handler.postDelayed(this, 0);
            }};
    }
    public void startTimer(){
        starttime = SystemClock.uptimeMillis();
        handler.postDelayed(updateTimer, 0);
    }
    public void stopTimer(){
        time.setTextColor(Color.BLUE);
        timeSwapBuff += timeInMilliseconds;
        handler.removeCallbacks(updateTimer);
    }
    void resetTimer(){
        starttime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedtime = 0L;
        t = 1;
        secs = 0;
        mins = 0;
        milliseconds = 0;
        handler.removeCallbacks(updateTimer);
        time.setText("00:00:00");
    }

}
