package edu.pitt.videoapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jake on 11/7/15.
 */
public class EDLConverter {
    // Default path in the event that one is not passed to the function
    private static final String defaultPath = "/sdcard/exportfile.edl";

    /**
     * This method takes a list of cuts stored in a camera manager, retrieves them, and writes them
     * to a properly formatted CMX3600 EDL file
     *
     * @param cm The CameraManager holding the list of cuts to be exported
     * @return The file that was written to
     * @throws IOException in the event of an error writing to the SD card
     */
    public static File convert(CameraManager cm) throws IOException{
        return convert(cm, defaultPath);
    }

    /**
     * A version of the above function with a path parameter for testing. This function is called
     * by that one with the path provided. It's function overloading.
     *
     * @param cm The CameraManager holding the list of cuts to be exported
     * @param path The path to which the file should be written
     * @return
     * @throws IOException
     */
    private static File convert(CameraManager cm, String path) throws IOException {
        // Monotonically increasing edit counter. Each line should have a unique edit number
        int edit = 1;

        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        ArrayList<Cut> cutlist = cm.getCutlist();

        for(Cut cut: cutlist) {
            String fmtString = String.format("%3d  %s       V     C        %s", edit, cut.sequenceChar(), cut.getTimecodes());
            writer.write(fmtString);
            edit++;
        }
        return file;
    }
}
