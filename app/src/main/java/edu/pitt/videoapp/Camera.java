package edu.pitt.videoapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by Christopher on 10/3/2015.
 */
public class Camera {
    final int DEFAULT_X_COORDINATE = 100;
    final int DEFAULT_Y_COORDINATE = 100;

    private int xCoord;
    private int yCoord;
    private CameraView cameraView;

    public Camera (Context ctx) {
        // Assign image variable to drawable resource when we have one

        xCoord = DEFAULT_X_COORDINATE;
        yCoord = DEFAULT_Y_COORDINATE;
    }

    public void setView(CameraView cv) {
        cameraView = cv;
    }
}
