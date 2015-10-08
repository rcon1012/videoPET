package edu.pitt.videoapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class XMLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
    }

    // shows the email dialog to email the xml
    public void showEmailDialog(View view) {
        // setup custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(XMLActivity.this);
        LayoutInflater inflater = XMLActivity.this.getLayoutInflater();
        LayoutInflater factory = LayoutInflater.from(XMLActivity.this);
        final View textEntryView = factory.inflate(R.layout.email_dialog, null);
        final AlertDialog emailDialog = builder.create();
        emailDialog.setView(textEntryView);
        // send email on click of "Send" button
        Button emailSendButton = (Button) textEntryView.findViewById(R.id.emailSendButton);
        emailSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = ((EditText) textEntryView.findViewById(R.id.emailSubjectText)).getText().toString();
                String email = ((EditText) textEntryView.findViewById(R.id.emailText)).getText().toString();
                String body = ((EditText) textEntryView.findViewById(R.id.emailBodyText)).getText().toString();
                emailDialog.dismiss();
            }
        });
        emailDialog.show();
    }
}
