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
     * @param cm The camera manager holding the camera data
     * @return The file that was written to
     * @throws IOException in the event of an error writing to the SD card
     */
    public static File convert(CameraManager cm) throws IOException{
        return convert(cm, defaultPath);
    }

    private static File convert(CameraManager cm, String path) throws IOException {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        ArrayList<Cut> cutlist = cm.getCutlist();

        for(Cut cut: cutlist) {
            writer.write("format" + cut.getTimestamp());
        }
        return file;
    }
}
