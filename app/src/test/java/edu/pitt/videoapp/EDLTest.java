package edu.pitt.videoapp;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import edu.pitt.videoapp.EDLConverter;

import static org.mockito.Mockito.*;

/**
 * Created by jake on 11/12/15.
 */
public class EDLTest {
    //TODO: Test that before conversion, all fields in a Cut are not null
    CameraManager cm;
    long fakeTime = 4707120;

    Cut mockCut;
    @Before
    public void setUp() {
        cm = mock(CameraManager.class);
        mockCut = mock(Cut.class);
    }

    @Test
    public void testEdlConvert() {
        ArrayList<Cut> mockList = new ArrayList<Cut>();
        mockList.add(mockCut);
        mockList.add(mockCut);
        mockList.add(mockCut);

        when(cm.getCutlist()).thenReturn(mockList);
        when(mockCut.compareTo(mockCut)).thenReturn(1);
        when(mockCut.getSourceIn()).thenReturn(fakeTime);
        when(mockCut.getRecordIn()).thenReturn(fakeTime * 2);
        when(mockCut.getTimecodes()).thenReturn("00:00:11:22 00:00:11:22 00:00:22:44 00:00:22:44");
        when(mockCut.sequenceChar()).thenReturn("TST");

        try {
            Writer mockWriter = mock(Writer.class);
            EDLConverter.convert(cm, mockWriter);
            verify(mockWriter).write("001 TST AA/V C 00:00:11:22 00:00:11:22 00:00:22:44 00:00:22:44");
            verify(mockWriter).write("002 TST AA/V C 00:00:11:22 00:00:11:22 00:00:22:44 00:00:22:44");
            verify(mockWriter).write("003 TST AA/V C 00:00:11:22 00:00:11:22 00:00:22:44 00:00:22:44");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
