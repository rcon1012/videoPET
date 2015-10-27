package edu.pitt.videoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * The activity that displays the main page of the app.
 * This should launch on startup and display 3 buttons.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    /**
     * Callback for the "New" button to launch a new StageActivity
     * @param view the clicked "New" button
     */
    public void newProject(View view) {
        Intent intent = new Intent(this, StageActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for the "Load XML" button to launch a new XMLActivity
     * @param view the clicked button
     */
    public void loadXML(View view) {
        Intent intent = new Intent(this, XMLActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for the "Load Project" button to launch a new ProjectActivity
     * @param view the clicked button
     */
    public void project(View view) {
        Intent intent = new Intent(this, ProjectActivity.class);
        startActivity(intent);
    }

}
