package edu.pitt.videoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class StageActivity extends AppCompatActivity {

    private Menu menu;

    private int screenWidth;
    private int screenHeight;

    private CameraManager cameraManager;
    private StageManager stageManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();

        cameraManager = new CameraManager();
        stageManager = new StageManager();

        setContentView(R.layout.activity_stage);

        Rig stage = new Rig(this, Rig.STAGE);
        stage.setXY((float) screenWidth / 2 - 300 / 2 + 24, 10);
        stageManager.addStage(stage);

        if(savedInstanceState != null)
        {
            ArrayList<Camera> loadCameras = savedInstanceState.getParcelableArrayList("cameras");
            for(Camera camera : loadCameras)
            {
                Camera c = new Camera(this);
                //c.setXY((float)camera.getX(), (float)camera.getY());
                c.setDesc(camera.getLabel());
                c.setLabel(camera.getLabel());
                cameraManager.addCamera(c);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stage, menu);

        View v = menu.findItem(R.id.rename).getActionView();
        EditText txtrename = ( EditText ) v.findViewById(R.id.txt_rename);

        return super.onCreateOptionsMenu(menu);
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
                MenuItem lockItem = menu.findItem(R.id.pause_play_btn);
                // Changes button icon to lock/unlock
                if ( lockItem.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_lock_open_white_48dp).getConstantState())) {
                    lockItem.setIcon(getResources().getDrawable(R.drawable.ic_lock_outline_white_48dp));
                    stageManager.setAllLocks( true );
                    cameraManager.setAllLocks ( true ) ;
                }
                else {
                    lockItem.setIcon(getResources().getDrawable(R.drawable.ic_lock_open_white_48dp));
                    stageManager.setAllLocks( false );
                    cameraManager.setAllLocks ( false ) ;
                }
                return true;
            case R.id.add_camera:
                // DO NOT COMMIT THIS
                //Timer t = new Timer();
                //t.start();
                // DO NOT COMMIT ABOVE
                Camera c = new Camera(this);
                // 250 / 150 is width and height of rig... TODO getters for height and width of center of rig
                c.setXY((float)screenWidth/2 - 250/2, (float)screenHeight/2 - 150/2);
                cameraManager.addCamera(c);
                return true;
            case R.id.add_stage:
                Rig stage = new Rig(this, Rig.STAGE);
                stage.setXY((float)screenWidth/2 - 300/2 + 24, 10);
                stageManager.addStage(stage);
                return true;
            case R.id.return_home:
                return true;

        }
    return super.onOptionsItemSelected(item);
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }
}
