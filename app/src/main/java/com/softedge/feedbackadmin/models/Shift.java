package com.softedge.feedbackadmin.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

public class Shift {

    public static final String COLUMN_SHIFT_NAME = "shift_name";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";

    private int shift_id;

    @ColumnInfo(name = COLUMN_SHIFT_NAME)
    @NonNull
    private String shift_name;

    @ColumnInfo(name = COLUMN_START_TIME)
    private String start_time;

    @ColumnInfo(name = COLUMN_END_TIME)
    private String end_time;

    public Shift(@NonNull String shift_name, String start_time, String end_time) {
        this.shift_name = shift_name;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    @NonNull
    public String getShift_name() {
        return shift_name;
    }

    public void setShift_name(@NonNull String shift_name) {
        this.shift_name = shift_name;
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

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }
}
