package edu.pitt.videoapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;

/**
 * Created by Ryan on 11/3/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @MediumTest
     public void testNewProjectButton() {
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.main_button_1));
    }

    @MediumTest
    public void testSetupsButton() {
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.main_button_2));
    }

    @MediumTest
    public void testXMLButton() {
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.main_button_3));
    }

}
