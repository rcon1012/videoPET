package edu.pitt.videoapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Created by jake on 11/12/15.
 */
public class CutTest {

    Cut c;
    long fakeTime = 1000;

    @Before
    public void setUp() {
        c = new Cut(fakeTime, 2*fakeTime);
    }

    @Test
    public void testCompare() {
        Cut newCut = new Cut(fakeTime + 1, 2*fakeTime + 1);
        assertTrue("New cut time  should be greater than old cut time", c.compareTo(newCut) < 0);
    }

}
