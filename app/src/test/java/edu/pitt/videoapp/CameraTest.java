package edu.pitt.videoapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by jake on 10/8/15.
 */
public class CameraTest {

    Camera camera;
    CameraManager cameraManager;

    @Before public void setUp() {
        int x = 100;
        int y = 100;
        cameraManager = mock(CameraManager.class);
        camera = new Camera();                  //Was camera=new Camera(100,100); changed due to errors
    }
}
