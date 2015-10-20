package edu.pitt.videoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    public void newProject(View view) {
        Intent intent = new Intent(this, StageActivity.class);
        startActivity(intent);
    }

    public void loadXML(View view) {
        Intent intent = new Intent(this, XMLActivity.class);
        startActivity(intent);
    }

    public void project(View view) {
        Intent intent = new Intent(this, ProjectActivity.class);
        startActivity(intent);
    }

}
