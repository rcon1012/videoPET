package edu.pitt.videoapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Created by Christopher on 10/29/2015.
 */
public class Rig extends RelativeLayout{

    float dX, dY;


    public Rig(Activity activity) {
        super(activity);
        RelativeLayout parent = (RelativeLayout) activity.findViewById(R.id.stageActivityLayout);
        inflate(getContext(), R.layout.rig_layout, parent);
        ImageButton x = (ImageButton) activity.findViewById(R.id.imageButton);
        //int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        //int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        Log.d("Rig", "Before Touched");
        x.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                view = (View) view.getParent();
                Log.d("Rig", "Touched");
                switch (event.getActionMasked()) {

                    case MotionEvent.ACTION_DOWN:

                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        view.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }
}
