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
        this.sequenceName = sequenceName;
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
        return String.format("%2ld:%2ld:%2ld:%2ld", timecode);
    }

    public void setOutTimes(long sourceOut, long recordOut) {
        this.sourceOut = sourceOut;
        this.recordOut = recordOut;
    }

    public String sequenceChar() {
        return sequenceName;
    }
}
