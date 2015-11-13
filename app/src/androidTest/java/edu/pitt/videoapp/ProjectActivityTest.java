package edu.pitt.videoapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Ryan on 10/26/2015.
 */
public class ProjectActivityTest extends ActivityInstrumentationTestCase2<ProjectActivity> {
    private ProjectActivity pa;

    public ProjectActivityTest()
    {
        super(ProjectActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        pa = (ProjectActivity) getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
    *   Test deleting a set-up file
    *   Create test file with ProjectActivity.createTestFile()
     */
    @SmallTest
    public void testDeleteSetupFile() {
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pa.createTestFile();
                    ListView lv = (ListView) pa.findViewById(R.id.setupListView);
                    lv.performItemClick(lv.getChildAt(0), 0, lv.getAdapter().getItemId(0));
                    pa.findViewById(R.id.deleteSetupButton).performClick();
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

    /**
        *   Test set-up file parsed correctly
        *   using file created by ProjectActivity.createTestFile()
        *
        *   Camera
        *       xCoord = 200
        *       yCoord = 300
        *   Camera
        *       xCoord = 400
        *       yCoord = 500
         */
    @UiThreadTest
    public void testParseSetupFile() {
        pa.createTestFile();
        ListView lv = (ListView) pa.findViewById(R.id.setupListView);
        lv.performItemClick(lv.getAdapter().getView(0, null, null), 0, lv.getAdapter().getItemId(0));
        ArrayList<Camera> actual = pa.parseSetupFile();
        ArrayList<Camera> expected = new ArrayList<Camera>();
        Camera c1 = new Camera();                   //was Camera c1 = new Camera(200, 300); changed due to errors by Luke
        c1.inactiveSetXY(200, 300);
        c1.inactiveSetLabel("cam1");
        c1.inactiveSetNotes("notes for camera 1");
        Camera c2 = new Camera();                   //was Camera c2 = new Camera(400, 500); changed due to errors by Luke
        c2.inactiveSetXY(400, 500);
        c2.inactiveSetLabel("cam2");
        c2.inactiveSetNotes("notes for camera 2");
        expected.add(c1);
        expected.add(c2);
        assertTrue(expected.equals(actual));
    }
}
