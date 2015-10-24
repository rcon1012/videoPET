package edu.pitt.videoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class StageActivity extends AppCompatActivity {

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
                cameraManager.addCamera(this);
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
                MenuItem settingsItem = menu.findItem(R.id.pause_play_btn);
                if ( settingsItem.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_action_play).getConstantState()))
                    settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_action_pause));
                else
                    settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_action_play));

            case R.id.add_camera:
                cameraManager.addCamera(this);
                return true;
            case R.id.add_stage:
                return true;
            case R.id.return_home:
                return true;
        }
    return super.onOptionsItemSelected(item);
    }

}
