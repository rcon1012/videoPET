package edu.pitt.videoapp;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Created by jake on 11/12/15.
 */
public class CutTest {

    Cut c;
    long fakeTime = 4707120;

    @Before
    public void setUp() {
        c = new Cut(fakeTime, 2*fakeTime, "Test Cut");
    }

    @Test
    public void testCompare() {
        Cut newCut = new Cut(fakeTime + 1, 2*fakeTime + 1, "Test Cut 2");
        assertTrue("New cut time  should be greater than old cut time", c.compareTo(newCut) < 0);
    }

    @Test
    public void testFormat() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expectedRecordString = "02:36:54:07";
        String expectedSourceString = "01:18:27:03";
        Method formatMethod = Cut.class.getDeclaredMethod("formatTimecode", long.class);
        formatMethod.setAccessible(true);
        String formatString = (String) formatMethod.invoke(null, c.getRecordIn());
        assertTrue("Expected \"" + expectedRecordString + "\"; Got \"" + formatString + "\".", formatString.equals(expectedRecordString));

        formatString = (String) formatMethod.invoke(null, c.getSourceIn());
        assertTrue("Expected \"" + expectedSourceString + "\"; Got \"" + formatString + "\".", formatString.equals(expectedSourceString));
    }


}
