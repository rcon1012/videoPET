package edu.pitt.videoapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jake on 10/9/15.
 */
public class StageActivityTest extends ActivityInstrumentationTestCase2<StageActivity> {

    private StageActivity stageActivity;
    private CameraManager cm;
    private Context ctx;
    private Menu menu;

    public StageActivityTest() {
        super(StageActivity.class);
    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();

        stageActivity = (StageActivity) getActivity();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        cm = new CameraManager();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void testSanity(){
        assertTrue(true);
    }

    /*
        Camera functionality tests
     */

    @UiThreadTest
    public void testCameraBasic(){
        Camera c = new Camera(stageActivity);
    }

    @UiThreadTest
    public void testCameraGetX(){
        Camera c = new Camera(stageActivity);
        float[] result = c.getXY();
        assertEquals(result[0], (float) 0);
    }

    @UiThreadTest
    public void testCameraGetY(){
        Camera c = new Camera(stageActivity);
        float[] result = c.getXY();
        assertEquals(result[1], 0f);
    }

    @UiThreadTest
    public void testCameraGetLabel(){
        Camera c = new Camera(stageActivity);
        assertEquals(c.getLabel(), "Camera");
    }

    @UiThreadTest
    public void testCameraGetDesc(){
        Camera c = new Camera(stageActivity);
        assertEquals(c.getDesc(), "");
    }

    @UiThreadTest
    public void testCameraGetLock(){
        Camera c = new Camera(stageActivity);
        assertFalse(c.getLock());
    }

    @UiThreadTest
    public void testCameraIsAlive(){
        Camera c = new Camera(stageActivity);
        assertTrue(c.isAlive());
    }

    @UiThreadTest
    public void testCameraSetXY(){
        float x = 150.5f;
        float y = 250.5f;
        Camera c = new Camera(stageActivity);
        float result1[] = c.getXY();
        assertNotSame(result1[0], x);
        assertNotSame(result1[1], y);
        c.setXY(x, y);
        float result2[] = c.getXY();
        assertEquals(result2[0], x);
        assertEquals(result2[1], y);
    }

    @UiThreadTest
    public void testCameraSetLabel(){
        String label = "Hello" ;
        Camera c = new Camera(stageActivity);
        String result1 = c.getLabel();
        assertNotSame(result1, label);
        c.setLabel(label);
        String result2 = c.getLabel();
        assertEquals(result2, label);
    }

    @UiThreadTest
    public void testCameraSetDesc(){
        String desc = "This camera is used to capture the right side of the stage" ;
        Camera c = new Camera(stageActivity);
        String result1 = c.getDesc();
        assertNotSame(result1, desc);
        c.setDesc(desc);
        String result2 = c.getDesc();
        assertEquals(result2, desc);
    }

    @UiThreadTest
    public void testCameraSetLock(){
        boolean lock = true;
        Camera c = new Camera(stageActivity);
        boolean result1 = c.getLock();
        assertNotSame(result1, lock);
        c.setLock(lock);
        boolean result2 = c.getLock();
        assertEquals(result2, lock);
    }

    @UiThreadTest
    public void testCameraIsActive(){
        Camera c = new Camera(stageActivity);
        assertFalse(c.isActive());
    }

    @UiThreadTest
    public void testCameraRemoveActive(){
        Camera c = new Camera(stageActivity);
        c.removeActive();
        assertFalse(c.isActive());
    }

    /*
        Rig functionality Tests
     */


    @UiThreadTest
    public void testRigBasic(){
        Rig r = new Rig(stageActivity);
    }

    @UiThreadTest
    public void testRigGetX(){
        Rig r = new Rig(stageActivity);
        float[] result = r.getXY();
        assertEquals(result[0], (float) 0);
    }

    @UiThreadTest
    public void testRigGetY(){
        Rig r = new Rig(stageActivity);
        float[] result = r.getXY();
        assertEquals(result[1], 0f);
    }

    @UiThreadTest
    public void testRigGetLabel(){
        Rig r = new Rig(stageActivity);
        assertEquals(r.getLabel(), "Camera");
    }

    @UiThreadTest
    public void testRigGetLock(){
        Rig r = new Rig(stageActivity);
        assertFalse(r.getLock());
    }

    @UiThreadTest
    public void testRigWasDeleted(){
        Rig r = new Rig(stageActivity);
        assertFalse(r.wasDeleted());
    }

    @UiThreadTest
    public void testRigSetXY(){
        float x = 150.5f;
        float y = 250.5f;
        Rig r = new Rig(stageActivity);
        float result1[] = r.getXY();
        assertNotSame(result1[0], x);
        assertNotSame(result1[1], y);
        r.setXY(x, y);
        float result2[] = r.getXY();
        assertEquals(result2[0], x);
        assertEquals(result2[1], y);
    }

    @UiThreadTest
    public void testRigSetLabel(){
        String label = "Hello" ;
        Rig r = new Rig(stageActivity);
        String result1 = r.getLabel();
        assertNotSame(result1, label);
        r.setLabel(label);
        String result2 = r.getLabel();
        assertEquals(result2, label);
    }

    @UiThreadTest
    public void testRigSetLock(){
        boolean lock = true;
        Rig r = new Rig(stageActivity);
        boolean result1 = r.getLock();
        assertNotSame(result1, lock);
        r.setLock(lock);
        boolean result2 = r.getLock();
        assertEquals(result2, lock);
    }

    @UiThreadTest
    public void testGetRigType(){
        Rig r1 = new Rig(stageActivity) ;
        assertEquals(r1.getRigType(), Rig.CAMERA);

        Camera c = new Camera (stageActivity);
        assertEquals(c.getCamRig().getRigType(), Rig.CAMERA);

        Rig r2 = new Rig(stageActivity, Rig.CAMERA) ;
        assertEquals(r2.getRigType(), Rig.CAMERA);

        Rig r3 = new Rig(stageActivity, Rig.STAGE) ;
        assertEquals(r3.getRigType(), Rig.STAGE);
    }

    @UiThreadTest
    public void testSetDrawToThisLine() {
        Rig r = new Rig (stageActivity) ;
        Rig s = new Rig (stageActivity, Rig.STAGE);
        r.setDrawToThisStage(s);
        Rig x = r.getDrawToThisStage();
        assertEquals(s, x);
    }

    @UiThreadTest
    public void testGetDrawToThisLine() {
        Rig r = new Rig (stageActivity);
        Rig s = new Rig (stageActivity, Rig.STAGE);
        r.setDrawToThisStage(s);
        assertEquals(r.getDrawToThisStage(), s);
    }

    @UiThreadTest
    public void testDrawLine() {
        Rig r = new Rig (stageActivity) ;
        Rig s = new Rig (stageActivity, Rig.STAGE);
        r.setDrawToThisStage(s);
        r.drawLine();
        s.addToLineRigList(r);
        assertEquals( r, s.getLineRigList().get(0) ) ;
    }
}
