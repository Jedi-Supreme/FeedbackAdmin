package com.softedge.feedbackadmin.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "FEEDBACKS")
public class Branch_data {

    public static final String TABLE = "FEEDBACKS";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_FEEDBACKS = "feedbacks";
    public static final String COLUMN_BRANCHNAME = "branchname";


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = COLUMN_TIMESTAMP)
    private String timestamp;

    @ColumnInfo(name = COLUMN_DATE)
    private String date;

    @ColumnInfo(name = COLUMN_FEEDBACKS)
    private Boolean userfeeds;

    @ColumnInfo(name = COLUMN_BRANCHNAME)
    private String branchname;

    public Branch_data(String timestamp, String date, Boolean userfeeds, String branchname) {
        this.timestamp = timestamp;
        this.date = date;
        this.userfeeds = userfeeds;
        this.branchname = branchname;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getUserfeeds() {
        return userfeeds;
    }

    public void setUserfeeds(Boolean userfeeds) {
        this.userfeeds = userfeeds;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }
}
