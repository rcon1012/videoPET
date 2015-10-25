package edu.pitt.videoapp;

import android.app.Activity;
import android.view.MenuItem;
import android.widget.PopupMenu;

import java.util.ArrayList;
import android.util.Log;
/**
 * Created by Christopher on 10/3/2015.
 */
public class CameraManager implements PopupMenu.OnMenuItemClickListener {

    ArrayList<Camera> cameraArrayList;
    private static final String TAG = CameraManager.class.getSimpleName();

    public CameraManager (){
        cameraArrayList = new ArrayList<Camera>();
    }

    public Camera addCamera (final Activity activity) {
        Camera c = new Camera(activity, this);
        cameraArrayList.add(c);

        return c;
    }

    public void removeCamera ( Camera c ) {
        cameraArrayList.remove(c);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.add_camera_label:
                Log.d(TAG, "LABEL CLICKED");
                return true;
        }
        return true;
    }
}
