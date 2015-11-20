package edu.pitt.videoapp;

import org.junit.Before;

import java.io.IOException;

import edu.pitt.videoapp.EDLConverter;

import static org.mockito.Mockito.*;

/**
 * Created by jake on 11/12/15.
 */
public class EDLTest {
    //TODO: Test that before conversion, all fields in a Cut are not null

    //TODO: Write tests for fake EDLs
    CameraManager cm;

    @Before
    public void setUp() {
        cm = mock(CameraManager.class);

    }

    public void testEdlConvert() {
        try {
            EDLConverter.convert(cm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
