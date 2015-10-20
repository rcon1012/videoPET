package edu.pitt.videoapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ryan on 10/11/2015.
 */
public class XMLActivityTest extends ActivityInstrumentationTestCase2<XMLActivity> {

    private XMLActivity xmlActivity;
    private Button emailXMLButton;

    public XMLActivityTest() {
        super(XMLActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        xmlActivity = (XMLActivity)getActivity();
        emailXMLButton = (Button) xmlActivity.findViewById(R.id.emailXMLButton);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // Test email dialog opens on button click of email button
    @SmallTest
    public void testEmailDialog()
    {
        emailXMLButton.callOnClick();
        LayoutInflater factory = LayoutInflater.from(getActivity());
        View textEntryView = factory.inflate(R.layout.email_dialog, null);
        ViewAsserts.assertOnScreen(textEntryView, textEntryView);
    }
}
