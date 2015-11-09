package edu.pitt.videoapp;

/**
 * Created by jake on 11/7/15.
 */
public class Cut implements Comparable<Cut> {
    private long timestamp;

    public Cut(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(Cut another) {
        if(timestamp < another.timestamp) {
            return -1;
        } else if(timestamp > another.timestamp) {
            return 1;
        } else {
            return 0;
        }
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
