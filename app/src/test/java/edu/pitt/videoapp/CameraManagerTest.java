package edu.pitt.videoapp;


import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Created by jake on 10/27/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class CameraManagerTest {

    CameraManager cameraManager;

    @Mock
    Camera mockCamera;

    @Before public void setUp() {
        cameraManager = new CameraManager();
    }

    @Test public void size() {
        assertEquals(0, cameraManager.cameraArrayList.size());

        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        assertEquals(3, cameraManager.cameraArrayList.size());
    }

    @Test public void delete() {
        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        assertEquals(3, cameraManager.cameraArrayList.size());

        cameraManager.removeCamera(mockCamera);
        assertEquals(2, cameraManager.cameraArrayList.size());

        cameraManager.removeCamera(mockCamera);
        cameraManager.removeCamera(mockCamera);
        assertEquals(0, cameraManager.cameraArrayList.size());

        cameraManager.removeCamera(mockCamera);
        assertEquals(0, cameraManager.cameraArrayList.size());
    }
}
