package edu.pitt.videoapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Christopher on 10/3/2015.
 */
public class CameraManager {

    ArrayList<Camera> cameraArrayList;

    public CameraManager (){
        cameraArrayList = new ArrayList<Camera>();
    }

    public Camera addCamera (Context context) {
        Camera c = new Camera(context);
        cameraArrayList.add(c);
        return c;
    }

    public void removeCamera ( Camera c ) {
        cameraArrayList.remove(c);
    }

}
