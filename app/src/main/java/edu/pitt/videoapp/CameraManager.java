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
public class CameraManager  {

    ArrayList<Camera> cameraArrayList;
    private static final String TAG = CameraManager.class.getSimpleName();

    public CameraManager (){
        cameraArrayList = new ArrayList<Camera>();
    }

    /**
     * Creates and adds a new camera object to the list of cameras.
     *
     * @param c The camera instance created outside of the class
     *          and passed in.
     * @return The instance of the camera that was just created
     */
    public Camera addCamera (Camera c) {
        cameraArrayList.add(c);

        return c;
    }

    public void setAllLocks ( boolean lock ) {
        for (Camera c : cameraArrayList ) {
            c.setLock( lock );
        }
    }

    /**
     * Removes a camera from the camera list.
     * This does not remove it from the screen.
     *
     * @param c A reference to the camera in the queue which should be removed
     */
    public void removeCamera ( Camera c ) {
        cameraArrayList.remove(c);
    }
}
