package edu.pitt.videoapp;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ImageView;

/**
 * Created by jake on 10/9/15.
 */
public class StageActivityTest extends ActivityUnitTestCase<StageActivity> {

    private StageActivity stageActivity;
    private CameraManager cm;
    private Context ctx;

    public StageActivityTest() {
        super(StageActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Start and retrieve a reference to the Activity under test
        startActivity(new Intent(getInstrumentation().getTargetContext(), StageActivity.class), null, null);
        stageActivity = (StageActivity) getActivity();
        cm = new CameraManager();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void testCameraIconPresence() {
        Camera c = cm.addCamera(stageActivity);

    }
}
