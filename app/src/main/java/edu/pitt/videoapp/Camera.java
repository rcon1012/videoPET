package edu.pitt.videoapp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Christopher on 10/3/2015.
 */
public class Camera implements Parcelable {
    final int DEFAULT_X_COORDINATE = 100;
    final int DEFAULT_Y_COORDINATE = 100;

    private int xCoord;
    private int yCoord;
    private CameraView cameraView;

    public Camera (Context ctx) {
        // Assign image variable to drawable resource when we have one

        xCoord = DEFAULT_X_COORDINATE;
        yCoord = DEFAULT_Y_COORDINATE;
    }

    public Camera (int xCoord, int yCoord)
    {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void setView(CameraView cv) {
        cameraView = cv;
    }

    // Parcelling part
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
