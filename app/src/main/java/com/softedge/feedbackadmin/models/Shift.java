package com.softedge.feedbackadmin.models;

public class Shift {

    private String start_time;
    private String end_time;

    public Shift(String start_time, String end_time) {
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }
}
