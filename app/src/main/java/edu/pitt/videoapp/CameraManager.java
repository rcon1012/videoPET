package edu.pitt.videoapp;

import android.app.Activity;
import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

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

    public Camera addCamera (final Activity context) {
        Camera c = new Camera(context);
        cameraArrayList.add(c);

        RelativeLayout parent = (RelativeLayout) context.findViewById(R.id.stageActivityLayout);
        CameraView cv = new CameraView(context);
        parent.addView(cv);
        cv.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Log.d(TAG, "Long click worked!!!!");
                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(CameraManager.this);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_camera, popup.getMenu());
                popup.show();
                return true;
            }
        });
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
