package edu.pitt.videoapp;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

    public CameraView(Context context) {
        super(context);

        Drawable cameraDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraDrawable = getResources().getDrawable(R.drawable.camera, context.getTheme());
        } else {
            cameraDrawable = getResources().getDrawable(R.drawable.camera);
        }

        setOnTouchListener(new CameraTouchListener());


        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

        super.setImageDrawable(cameraDrawable);
    }

    private class CameraTouchListener implements OnTouchListener {
            int prevX;
            int prevY;
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                        params.topMargin += (int) event.getRawY() - prevY;
                        prevY = (int) event.getRawY();
                        params.leftMargin = (int) event.getRawX() - prevX;
                        prevX = (int) event.getRawX();
                        view.setLayoutParams(params);
                        return true;

                    case MotionEvent.ACTION_UP:
                        params.topMargin += (int) event.getRawY() - prevY;
                        params.leftMargin += (int) event.getRawX() - prevX;
                        view.setLayoutParams(params);
                        return true;

                    case MotionEvent.ACTION_DOWN:
                        prevX = (int) event.getRawX();
                        prevY = (int) event.getRawY();
                        params.bottomMargin = -2 * view.getHeight();
                        params.rightMargin = -2 *view.getWidth();
                        view.setLayoutParams(params);
                        return true;
                }

                return false;
            }
        }

}
