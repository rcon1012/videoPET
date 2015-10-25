package edu.pitt.videoapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by jake on 10/11/15.
 */
public class CameraView extends ImageView {
    private final String TAG = CameraView.class.getSimpleName();
    private Camera cam;

    public CameraView(Context context, Camera cam) {
        super(context);

        this.cam = cam;
        Drawable cameraDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraDrawable = getResources().getDrawable(R.drawable.camera, context.getTheme());
        } else {
            cameraDrawable = getResources().getDrawable(R.drawable.camera);
        }

        Activity act = (Activity) context;
        int screenWidth = act.getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = act.getWindowManager().getDefaultDisplay().getHeight();
        setOnTouchListener(new CameraTouchListener(this, screenHeight, screenWidth));


        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

        super.setImageDrawable(cameraDrawable);
    }

    private class CameraTouchListener implements OnTouchListener {
            int windowX;
            int windowY;
            CameraView cv;

            public CameraTouchListener(CameraView cameraView, int screenHeight, int screenWidth) {
                cv = cameraView;
                windowX = screenWidth + 100;
                windowY = screenHeight + 150;
            }

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cv.getLayoutParams();

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                        int xCoord = (int) event.getRawX() - 100;
                        int yCoord = (int) event.getRawY() - 150;
                        if(xCoord > windowX) {
                            xCoord = windowX;
                        } else if(xCoord < 0) {
                            xCoord = 0;
                        }
                        if(yCoord > windowY) {
                            yCoord = windowY;
                        } else if(yCoord < 0) {
                            yCoord = 0;
                        }
                        params.leftMargin = xCoord;
                        params.topMargin = yCoord;
                        Log.d(TAG, "onTouch x = " + xCoord + "; y = " + yCoord);
                        if(cam != null) {
                            cam.setCoordinates(xCoord, yCoord);
                        } else {
                            Log.d(TAG, "onTouch cam is null");
                        }
                        cv.setLayoutParams(params);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        break;
                }
                return true;
            }
        }

}
