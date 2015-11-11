package edu.pitt.videoapp;

import android.app.Activity;
import android.view.MenuItem;
import android.widget.PopupMenu;

import java.util.ArrayList;
import android.util.Log;
/**
 * A singleton class for holding all instances of the camera objects.
 * This class is used to write the appropriate camera data to the project
 * configuration.
 *
 * @author christopher
 */
public class StageManager  {

    ArrayList<Rig> stageArrayList;

    public StageManager (){
        stageArrayList = new ArrayList<Rig>();
    }

    /**
     * Creates and adds a new camera object to the list of cameras.
     *
     * @param r The camera instance created outside of the class
     *          and passed in.
     */
    public void addStage (Rig r) {
        stageArrayList.add(r);
    }

    /**
     * Removes a camera from the camera list.
     * This does not remove it from the screen.
     *
     * @param r A reference to the camera in the queue which should be removed
     */
    public void removeCamera ( Rig r ) {
        stageArrayList.remove(r);
    }
}
