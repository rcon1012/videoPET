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
    Drawable CAMERA_IMAGE;

    private int xCoord;
    private int yCoord;
    private ImageView image;

    public Camera (Context ctx){
        // Assign image variable to drawable resource when we have one

        xCoord = DEFAULT_X_COORDINATE;
        yCoord = DEFAULT_Y_COORDINATE;

        image = new ImageView(ctx.getApplicationContext());
        image.setImageDrawable(CAMERA_IMAGE);
    }

    public ImageView getImageInstance() {
        return image;
    }
}
