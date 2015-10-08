package edu.pitt.videoapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public class XMLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
    }

    public void showEmailDialog(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(XMLActivity.this);
        LayoutInflater inflater = XMLActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.email_dialog, null));
        builder.create();
        builder.show();

    }
}
