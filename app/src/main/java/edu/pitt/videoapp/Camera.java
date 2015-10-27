package edu.pitt.videoapp;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RelativeLayout;

/**
 * Created by Christopher on 10/3/2015.
 */
public class Camera implements Parcelable {
    final int DEFAULT_X_COORDINATE = 100;
    final int DEFAULT_Y_COORDINATE = 100;
    private static final String TAG = Camera.class.getSimpleName();

    private int xCoord;
    private int yCoord;
    private CameraView cameraView;
    private boolean active;
    private CameraManager manager;
    private String camLabel;

    // Not sure if this is the best approach for accessing the activity from CameraView.java
    public Activity stage_activity;

    public Camera (final Activity activity, final CameraManager manager) {
        this.stage_activity = activity;
        xCoord = DEFAULT_X_COORDINATE;
        yCoord = DEFAULT_Y_COORDINATE;

        cameraView = new CameraView(activity, this);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RelativeLayout parent = (RelativeLayout) activity.findViewById(R.id.stageActivityLayout);
                parent.addView(cameraView);
            }
        });

    }

    public void setCoordinates(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public void setCameraView(CameraView cv) {
        cameraView = cv;
    }

    public CameraView getCameraView() {
        return cameraView;
    }

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

    public Camera (int xCoord, int yCoord)
    {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void setView(CameraView cv) {
        cameraView = cv;
    }

    // Parcel functions for Camera object so it can be passed to other Activites
    public Camera(Parcel in){
        xCoord = in.readInt();
        yCoord = in.readInt();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(xCoord);
        dest.writeInt(yCoord);
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
        int result = xCoord;
        result = 31 * result + yCoord;
        return result;
    }
}
