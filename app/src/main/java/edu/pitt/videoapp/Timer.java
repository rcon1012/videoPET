package edu.pitt.videoapp;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Calendar;
import android.os.Handler;
import android.os.Message;
import android.os.CountDownTimer;
import android.widget.LinearLayout;
/**
 * Created by Luke on 10/27/2015.
 * With the help of Jake
 */

public class Timer //extends TextClock
{
    private LinearLayout centerLayout;
    CountDownTimer newTimer;
    Handler Alarm;
    String Message;
    long timer=100000;
    long message = 9000;

    public Timer()
    {
        newTimer = new CountDownTimer(timer, 1000) {
            public void onTick(long msLeft) {
                Log.d("Timer", msLeft / 60000 + ": " + (msLeft / 1000) % 60);
                TextClock.S
                if (msLeft < 60000) {
                    //mTextField.setText("One Minute left");
                    Log.d("Timer", "One minute left");
                }
            }

            public void onFinish() {
                //mTextField.setText("");
                Log.d("Timer", "Timer finished");
            }


        };

        /*Alarm=new Handler();
        Message alarmMessage=new Message();
        Message="1 minute left";*/
    }

    public void start()
    {
        newTimer.start();
    }
    public void cancel()
    {
        newTimer.cancel();
    }
    //if( postDelayed(Alarm, 17400));
    /*public boolean setAlarmTime()
    {
        return postAtTime(Alarm, Message, 17400);
    }*/


}