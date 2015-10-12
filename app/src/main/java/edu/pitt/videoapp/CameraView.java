package edu.pitt.videoapp;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by jake on 10/11/15.
 */
public class CameraView extends ImageView {
    private final String TAG = CameraView.class.getSimpleName();
    Paint paint;

    private float initialX;
    private float initialY;

    public CameraView(Context context) {
        super(context);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xff101010);

        Drawable cameraDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraDrawable = getResources().getDrawable(R.drawable.camera, context.getTheme());
        } else {
            cameraDrawable = getResources().getDrawable(R.drawable.camera);
        }

        super.setImageDrawable(cameraDrawable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                initialY = event.getY();

                Log.d(TAG, "Action was DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Action was MOVE");
                break;

            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                float finalY = event.getY();

                Log.d(TAG, "Action was UP");

                if (initialX < finalX) {
                    Log.d(TAG, "Left to Right swipe performed");
                }

                if (initialX > finalX) {
                    Log.d(TAG, "Right to Left swipe performed");
                }

                if (initialY < finalY) {
                    Log.d(TAG, "Up to Down swipe performed");
                }

                if (initialY > finalY) {
                    Log.d(TAG, "Down to Up swipe performed");
                }

                break;

            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"Action was CANCEL");
                break;

            case MotionEvent.ACTION_OUTSIDE:
                Log.d(TAG, "Movement occurred outside bounds of current screen element");
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
    }

}
