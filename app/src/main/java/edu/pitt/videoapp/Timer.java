package edu.pitt.videoapp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
//@TargetAPI(Build.VERSION_CODES.GINGERBREAD)
//@SuppressLint("NewAPI")


public class Timer extends Activity
{
    //private LinearLayout centerLayout;
    CountDownTimer newTimer;
    Handler Alarm;
    String Message;
    long timer=1800000;                         //set to 30 minutes = 1800000
    long message = 9000;
    TextView camera_text_label;
    LinearLayout centerLayout;

    public Timer(TextView camera, LinearLayout center)
    {
        camera_text_label=camera;
        centerLayout=center;
        //textClock=(TextClock) findViewById(R.id.textClock);
        newTimer = new CountDownTimer(timer, 1000) {
            public void onTick(long msLeft) {
                //Log.d("Timer", msLeft / 60000 + ": " + (msLeft / 1000) % 60);

                camera_text_label.setText( msLeft / 60000 + ": " + (msLeft / 1000) % 60);
                //super.clock = (TextClock) activity.findViewById(R.id.textClock);
                //super.clock.setId(View.generateViewId());
                //TextClock.setText(("Timer")+ msLeft / 60000 + ": " + (msLeft / 1000) % 60);
                if (msLeft < 60000) {
                    //mTextField.setText("One Minute left");
                    centerLayout.setBackgroundColor(Color.parseColor("yellow"));
                    //Log.d("Timer", "One minute left");
                }
            }

            public void onFinish() {
                //mTextField.setText("");
                camera_text_label.setText("00:00");
                Log.d("Timer", "Timer finished");
            }


        };

        /*Alarm=new Handler();
        Message alarmMessage=new Message();
        Message="1 minute left";*/
    }
    //public void stop()
    //{
    //
    //}
    public void start()
    {
        newTimer.start();
    }                 //starts the countdown
    public void cancel()
    {
        newTimer.cancel();
    }               //cancels countdown
    //if( postDelayed(Alarm, 17400));
    /*public boolean setAlarmTime()
    {
        return postAtTime(Alarm, Message, 17400);
    }*/


}