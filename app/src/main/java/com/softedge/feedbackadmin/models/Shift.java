package com.softedge.feedbackadmin.models;

import android.arch.persistence.room.ColumnInfo;

public class Shift {

    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";

    @ColumnInfo(name = COLUMN_START_TIME)
    private String start_time;

    @ColumnInfo(name = COLUMN_END_TIME)
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
