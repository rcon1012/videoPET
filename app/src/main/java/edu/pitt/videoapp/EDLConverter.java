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
            /*
             Note on the format of a CMX 3600 EDL file:
             The first three numbers are the "edit number". These increase monotonically and are used
                to order the edits.
             The second set of numbers are the "reel name". AX is a special reel name for external
                sources like all-black or color bars. We want individual reel names, so that we can cut
                back and forth between them
             The next character is V for video, A for audio, AA for 2 channel audio, or AA/V for all audio
                and the video track. We should probably do AA/V
             Next comes a character to represent the type of edit. C is cut. D is dissolve. W is wipe.
                for now we should just support the cut. The rest can be done in post
             Finally we have the source in/out timecodes (the timestamps on the reel that we are getting
                the footage from) and record in/out (the timestamps on the sequence where the footage is
                being inserted). These are in the format hour:minute:second:frame.
             */
            String fmtString = String.format("%3d %s AA/V C %s", edit, cut.sequenceChar(), cut.getTimecodes());
            writer.write(fmtString);
            edit++;
        }
        return file;
    }
}
