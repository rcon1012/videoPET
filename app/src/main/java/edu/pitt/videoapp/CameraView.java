package edu.pitt.videoapp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

/**
 * Created by jake on 10/11/15.
 */
public class CameraView extends ImageView {
    private static final String TAG = CameraView.class.getSimpleName();
    private Camera cam;

    public CameraView(final Activity activity, Camera cam) {
        super(activity);

        this.cam = cam;
        Drawable cameraDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraDrawable = getResources().getDrawable(R.drawable.camera, activity.getTheme());
        } else {
            cameraDrawable = getResources().getDrawable(R.drawable.camera);
        }

        int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();

        setOnTouchListener(new CameraTouchListener(this, screenHeight, screenWidth));
        setOnLongClickListener(new CameraLongClickListener(this, activity));

        super.setImageDrawable(cameraDrawable);
    }

    private class CameraTouchListener implements View.OnTouchListener {
        private int windowX;
        private int windowY;
        private CameraView cameraView;

        public CameraTouchListener(CameraView cameraView, int screenHeight, int screenWidth) {
            this.cameraView = cameraView;
            windowX = screenWidth;
            windowY = screenHeight;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cameraView.getLayoutParams();

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    int xCoord = (int) event.getRawX() - 100;
                    int yCoord = (int) event.getRawY() - 150;
                    if (xCoord > windowX) {
                        xCoord = windowX;
                    } else if (xCoord < 0) {
                        xCoord = 0;
                    }
                    if (yCoord > windowY) {
                        yCoord = windowY;
                    } else if (yCoord < 0) {
                        yCoord = 0;
                    }

                    params.leftMargin = xCoord;
                    params.topMargin = yCoord;
                    Log.d(TAG, "onTouch x = " + xCoord + "; y = " + yCoord);

                    cam.setCoordinates(xCoord, yCoord);

                    cameraView.setLayoutParams(params);
                    break;
                case MotionEvent.ACTION_DOWN:
                    break;
            }
            return false;
        }
    }

    private class CameraLongClickListener implements View.OnLongClickListener{
        private CameraView cameraView;
        private Activity activity;

        CameraLongClickListener(CameraView cameraView, Activity activity) {
            this.cameraView = cameraView;
            this.activity = activity;
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "Long click worked!!!!");

            PopupMenu popup = new PopupMenu(activity, v);
            popup.setOnMenuItemClickListener(cam.getManager());

            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_camera, popup.getMenu());
            popup.show();

            return false;
        }
    }
}
