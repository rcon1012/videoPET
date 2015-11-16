package edu.pitt.videoapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
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
    @UiThreadTest
    public void testDeleteSetupFile() {
        pa.createTestFile();
        ListView lv = (ListView) pa.findViewById(R.id.setupListView);
        int priorCount = lv.getCount();
        lv.performItemClick(lv.getAdapter().getView(0, null, null), 0, lv.getAdapter().getItemId(0));
        pa.findViewById(R.id.deleteSetupButton).performClick();
        assertEquals(priorCount - 1, lv.getCount());
    }

     /**
     *  Test set-up file parsed correctly
     *  using file created by ProjectActivity.createTestFile()
     *
     *   Camera
     *       Label: cam 1
     *       xCoord: 200
     *       yCoord: 300
     *       Notes: notes for camera 1
     *       On Stage: stage0
     *   Camera
     *       Label: cam2
     *       xCoord: 400
     *       yCoord: 500
     *       Notes: notes for camera 2
     *       On Stage: stage 0
     *   Stage
     *       Label: stage0
     *       xCoord: 600
     *       yCoord: 600
     *       Notes: notes for stage 0
     */
    @UiThreadTest
    public void testParseSetupFile() {
        pa.createTestFile();
        ListView lv = (ListView) pa.findViewById(R.id.setupListView);
        lv.performItemClick(lv.getAdapter().getView(0, null, null), 0, lv.getAdapter().getItemId(0));
        ArrayList<ArrayList<Camera>> actual = pa.parseSetupFile();
        ArrayList<ArrayList<Camera>> expected = new ArrayList<ArrayList<Camera>>();
        ArrayList<Camera> expectedStage = new ArrayList<Camera>();
        Camera stage1 = new Camera();
        stage1.inactiveSetXY(600, 600);
        stage1.inactiveSetLabel("stage0");
        stage1.inactiveSetNotes("notes for stage 0");
        expectedStage.add(stage1);
        ArrayList<Camera> expectedCam = new ArrayList<Camera>();
        Camera c1 = new Camera();                   //was Camera c1 = new Camera(200, 300); changed due to errors by Luke
        c1.inactiveSetXY(200, 300);
        c1.inactiveSetLabel("cam1");
        c1.inactiveSetNotes("notes for camera 1");
        c1.inactiveSetStageTarget("stage0");
        Camera c2 = new Camera();                   //was Camera c2 = new Camera(400, 500); changed due to errors by Luke
        c2.inactiveSetXY(400, 500);
        c2.inactiveSetLabel("cam2");
        c2.inactiveSetNotes("notes for camera 2");
        c1.inactiveSetStageTarget("stage0");
        expectedCam.add(c1);
        expectedCam.add(c2);
        expected.add(0, expectedStage);
        expected.add(1, expectedCam);
        assertTrue(expected.equals(actual));
    }
}
