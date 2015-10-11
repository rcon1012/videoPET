package edu.pitt.videoapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jake on 10/11/15.
 */
public class CameraView extends View {
    Paint paint;

    public CameraView(Context context, AttributeSet as) {
        super(context, as);
        init();
    }

    public CameraView(Context context) {
        super(context);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(10f, 10f, 20f, 20f, paint);
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xff101010);
    }
}
