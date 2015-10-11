package edu.pitt.videoapp;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Christopher on 10/3/2015.
 */
public class CameraManager {

    ArrayList<Camera> cameraArrayList;

    public CameraManager (){
        cameraArrayList = new ArrayList<Camera>();
    }

    public Camera addCamera (Activity context) {
        Camera c = new Camera(context);
        cameraArrayList.add(c);

        RelativeLayout parent = (RelativeLayout) context.findViewById(R.id.stageActivityLayout);
        CameraView cv = new CameraView(context);
        parent.addView(cv);
        return c;
    }

    public void removeCamera ( Camera c ) {
        cameraArrayList.remove(c);
    }

}
