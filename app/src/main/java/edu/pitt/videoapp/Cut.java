package edu.pitt.videoapp;

/**
 * Created by jake on 11/7/15.
 */
public class Cut implements Comparable<Cut> {
    private long sourceIn;
    private long sourceOut;
    private long recordIn;
    private long recordOut;
    private String sequenceName;

    /**
     * Constructor that takes the camera and sequence timestamps
     * @param sourceIn Camera specific timestamp
     * @param recordIn Sequence timestamp
     */
    public Cut(long sourceIn, long recordIn, String sequenceName) {
        this.sourceIn = sourceIn;
        this.recordIn = recordIn;
        this.sequenceName = new String(sequenceName);
    }

    /**
     * Comparator that returns the difference
     * @param another The Cut object being compared to
     * @return The difference in milliseconds between the cuts, capped at MIN and MAX int values
     */
    @Override
    public int compareTo(Cut another) {
        long comp = this.recordIn - another.getRecordIn();
        if(comp > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (comp < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else {
            return (int) comp;
        }
    }

    public long getRecordIn() {
        return recordIn;
    }

    public long getSourceIn() {
        return sourceIn;
    }

    public String getTimecodes() {
        String sIn = formatTimecode(sourceIn);
        String sOut = formatTimecode(sourceOut);
        String rIn = formatTimecode(recordIn);
        String rOut = formatTimecode(recordOut);

        return String.format("%s %s %s %s", sIn, sOut, rIn, rOut);
    }

    private static String formatTimecode(long timecode) {
        long justMilliseconds = timecode % 1000;
        long frames = (justMilliseconds / 33) % 30;
        long seconds = timecode/1000;
        long minutes = seconds/60;
        seconds = seconds % 60;
        long hours = minutes/60;
        minutes = minutes % 60;
        return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, frames);
    }

    public void setOutTimes(long sourceOut, long recordOut) {
        this.sourceOut = sourceOut;
        this.recordOut = recordOut;
    }

    public String sequenceChar() {
        return sequenceName;
    }
}
