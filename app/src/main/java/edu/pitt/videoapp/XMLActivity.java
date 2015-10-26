package edu.pitt.videoapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class XMLActivity extends AppCompatActivity {

    private String selectedFile = "";
    private String xmlFolder = "sequences";
    private ListView lv;
    private ArrayAdapter<String> lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        // open xml file directory
        File sequencesDir = new File(Environment.getExternalStorageDirectory(), xmlFolder);
        // create directory if does not exist
        if (!sequencesDir.exists()) {
            sequencesDir.mkdirs();
        }

        // this file is for testing purposes only
        File file = new File(Environment.getExternalStorageDirectory() + "/" + xmlFolder, "test.xml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // end test file

        // xml list view skeleton
        // Get a handle to the list view
        lv = (ListView) findViewById(R.id.XMLlistView);
        // set adapter source to arraylist
        // so add/remove operations are enabled
        lvAdapter = new ArrayAdapter<String>(XMLActivity.this,
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

    /*
    *   Shows the email dialog
    *   called via onClick of emailXMLButton
    *   @param view     emailXMLButton view
     */
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
                i.putExtra(Intent.EXTRA_EMAIL, emails);
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT, body);
                // to attach files to email
                Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" + xmlFolder, selectedFile));
                i.putExtra(Intent.EXTRA_STREAM, uri);
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

    /*
    *   Deletes the selected XML file
    *   @param view     deleteXMLButton view
     */
    public void deleteXML(View view)
    {
        // check if item is selected
        if(selectedFile.equals(""))
        {
            Toast.makeText(XMLActivity.this, "No item selected", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory() + "/" + xmlFolder, selectedFile);
        if(file.exists())
        {
            if(file.delete())
            {
                Toast.makeText(XMLActivity.this, selectedFile + " deleted", Toast.LENGTH_SHORT).show();
                // refresh listview
                lvAdapter.remove(new String(selectedFile));
                lvAdapter.notifyDataSetChanged();
                // reset selectedFile to empty string
                selectedFile = "";
            }

            else
            {
                Toast.makeText(XMLActivity.this, "Could not delete " + selectedFile, Toast.LENGTH_SHORT).show();
            }
        }

        else
        {
            Toast.makeText(XMLActivity.this, "Could not locate file", Toast.LENGTH_SHORT).show();
        }
    }
}
