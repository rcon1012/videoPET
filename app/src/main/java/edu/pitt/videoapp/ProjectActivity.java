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
        File sequencesDir = new File(Environment.getExternalStorageDirectory(), setupsFolder);
        // create directory if does not exist
        if (!sequencesDir.exists()) {
            sequencesDir.mkdirs();
        }

        // setup list view skeleton
        // Get a handle to the list view
        lv = (ListView) findViewById(R.id.setupListView);
        // set adapter source to arraylist
        // so add/remove operations are enabled
        lvAdapter = new ArrayAdapter<String>(ProjectActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(Arrays.asList(sequencesDir.list())));
        lv.setAdapter(lvAdapter);
        // set on click listener for each item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFile = ((TextView)view).getText().toString();
            }
        });
    }

    public void deleteSetup(View view)
    {
        // check if item is selected
        if(selectedFile.equals(""))
        {
            Toast.makeText(ProjectActivity.this, "No item selected", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory() + "/" + setupsFolder, selectedFile);
        if(file.exists())
        {
            if(file.delete())
            {
                Toast.makeText(ProjectActivity.this, selectedFile + " deleted", Toast.LENGTH_SHORT).show();
                // refresh listview
                lvAdapter.remove(new String(selectedFile));
                lvAdapter.notifyDataSetChanged();
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

    public ArrayList<Camera> parseSetupFile()
    {
        ArrayList<Camera> cameras = new ArrayList<Camera>();
        // open file
        // check if item is selected
        if(selectedFile.equals(""))
        {
            Toast.makeText(ProjectActivity.this, "No item selected", Toast.LENGTH_SHORT).show();
            return cameras;
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/" + setupsFolder, selectedFile);

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
                        *       xCoord = x
                        *       yCoord = y
                        */
                        // parse camera data
                        // initialize x and y coordinates to default position
                        int xCoord = 100;
                        int yCoord = 100;
                        line = br.readLine();
                        lineNumber++;
                        String[] xLine = line.split("\\txCoord = ");
                        // check xCoord line is properly formatted
                        if(xLine.length < 1)
                        {
                            Toast.makeText(ProjectActivity.this, "Error parsing file at line " + lineNumber +
                                            ":\n" + line,
                                    Toast.LENGTH_SHORT).show();
                        }
                        // parse xCoord
                        try{
                            xCoord = Integer.parseInt(xLine[1]);
                        }
                        catch(NumberFormatException e)
                        {
                            Toast.makeText(ProjectActivity.this, "Number format exception at line " + lineNumber +
                                            ":\n" + line,
                                    Toast.LENGTH_SHORT).show();
                            Log.e(TAG, e.getMessage());
                        }
                        line = br.readLine();
                        lineNumber++;
                        String[] yLine = line.split("\\tyCoord = ");
                        // check yCoord line is properly formatted
                        if(yLine.length < 1)
                        {
                            Toast.makeText(ProjectActivity.this, "Error parsing file at line " + lineNumber +
                                            ":\n" + line,
                                    Toast.LENGTH_SHORT).show();
                        }
                        // parse yCoord
                        try{
                            yCoord = Integer.parseInt(yLine[1]);
                        }
                        catch(NumberFormatException e)
                        {
                            Toast.makeText(ProjectActivity.this, "Number format exception at line " + lineNumber +
                                            ":\n" + line,
                                    Toast.LENGTH_SHORT).show();
                            Log.e(TAG, e.getMessage());
                        }
                        Camera cam = new Camera(xCoord, yCoord);
                        cameras.add(cam);
                        Log.d(TAG, "xCoord = " + xCoord + " " + "yCoord = " + yCoord);
                    }
                }
                br.close();
            }
            catch (IOException e) {
                Toast.makeText(ProjectActivity.this, "IOException at line " + lineNumber +
                                ":\n" + line,
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }
        }

        else
        {
            Toast.makeText(ProjectActivity.this, "Could not locate file " + selectedFile, Toast.LENGTH_SHORT).show();
        }
        return cameras;
    }

    public void loadSetup(View view)
    {
        // parse the selected setup file
        ArrayList<Camera> cameras = parseSetupFile();

        // set intent to change to stage activity
        Intent intent = new Intent(ProjectActivity.this, StageActivity.class);
        // add arraylist of 'Camera' to bundle
        Bundle b = new Bundle();
        b.putParcelableArrayList("cameras", cameras);
        intent.putExtras(b);
        // change to stage activity
        startActivity(intent);
        finish();
    }

    // this is for testing purposes only
    public void createTestFile()
    {
        // this file is for testing purposes only
        /*
        *   Camera
        *       xCoord = 200
        *       yCoord = 300
        *   Camera
        *       xCoord = 400
        *       yCoord = 500
         */
        File file = new File(Environment.getExternalStorageDirectory() + "/" + setupsFolder, "test.txt");
        try {
            file.createNewFile();
            String text = "Camera\n\txCoord = 200\n\tyCoord = 300\nCamera\n\txCoord = 400\n" +
                    "\tyCoord = 500";
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
