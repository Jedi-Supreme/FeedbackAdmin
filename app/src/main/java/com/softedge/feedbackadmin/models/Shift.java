package com.softedge.feedbackadmin.models;

import android.arch.persistence.room.ColumnInfo;

public class Shift {

    @ColumnInfo(name = "start_time")
    private String start_time;

    @ColumnInfo(name = "end_time")
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
