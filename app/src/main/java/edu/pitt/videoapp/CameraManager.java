package edu.pitt.videoapp;

import java.util.ArrayList;

/**
 * Created by Christopher on 10/3/2015.
 */
public class CameraManager {

    ArrayList<Camera> cm;

    public CameraManager (){
        cm = new ArrayList<Camera>();
    }

    public void addCamera () {
        Camera c = new Camera();
        cm.add(c);
    }

    public void removeCamera ( Camera c ) {
        cm.remove(c);
    }

}
