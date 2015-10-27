package edu.pitt.videoapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
    /**
     * Test delete of xml file
     */
    @SmallTest
    public void testDeleteXML()
    {
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    xmlActivity.creatTestFile();
                    ListView lv = (ListView) xmlActivity.findViewById(R.id.XMLlistView);
                    lv.performItemClick(lv.getChildAt(0), 0, lv.getAdapter().getItemId(0));
                    xmlActivity.findViewById(R.id.deleteButton).performClick();
                    assertEquals(0, lv.getCount());
                }
            });
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            assertTrue(false);
        }
    }
}
