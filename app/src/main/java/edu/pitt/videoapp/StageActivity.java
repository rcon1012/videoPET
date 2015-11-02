package edu.pitt.videoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class StageActivity extends AppCompatActivity {
    private static final String TAG = StageActivity.class.getSimpleName();
    private Menu menu;
    private CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        cameraManager = new CameraManager();

        if(savedInstanceState != null)
        {
            ArrayList<Camera> loadCameras = savedInstanceState.getParcelableArrayList("cameras");
            for(Camera cameras : loadCameras)
            {
                cameraManager.addCamera(cameras);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stage, menu);

        int i = 1;
        View v = menu.findItem(R.id.rename).getActionView();
        EditText txtrename = ( EditText ) v.findViewById(R.id.txt_rename);
        txtrename.setText("Sequence" + i, TextView.BufferType.EDITABLE); // Have it increment based on known sequences

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.pause_play_btn:


                // TODO Start / end sequence
                MenuItem settingsItem = menu.findItem(R.id.pause_play_btn);
                // Changes button icon to play/pause
                //if ( settingsItem.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_action_pause).getConstantState()))
                 //   settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_action_play));
                //else
                  //  settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_action_pause));
                return true;
            case R.id.add_camera:
                // DO NOT COMMIT THIS
                //Timer t = new Timer();
                //t.start();
                // DO NOT COMMIT ABOVE
                int screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
                int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
                Camera c = new Camera(this, cameraManager);
                // 250 / 150 is width and height of rig... TODO getters for height and width of center of rig
                c.setXY((float)screenWidth/2 - 250/2, (float)screenHeight/2 - 150/2);
                cameraManager.addCamera(c);
                return true;
            case R.id.add_stage:
                return true;
            case R.id.return_home:
                return true;

        }
    return super.onOptionsItemSelected(item);
    }



}
