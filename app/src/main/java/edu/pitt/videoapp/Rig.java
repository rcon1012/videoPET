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

    private ImageButton moveLayoutButton;



    public Rig(Activity activity) {
        super(activity);
        init(activity);
        setupDrag();
        //int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        //int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        Log.d("Rig", "layoutId = " + getId());
    }

    private void init(Activity activity){
        RelativeLayout parent = (RelativeLayout) activity.findViewById(R.id.stageActivityLayout);
        inflate(getContext(), R.layout.rig_layout, parent);
        // Generate a new id for each item so the next rig can access default xml ids
        this.moveLayoutButton = (ImageButton) activity.findViewById(R.id.moveButton);
        this.moveLayoutButton.setId(View.generateViewId());
    }
    private void setupDrag() {
        this.moveLayoutButton.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;
            public boolean onTouch(View view, MotionEvent event) {
                view = (View) view.getParent().getParent().getParent().getParent().getParent(); // This line needs to be altered depending on the xml structure
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
