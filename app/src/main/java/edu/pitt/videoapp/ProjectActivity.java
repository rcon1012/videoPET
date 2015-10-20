package edu.pitt.videoapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProjectActivity extends AppCompatActivity {

    private String selectedFile = "";
    private String setupFolder = "set-ups";
    private ListView lv;
    private ArrayAdapter<String> lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        // open setups file directory
        File sequencesDir = new File(Environment.getExternalStorageDirectory(), setupFolder);
        // create directory if does not exist
        if (!sequencesDir.exists()) {
            sequencesDir.mkdirs();
        }

        // this file is for testing purposes only
        File file = new File(Environment.getExternalStorageDirectory() + "/" + setupFolder, "test.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // end test file

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

        File file = new File(Environment.getExternalStorageDirectory() + "/" + setupFolder, selectedFile);
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
            Toast.makeText(ProjectActivity.this, "Could not locate file", Toast.LENGTH_SHORT).show();
        }
    }

}
