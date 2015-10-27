package edu.pitt.videoapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
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

    @MediumTest
    public void testLabelChangeOnOkayClickNoText() {
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String oldName = "old_name";
                    Camera c = new Camera(stageActivity, cm);
                    c.setCamLabel(oldName);
                    CameraView cv = c.getCameraView();
                    Dialog dialog = cv.activateLabelDialog();
                    Button ok_button = (Button) dialog.findViewById(R.id.dialog_ok);
                    ok_button.performClick();
                    assertNotSame(oldName, c.getCamLabel());
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            assertTrue(false);
        }
    }

    @MediumTest
    public void testLabelChangeOnCancelClick() {
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String oldName = "old_name";
                    Camera c = new Camera(stageActivity, cm);
                    c.setCamLabel(oldName);
                    CameraView cv = c.getCameraView();
                    Dialog dialog = cv.activateLabelDialog();
                    Button cancel_button = (Button) dialog.findViewById(R.id.dialog_cancel);
                    cancel_button.performClick();
                    assertSame(oldName, c.getCamLabel());
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            assertTrue(false);
        }
    }

    @MediumTest
    public void testLabelChangeOnOkayClickText() {
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String oldName = "old_name";
                    String newName = "new_name" ;
                    Camera c = new Camera(stageActivity, cm);
                    c.setCamLabel(oldName);
                    CameraView cv = c.getCameraView();
                    Dialog dialog = cv.activateLabelDialog();
                    EditText edit = (EditText) dialog.findViewById(R.id.change_camera_label);
                    edit.requestFocus();
                    edit.setText(newName);
                    Button ok_button = (Button) dialog.findViewById(R.id.dialog_ok);
                    ok_button.performClick();
                    assertSame(newName, c.getCamLabel());
                    assertNotSame(oldName, c.getCamLabel());
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            assertTrue(false);
        }
    }
}
