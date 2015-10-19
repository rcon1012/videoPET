package edu.pitt.videoapp;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by jake on 10/11/15.
 */
public class CameraView extends ImageView {
    private final String TAG = CameraView.class.getSimpleName();
    Paint paint;

    private float initialX;
    private float initialY;

    RelativeLayout.LayoutParams parms;
    LinearLayout.LayoutParams par;
    float dx=0,dy=0,x=0,y=0;

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

}
