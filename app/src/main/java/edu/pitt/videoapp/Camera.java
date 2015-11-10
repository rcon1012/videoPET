package edu.pitt.videoapp;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Christopher on 10/3/2015.
 */
public class Camera implements Parcelable {
    final float DEFAULT_X_COORDINATE = 0;
    final float DEFAULT_Y_COORDINATE = 0;
    private static final String TAG = Camera.class.getSimpleName();

    private float xCoord; // remove
    private float yCoord; // remove
    //private CameraView cameraView;
    private boolean active;
    private CameraManager manager;
    private String camLabel; // remove

    private Rig camRig;
    private String desc = "";

    // Not sure if this is the best approach for accessing the activity from CameraView.java
    public Activity stage_activity;

    public Camera (final Activity activity, final CameraManager manager) {
        this.stage_activity = activity;
        this.camRig = new Rig(activity);


        xCoord = DEFAULT_X_COORDINATE;
        yCoord = DEFAULT_Y_COORDINATE;

        //cameraView = new CameraView(activity, this);

        /*
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RelativeLayout parent = (RelativeLayout) activity.findViewById(R.id.stageActivityLayout);
                parent.addView(cameraView);
            }
        });*/

    }

    public Camera() {
    }

    // New Camera constructor -final
    public Camera(Activity activity){
        this.camRig = new Rig(activity);
    }

    // New get position -final
    public float[] getXY(){
        return camRig.getXY();
    }

    // New set position -final
    public void setXY(float x, float y){
        camRig.setXY(x, y);
    }

    // New set label -final
    public void setLabel(String s){
        camRig.setLabel(s);
    }

    // New get label -final
    public String getLabel(){
        return camRig.getLabel();
    }

    // New set desc -final
    public void setDesc(String s){
        this.desc = s;
    }

    // get desc -final
    public String getDesc(){
        return this.desc;
    }

    // get lock -final
    public boolean getLock(){
        return camRig.getLock();
    }

    // set lick -final
    public void setLock(boolean lock){
        camRig.setLock(lock);
    }

    // Asks if camera is alive or deleted -final
    public boolean isAlive(){
        return !camRig.wasDeleted();
    }


    /*
    public void setCameraView(CameraView cv) {
        cameraView = cv;
    }

    public CameraView getCameraView() {
        return cameraView;
    }
    */

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public CameraManager getManager() {
        return manager;
    }

    public void setCamLabel(String newLabel){
        this.camLabel = newLabel;
    }

    public String getCamLabel(){
        return this.camLabel;
    }


    // Parcel functions for Camera object so it can be passed to other Activites
    public Camera(Parcel in){
        xCoord = in.readFloat();
        yCoord = in.readFloat();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(xCoord);
        dest.writeFloat(yCoord);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Camera createFromParcel(Parcel in) {
            return new Camera(in);
        }

        public Camera[] newArray(int size) {
            return new Camera[size];
        }
    };
    // END: parcel functions

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Camera)) return false;

        Camera camera = (Camera) o;

        if (xCoord != camera.xCoord) return false;
        return yCoord == camera.yCoord;

    }

    @Override
    public int hashCode() {
        float result = xCoord;
        result = 31 * result + yCoord;
        return (int)result;
    }
}
