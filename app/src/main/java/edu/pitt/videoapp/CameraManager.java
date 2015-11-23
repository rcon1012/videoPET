package edu.pitt.videoapp;

import android.app.Activity;
import android.view.MenuItem;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import android.util.Log;
/**
 * A singleton class for holding all instances of the camera objects.
 * This class is used to write the appropriate camera data to the project
 * configuration.
 *
 * @author christopher
 */
public class CameraManager implements Iterable<Camera> {

    private ArrayList<Camera> cameraArrayList;
    private long sequenceStartTime;
    private long sequenceEndTime;
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

    public void removeActive() {
        for (Camera c : cameraArrayList) {
            if (c.isActive())
                c.removeActive();
        }
    }
    public ArrayList<Cut> getCutlist() {
        ArrayList<Cut> cuts = new ArrayList<Cut>();
        for(Camera c: cameraArrayList) {
            cuts.addAll(c.getAllCuts());
        }
        Collections.sort(cuts);
        for(int i = 1; i < cuts.size(); i++) {
            long recordOut = cuts.get(i).getRecordIn();
            long sourceOut = cuts.get(i).getSourceIn();

            cuts.get(i - 1).setOutTimes(sourceOut, recordOut);
        }
        Cut lastCut = cuts.get(cuts.size() - 1);
        long lastRecordIn = lastCut.getRecordIn();
        long lastSourceIn = lastCut.getSourceIn();
        long delta = (sequenceEndTime - sequenceStartTime) - lastRecordIn;
        lastCut.setOutTimes(lastSourceIn + delta, lastRecordIn + delta);
        return cuts;
    }

    public long getSequenceStartTime() {
        return sequenceStartTime;
    }

    public void setSequenceStartTime(long sequenceStartTime) {
        this.sequenceStartTime = sequenceStartTime;
    }

    public long getSequenceEndTime() {
        return sequenceEndTime;
    }

    public void setSequenceEndTime(long sequenceEndTime) {
        this.sequenceEndTime = sequenceEndTime;
    }

    public Iterator<Camera> iterator() {
        return cameraArrayList.iterator();
    }
}
