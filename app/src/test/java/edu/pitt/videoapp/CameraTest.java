package edu.pitt.videoapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jake on 10/8/15.
 */
public class CameraTest {

    Camera camera;
    CameraManager cameraManager;

    @Before public void setUp() {
        int x = 100;
        int y = 100;
        cameraManager = new CameraManager();
        camera = new Camera();                  //Was camera=new Camera(100,100); changed due to errors
    }

    @Test public void hash() {
        int test_x = 100;
        int test_y = 99;

        camera.setXY(test_x, test_y);

        int expectedHash = 31 * test_x + test_y;
        assertEquals(expectedHash, camera.hashCode());
    }

    @Test
    public void makeCut() {

    }
}
