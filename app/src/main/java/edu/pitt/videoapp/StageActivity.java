package edu.pitt.videoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class StageActivity extends AppCompatActivity {

    public Menu getMenu() {
        return menu;
    }

    private Menu menu;

    private int screenWidth;
    private int screenHeight;

    private CameraManager cameraManager;
    private StageManager stageManager;

    private ImageView activeCamIcon;

    public int setupInputId = 779441;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();

        cameraManager = new CameraManager();
        stageManager = new StageManager();

        setContentView(R.layout.activity_stage);

        // get set-up data
        Bundle bundle = getIntent().getExtras();

        // if not loading setup
        if(bundle == null) {
            Rig stage = new Rig(this, Rig.STAGE, null);
            stage.setXY((float) screenWidth / 2 - 300 / 2 + 24, 10);
            stageManager.addStage(stage);

            Camera cameraStart = new Camera(this);
            cameraManager.addCamera(cameraStart);
        }

        // load set-up
        else {
            // load stages
            ArrayList<Camera> loadStages = bundle.getParcelableArrayList("stages");
            if (loadStages != null) {
                for (Camera st : loadStages) {
                    Rig s = new Rig(this, Rig.STAGE, null);
                    s.setXY(st.inActiveGetXY()[0], st.inActiveGetXY()[1]);
                    s.setLabel(st.inactiveGetLabel());
                    stageManager.addStage(s);
                }
            }

            // load cameras
            ArrayList<Camera> loadCameras = bundle.getParcelableArrayList("cameras");
            if (loadCameras != null) {
                for (Camera camera : loadCameras) {
                    Camera c = new Camera(this);
                    c.setXY(camera.inActiveGetXY()[0], camera.inActiveGetXY()[1]);
                    if(camera.inactiveGetNote() != null && !camera.inactiveGetNote().equals("")) {
                        c.setDesc(camera.inactiveGetNote());
                    }
                    if(camera.inactiveGetLabel() != null && !camera.inactiveGetLabel().equals("")) {
                        c.setLabel(camera.inactiveGetLabel());
                    }
                    if(camera.inactiveGetNote() != null && !camera.inactiveGetNote().equals("")) {
                        c.setDesc(camera.inactiveGetNote());
                    }
                    if(camera.inactiveGetStageTarget() != null && !camera.inactiveGetStageTarget().equals("")) {
                        // draw line to camera's stage target
                        String stageT = camera.inactiveGetStageTarget();
                        for (Rig stageTarget : stageManager.stageArrayList) {
                            if (stageTarget.getLabel().equals(stageT)) {
                                c.getCamRig().setDrawToThisStage(stageTarget);
                                stageTarget.setDrawToThisStage(c.getCamRig());
                                stageTarget.addToLineRigList(c.getCamRig());
                                c.getCamRig().drawLine();
                            }
                        }
                    }

                    cameraManager.addCamera(c);
                }
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stage, menu);

        //View v = menu.findItem(R.id.rename).getActionView();
        //EditText txtrename = ( EditText ) v.findViewById(R.id.txt_rename);


        // These two blocks solve an insidious bug where the icon initially loaded is *not*
        // one of the two options provided
        MenuItem lockItem = menu.findItem(R.id.lock_btn);
        lockItem.setIcon(getResources().getDrawable(R.drawable.ic_lock_open_white_48dp));

        MenuItem playItem = menu.findItem(R.id.play_stop_btn);
        playItem.setIcon(getResources().getDrawable(R.drawable.ic_play_arrow_black_48dp));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.play_stop_btn:
                MenuItem playItem = menu.findItem(R.id.play_stop_btn);
                if(playItem.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_play_arrow_black_48dp).getConstantState())) {
                    playItem.setIcon(getResources().getDrawable(R.drawable.ic_stop_black_48dp));
                    long now = System.currentTimeMillis();
                    cameraManager.setSequenceStartTime(now);
                }
                else {
                    long now = System.currentTimeMillis();
                    cameraManager.setSequenceEndTime(now);
                    showSaveSequenceDialog();
                }
                return true;
            case R.id.lock_btn:
                // TODO Start / end sequence
                MenuItem lockItem = menu.findItem(R.id.lock_btn);
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

                Camera c = new Camera(this);
                // 250 / 150 is width and height of rig... TODO getters for height and width of center of rig
                c.setXY((float)screenWidth/2 - 250/2, (float)screenHeight/2 - 150/2);
                cameraManager.addCamera(c);
                return true;
            case R.id.add_stage:
                if(stageManager.size()>2){
                    Toast.makeText(StageActivity.this, "MAX OF 3 STAGES", Toast.LENGTH_SHORT).show();
                }
                else {
                    Rig stage = new Rig(this, Rig.STAGE, null);
                    stage.setXY((float) screenWidth / 2 - 300 / 2 + 24, 10);
                    stageManager.addStage(stage);
                }
                return true;
            case R.id.return_home:
                Intent intent = new Intent(StageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.save_setup:
                showSaveSetupDialog();
                return true;

        }
    return super.onOptionsItemSelected(item);
    }

    public void removeStage() {
        Log.d("Removing","java");
        this.stageManager.deleteStage();
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    /**
     * Saves the current setup to 'set-ups' folder
     * @param filename name of the set-up file being saved
     */
    public void saveSetup(String filename) {
        String setup = "";
        for(Camera camera : cameraManager) {
            if(!camera.getCamRig().wasDeleted()) {
                setup += "Camera\n\t";
                setup += "Label: " + camera.getLabel() + "\n\t";
                setup += "xCoord: " + camera.getXY()[0] + "\n\t";
                setup += "yCoord: " + camera.getXY()[1] + "\n\t";
                setup += "Notes: " + camera.getDesc() + "\n\t";
                setup += "On Stage: ";
                if(camera.getCamRig().getDrawToThisStage() != null) {
                    setup += camera.getCamRig().getDrawToThisStage().getLabel();
                }
                setup += "\n";
            }
        }

        for(Rig stage : stageManager.stageArrayList) {
            if(!stage.wasDeleted()) {
                setup += "Stage\n\t";
                setup += "Label: " + stage.getLabel() + "\n\t";
                setup += "xCoord: " + stage.getXY()[0] + "\n\t";
                setup += "yCoord: " + stage.getXY()[1] + "\n\t";
                //setup += "Notes: " + stage.getDesc() + "\n\t";
            }
        }

        // open setups file directory
        File videoPET = new File(Environment.getExternalStorageDirectory(), "VideoPET");
        // create directory if does not exist
        if (!videoPET.exists()) {
            videoPET.mkdirs();
        }
        File setupsDir = new File(Environment.getExternalStorageDirectory() + "/VideoPET", "set-ups");
        // create directory if does not exist
        if (!setupsDir.exists()) {
            setupsDir.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/VideoPET/" + "set-ups", filename + ".txt");
        try {
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(setup);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows a dialog where the user can input what the current set-up
     * should be saved as
     */
    public void showSaveSetupDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Save Set-up");
        // set input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(input);
        // set buttons
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveSetup(input.getText().toString());
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();

    }

    /**
     * shows the save sequence dialog
     */
    public void showSaveSequenceDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Save Sequence");
        // set input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(input);
        // set buttons
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveSequence(input.getText().toString());
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();
    }

    /**
     * saves the sequence with the given filename
     * @param filename the name of the file the sequence will be saved as
     */
    public void saveSequence(String filename) {
        String path = Environment.getExternalStorageDirectory() + "/VideoPET/" + "sequences/" + filename + ".edl";
        try {
            EDLConverter.convert(cameraManager, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
