package edu.pitt.videoapp;


import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by jake on 10/27/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class CameraManagerTest {

    CameraManager cameraManager;
    ArrayList<Camera> cameraArrayList;

    @Mock
    Camera mockCamera;

    @Mock
    Cut mockCut;

    @Before public void setUp() throws NoSuchFieldException, IllegalAccessException {
        cameraManager = new CameraManager();
        Field cameraArrayListField = CameraManager.class.getDeclaredField("cameraArrayList");
        cameraArrayListField.setAccessible(true);
        cameraArrayList = (ArrayList<Camera>) cameraArrayListField.get(cameraManager);
    }

    @Test public void size() {
        assertEquals(0, cameraArrayList.size());

        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        assertEquals(3, cameraArrayList.size());
    }

    @Test public void delete() {
        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        assertEquals(3, cameraArrayList.size());

        cameraManager.removeCamera(mockCamera);
        assertEquals(2, cameraArrayList.size());

        cameraManager.removeCamera(mockCamera);
        cameraManager.removeCamera(mockCamera);
        assertEquals(0, cameraArrayList.size());

        cameraManager.removeCamera(mockCamera);
        assertEquals(0, cameraArrayList.size());
    }

    @Test
    public void getCutlist() {
        long faketime = 4707120;
        cameraManager.setSequenceStartTime(faketime);
        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        cameraManager.addCamera(mockCamera);
        // End the sequence 300,000 ms (5 minutes) after start
        cameraManager.setSequenceEndTime(faketime + 300000);

        ArrayList<Cut> mockList = new ArrayList<Cut>();
        mockList.add(mockCut);
        mockList.add(mockCut);
        mockList.add(mockCut);

        when(mockCamera.getAllCuts()).thenReturn(mock(ArrayList.class));
        cameraManager.getCutlist();
    }
}
