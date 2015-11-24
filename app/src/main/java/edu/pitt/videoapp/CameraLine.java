package edu.pitt.videoapp;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Christopher on 11/11/2015.
 */
public class CameraLine extends View {

    Paint paint = new Paint();
    Rig cam;
    Rig stage;

    public CameraLine ( Activity activity, Rig cam, Rig stage ) {
        super(activity);
        paint.setColor(Color.DKGRAY);
        this.cam = cam;
        this.stage = stage;
    }

    @Override
    public void onDraw(Canvas canvas) {
        float[] camxy = cam.getMidXY();
        float[] stagexy = stage.getMidXY();
        paint.setStrokeWidth(2);
        canvas.drawLine(camxy[0], camxy[1], stagexy[0] - 24, stagexy[1], paint);
    }

}
