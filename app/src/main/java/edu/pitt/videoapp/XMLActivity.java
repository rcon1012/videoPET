package edu.pitt.videoapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class XMLActivity extends AppCompatActivity {

    private String selectedFile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        // open xml file directory
        File sequencesDir = new File(Environment.getExternalStorageDirectory(), "sequences");
        // create directory if does not exist
        if (!sequencesDir.exists()) {
            sequencesDir.mkdirs();
        }

        // xml list view skeleton
        // Get a handle to the list view
        ListView lv = (ListView) findViewById(R.id.XMLlistView);
        lv.setAdapter(new ArrayAdapter<String>(XMLActivity.this,
                android.R.layout.simple_list_item_1, sequencesDir.list()));
        // set on click listener for each item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFile = ((TextView)view).getText().toString();
            }
        });
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
                String[] emails = new String[1];
                emails[0] = email;
                String body = ((EditText) textEntryView.findViewById(R.id.emailBodyText)).getText().toString();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , emails);
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT   , body);
                // to attach files to email
                //i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/MyFile.csv"));
                try {
                    startActivity(Intent.createChooser(i, "Send"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(XMLActivity.this, "No email clients installed", Toast.LENGTH_SHORT).show();
                }
                emailDialog.dismiss();
            }
        });
        emailDialog.show();
    }
}
