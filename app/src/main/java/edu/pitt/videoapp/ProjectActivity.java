package edu.pitt.videoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = ProjectActivity.class.getName();
    private String selectedFile = "";
    private String setupsFolder = "set-ups";
    private ListView lv;
    private ArrayAdapter<String> lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        // open setups file directory
        File videoPET = new File(Environment.getExternalStorageDirectory(), "VideoPET");
        // create directory if does not exist
        if (!videoPET.exists()) {
            videoPET.mkdirs();
        }
        File setupsDir = new File(Environment.getExternalStorageDirectory() + "/VideoPET", setupsFolder);
        // create directory if does not exist
        if (!setupsDir.exists()) {
            setupsDir.mkdirs();
        }

        // setup list view skeleton
        // Get a handle to the list view
        lv = (ListView) findViewById(R.id.setupListView);
        // set adapter source to arraylist
        // so add/remove operations are enabled
        lvAdapter = new ArrayAdapter<String>(ProjectActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(Arrays.asList(setupsDir.list())));
        lv.setAdapter(lvAdapter);
        // set on click listener for each item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFile = ((TextView) view).getText().toString();
            }
        });
    }

    /**
     *   Deletes the selected setup file from external storage
     *   @param view     deleteSetupButton view
     */
    public void deleteSetup(View view)
    {
        // check if item is selected
        if(selectedFile.equals(""))
        {
            Toast.makeText(ProjectActivity.this, "No item selected", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory() + "/VideoPet/" + setupsFolder, selectedFile);
        if(file.exists())
        {
            if(file.delete())
            {
                Toast.makeText(ProjectActivity.this, selectedFile + " deleted", Toast.LENGTH_SHORT).show();

                // refresh listview
                lvAdapter.remove(new String(selectedFile));
                lvAdapter.notifyDataSetChanged();


                /*
                *   reinitialize listview from directory
                *   this is necessary for testing
                */
                File setupsDir = new File(Environment.getExternalStorageDirectory() + "/VideoPet", setupsFolder);
                lvAdapter = new ArrayAdapter<String>(ProjectActivity.this,
                        android.R.layout.simple_list_item_1,
                        new ArrayList<String>(Arrays.asList(setupsDir.list())));
                lv.setAdapter(lvAdapter);

                // reset selectedFile to empty string
                selectedFile = "";
            }

            else
            {
                Toast.makeText(ProjectActivity.this, "Could not delete " + selectedFile, Toast.LENGTH_SHORT).show();
            }
        }

        else
        {
            Toast.makeText(ProjectActivity.this, "Could not locate file " + selectedFile, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *   Parses the selected text file and returns a Setup object (currently ArrayList<Camera>)
     *   @return     the parsed object representation of the setup
     */
    public ArrayList<ArrayList<Camera>> parseSetupFile()
    {
        ArrayList<ArrayList<Camera>> setup = new ArrayList<ArrayList<Camera>>();
        ArrayList<Camera> cameras = new ArrayList<Camera>();
        ArrayList<Camera> stages = new ArrayList<Camera>();
        // open file
        File file = new File(Environment.getExternalStorageDirectory() + "/VideoPET/" + setupsFolder, selectedFile);

        // parse text file for set-up data (cameras and stage\s)
        if(file.exists())
        {
            /*
            * current line number in file tracked to display to user
            * in the event of an error
            */
            int lineNumber = 1;
            String line = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while((line = br.readLine()) != null)
                {
                    if(line.equals("Camera"))
                    {
                        /*
                        *   Camera
                        *       Label: <label>
                        *       xCoord: <x>
                        *       yCoord: <y>
                        *       Notes: <notes>
                        *       On Stage: <stage target>
                        *
                        */
                        // parse camera data
                        // initialize x and y coordinates to default position
                        Camera c = new Camera();
                        float xCoord = 100;
                        float yCoord = 100;
                        String label = "";
                        String notes = "";
                        String stageTarget = "";
                        // label
                        line = br.readLine();
                        lineNumber++;
                        String[] labelLine = line.split("\\tLabel: ");
                        // parse label
                        try{
                            label = labelLine[1];
                        }
                        catch(Exception e)
                        {
                            Log.e(TAG, e.getMessage());
                        }

                        line = br.readLine();
                        lineNumber++;
                        String[] xLine = line.split("\\txCoord: ");
                        // parse xCoord
                        try{
                            xCoord = Float.parseFloat(xLine[1]);
                        }
                        catch(NumberFormatException e)
                        {
                            Log.e(TAG, e.getMessage());
                        }

                        line = br.readLine();
                        lineNumber++;
                        String[] yLine = line.split("\\tyCoord: ");
                        // parse yCoord
                        try{
                            yCoord = Float.parseFloat(yLine[1]);
                        }
                        catch(NumberFormatException e)
                        {
                            Log.e(TAG, e.getMessage());
                        }

                        // notes
                        line = br.readLine();
                        lineNumber++;
                        String[] notesLine = line.split("\\tNotes: ");
                        // parse notes
                        try{
                            notes = notesLine[1];
                        }
                        catch(Exception e)
                        {
                            Log.e(TAG, e.getMessage());
                        }

                        // stage target
                        line = br.readLine();
                        lineNumber++;
                        String[] stageLine = line.split("\\tOn Stage: ");
                        // parse stage target
                        try {
                            stageTarget = stageLine[1];
                        }
                        catch(Exception e)
                        {
                            Log.e(TAG, e.getMessage());
                        }

                        c.inactiveSetLabel(label);
                        c.inactiveSetXY(xCoord, yCoord);
                        c.inactiveSetNotes(notes);
                        c.inactiveSetStageTarget(stageTarget);
                        cameras.add(c);
                    }
                    /**
                     *  Stage
                     *       Label: <label>
                     *       xCoord: <x>
                     *       yCoord: <y>
                     *       Notes: <notes>
                     */
                    else if(line.equals("Stage")) {
                        // parse camera data
                        // initialize x and y coordinates to default position
                        Camera s = new Camera();
                        float xCoord = 100;
                        float yCoord = 100;
                        String label = "";
                        // label
                        line = br.readLine();
                        lineNumber++;
                        String[] labelLine = line.split("\\tLabel: ");
                        // parse label
                        try{
                            label = labelLine[1];
                        }
                        catch(Exception e)
                        {
                            Log.e(TAG, e.getMessage());
                        }

                        line = br.readLine();
                        lineNumber++;
                        String[] xLine = line.split("\\txCoord: ");
                        // parse xCoord
                        try{
                            xCoord = Float.parseFloat(xLine[1]);
                        }
                        catch(NumberFormatException e)
                        {
                            Log.e(TAG, e.getMessage());
                        }

                        line = br.readLine();
                        lineNumber++;
                        String[] yLine = line.split("\\tyCoord: ");
                        // parse yCoord
                        try{
                            yCoord = Float.parseFloat(yLine[1]);
                        }
                        catch(NumberFormatException e)
                        {
                            Log.e(TAG, e.getMessage());
                        }

                        s.inactiveSetLabel(label);
                        s.inactiveSetXY(xCoord, yCoord);
                        stages.add(s);
                    }
                    else {
                        Log.e(TAG, "Error parsing file at line " + lineNumber +
                                ":\n" + line);
                    }
                }
                br.close();
            }
            catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        else
        {
            Toast.makeText(ProjectActivity.this, "Could not locate file " + selectedFile, Toast.LENGTH_SHORT).show();
        }

        setup.add(0, stages);
        setup.add(1, cameras);
        return setup;
    }

    /**
     *   Loads the selected set-up to StageActivity
     *   @param view     loadSetupButton view
     */
    public void loadSetup(View view)
    {
        // check if item is selected
        if(selectedFile.equals(""))
        {
            Toast.makeText(ProjectActivity.this, "No item selected", Toast.LENGTH_SHORT).show();
            return;
        }
        // parse the selected setup file
        // [0] = stages
        // [1] = cameras
        ArrayList<ArrayList<Camera>> setup = parseSetupFile();

        // set intent to change to stage activity
        Intent intent = new Intent(ProjectActivity.this, StageActivity.class);
        // add arraylist of 'stages' to bundle
        intent.putParcelableArrayListExtra("stages", setup.get(0));
        // add arraylist of 'cameras' to bundle
        intent.putParcelableArrayListExtra("cameras", setup.get(1));
        // change to stage activity
        startActivity(intent);
        finish();
    }

    /**
     *  creates a hard-coded set-up file
     *  this is for testing purposes only
     */
    public void createTestFile()
    {
        // this file is for testing purposes only
        /*
        *   Camera
        *       Label: cam 1
        *       xCoord: 200
        *       yCoord: 300
        *       Notes: notes for camera 1
        *       On Stage: stage0
        *   Camera
        *       Label: cam2
        *       xCoord: 400
        *       yCoord: 500
        *       Notes: notes for camera 2
        *       On Stage: stage 0
        *   Stage
        *       Label: stage0
        *       xCoord: 600
        *       yCoord: 600
        *       Notes: notes for stage 0
         */
        File file = new File(Environment.getExternalStorageDirectory() + "/VideoPET/" + setupsFolder, "test.txt");
        try {
            file.createNewFile();
            String text = "Camera\n\tLabel: cam1\n\txCoord: 200\n\tyCoord: 300\n\tNotes: notes for camera 1\n\t" +
                    "On Stage: stage0\n" +
                    "Camera\n\tLabel: cam2\n\txCoord: 400\n\tyCoord: 500\n" +
                    "\tNotes: notes for camera 2\n\tOn Stage: stage0\n" +
                    "Stage\n\tLabel: stage0\n\txCoord: 600\n" +
                    "\tyCoord: 600\n" +
                    "\tNotes: notes for stage 0\n";
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // end test file
        lvAdapter.add(new String("test.txt"));
        lvAdapter.notifyDataSetChanged();
    }
}
