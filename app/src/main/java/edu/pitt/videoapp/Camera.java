package edu.pitt.videoapp;

import android.graphics.drawable.Drawable;

/**
 * Created by Christopher on 10/3/2015.
 */
public class Camera {
    final int DEFAULT_X_COORDINATE = 100;
    final int DEFAULT_Y_COORDINATE = 100;

    private int xCoord;
    private int yCoord;
    private Drawable image;

    public Camera (){
        // Assign image variable to drawable resource when we have one
        image = null;
        xCoord = DEFAULT_X_COORDINATE;
        yCoord = DEFAULT_Y_COORDINATE;
    }




}
