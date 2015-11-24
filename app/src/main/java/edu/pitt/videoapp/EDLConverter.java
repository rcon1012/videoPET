package edu.pitt.videoapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by jake on 11/7/15.
 */
public class EDLConverter {
    /**
     * This method takes a list of cuts stored in a camera manager, retrieves them, and writes them
     * to a properly formatted CMX3600 EDL file
     * A version of the above function with a path parameter for testing. This function is called
     * by that one with the path provided. It's function overloading.
     *
     * @param cm The CameraManager holding the list of cuts to be exported
     * @param path The path to which the file should be written
     * @return file, with contents written out
     * @throws IOException
     */
    public static File convert(CameraManager cm, String path) throws IOException {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        convert(cm, writer);
        writer.close();
        return file;
    }


    /**
     * Version of the above function with a File parameter so that the file object can be mocked
     * @param cm CameraManager holding the list of cuts to be exported
     * @param writer The write stream that data will be output on
     * @return file, with contents written out
     * @throws IOException
     */
    public static void convert(CameraManager cm, Writer writer) throws IOException {
        // Monotonically increasing edit counter. Each line should have a unique edit number
        int edit = 1;

        ArrayList<Cut> cutList = cm.getCutlist();

        String header = "TITLE: VideoPET\n" +
                "FCM: DROP FRAME\n\n";
        writer.write(header);
        for(Cut cut: cutList) {
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
            String fmtString = String.format("%03d AX AA/V C %s\n* FROM CLIP NAME %s\n", edit, cut.getTimecodes(), cut.sequenceChar());
            writer.write(fmtString);
            edit++;
        }
    }
}
