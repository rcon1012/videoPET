package edu.pitt.videoapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Christopher on 10/3/2015.
 */
public class Camera implements Parcelable {
    final float DEFAULT_X_COORDINATE = 0;
    final float DEFAULT_Y_COORDINATE = 0;
    float xCoord;
    float yCoord;
    String desc;
    private static final String TAG = Camera.class.getSimpleName();
    private boolean active;
    private CameraManager manager;
    private String camLabel;

    private String stageTarget;

    private String sequenceLabel;

    private Rig camRig;

    public StageActivity stageActivity;
    private ArrayList<Cut> cutList;
    private long startTime;

    // New Camera constructor -final
    public Camera(StageActivity activity){
        this.camRig = new Rig(activity);
    }

    /* This constructor has to be used when loading a project, because the StageActivity doesn't
        exist at the time of camera instantiation. Instead, we have to add a reference and create
        the rig in the onCreate method of the StageActivity
    */
    public Camera() {
    }

    public void setStageActivity(StageActivity sa) {
        stageActivity = sa;
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
        camRig.setDesc(s);
    }

    // get desc -final
    public String getDesc(){
        return camRig.getDesc();
    }

    // get lock -final
    public boolean getLock(){
        return camRig.getLock();
    }

    // set lock -final
    public void setLock(boolean lock){
        camRig.setLock(lock);
    }

    // Asks if camera is alive or deleted -final
    public boolean isAlive(){
        return !camRig.wasDeleted();
    }

    // Gets the cameras id
    public int getId () {
        return camRig.getId();
    }

    public boolean isActive () {
        return camRig.isActive ();
    }

    public void removeActive () {
        camRig.removeActive();
    }

    // Gets the camera's rig
    public Rig getCamRig() {
        return camRig;
    }

    /*
        These setters only set the value, they do not set
        the screen. Used for loading set-up configurations.
     */
    public void inactiveSetXY(float xCoord, float yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public float[]inActiveGetXY() {
        return new float[] {this.xCoord, this.yCoord};
    }

    public void inactiveSetLabel(String label) {
        this.camLabel = label;
    }
    public String inactiveGetLabel() {
        return this.camLabel;
    }

    public void inactiveSetNotes(String notes) {
        this.desc = notes;
    }
    public String inactiveGetNote() {
        return this.desc;
    }

    public void inactiveSetStageTarget(String stageTarget) {
        this.stageTarget = stageTarget;
    }
    public String inactiveGetStageTarget() {
        return this.stageTarget;
    }

    public void activate() {
        this.active = true;

        long cutTime = System.currentTimeMillis();
        // TODO: after rebase, change this to cutTime - stageActivity.getCameraManager().recordStart
        long recordTime = cutTime - startTime;
        long sourceTime = cutTime - startTime;

        cutList.add(new Cut(sourceTime, recordTime, camRig.getLabel()));
    }

    public void deactivate() {
        this.active = false;
    }

    public CameraManager getManager() {
        return manager;
    }

    public void setSequenceLabel(String newLabel){
        this.sequenceLabel = newLabel;
    }

    public String getSequenceLabel(){
        return this.sequenceLabel;
    }

    // Parcel functions for Camera object so it can be passed to other Activites
    public Camera(Parcel in){
        xCoord = in.readFloat();
        yCoord = in.readFloat();
        camLabel = in.readString();
        desc = in.readString();
        stageTarget = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(xCoord);
        dest.writeFloat(yCoord);
        dest.writeString(camLabel);
        dest.writeString(desc);
        dest.writeString(stageTarget);
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

        if (Float.compare(camera.xCoord, xCoord) != 0) return false;
        if (Float.compare(camera.yCoord, yCoord) != 0) return false;
        if (camLabel != null ? !camLabel.equals(camera.camLabel) : camera.camLabel != null)
            return false;
        return !(desc != null ? !desc.equals(camera.desc) : camera.desc != null);

    }

    @Override
    public int hashCode() {
        int result = (xCoord != +0.0f ? Float.floatToIntBits(xCoord) : 0);
        result = 31 * result + (yCoord != +0.0f ? Float.floatToIntBits(yCoord) : 0);
        result = 31 * result + (camLabel != null ? camLabel.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }

    public Collection<? extends Cut> getAllCuts() {
        return cutList;
    }
}
