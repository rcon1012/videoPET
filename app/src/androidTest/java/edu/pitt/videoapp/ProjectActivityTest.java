package edu.pitt.videoapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;

/**
 * Created by Ryan on 10/26/2015.
 */
public class ProjectActivityTest extends ActivityInstrumentationTestCase2<ProjectActivity> {
    private ProjectActivity pa;

    public ProjectActivityTest()
    {
        super(ProjectActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        pa = (ProjectActivity) getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void testDeleteSetupFile() {
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pa.createTestFile();
                    ListView lv = (ListView) pa.findViewById(R.id.setupListView);
                    lv.performItemClick(lv.getAdapter().getView(0, null, null), 0, lv.getAdapter().getItemId(0));
                    pa.deleteSetup(pa.findViewById(R.id.deleteSetupButton));
                    assertEquals(0, lv.getCount());
                }
            });
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            assertTrue(false);
        }

    }
}
