package edu.pitt.videoapp;
import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
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

                camera_text_label.setText( msLeft / 60000 + ": " + (msLeft / 1000) % 60);
                //super.clock = (TextClock) activity.findViewById(R.id.textClock);
                //super.clock.setId(View.generateViewId());
                //TextClock.setText(("Timer")+ msLeft / 60000 + ": " + (msLeft / 1000) % 60);
                if (msLeft < 60000) {
                    //mTextField.setText("One Minute left");
                    centerLayout.setBackgroundColor(Color.parseColor("yellow"));
                }
            }

            public void onFinish() {
                //mTextField.setText("");
                camera_text_label.setText("00:00");
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