package edu.pitt.videoapp;

/**
 * Created by jake on 11/7/15.
 */
public class Cut implements Comparable<Cut> {
    private String timestamp;


    @Override
    public int compareTo(Cut another) {
       return timestamp.compareTo(another.getTimestamp());
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
