package edu.pitt.videoapp;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Ryan on 10/24/2015.
 */
public class ProjectActivityTest extends ActivityInstrumentationTestCase2<ProjectActivity> {
    private Activity projectActivity;
    private String selectedFile = "";
    private String setupsFolder = "set-ups";
    private ListView lv;
    private ArrayAdapter<String> lvAdapter;

    public ProjectActivityTest() {
        super(ProjectActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        projectActivity = (ProjectActivity)getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // this is an invalid test
    // need a good way to autmate testing for this method
    @UiThreadTest
    public void loadSetup()
    {
        ListView setupListView = (ListView) projectActivity.findViewById(R.id.setupListView);
        setupListView.setItemChecked(0, true);
        Button loadSetup = (Button) projectActivity.findViewById(R.id.loadSetupButton);
        loadSetup.callOnClick();
        assertEquals(projectActivity.findViewById(R.id.layoutContainerProject).getVisibility(), View.GONE);
        assertTrue(true);
    }
}
