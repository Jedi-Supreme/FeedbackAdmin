package com.softedge.feedbackadmin.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = Branch_data.TABLE, indices = {@Index(value = {Branch_data.COLUMN_TIMESTAMP},unique = true)})
public class Branch_data {

    public static final String TABLE = "FEEDBACKS";
    static final String COLUMN_ID = "id";
    static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_DATE = "date";
    public static final String COLUMN_FEEDBACKS = "feedbacks";
    public static final String COLUMN_BRANCHNAME = "branchname";
    public static final String COLUMN_SERVICE_POINT = Company_details.SERVICE_POINT;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = COLUMN_TIMESTAMP)
    private String timestamp;

    @ColumnInfo(name = COLUMN_DATE)
    private String date;

    @ColumnInfo(name = COLUMN_FEEDBACKS)
    private Boolean userfeeds;

    @ColumnInfo(name = COLUMN_BRANCHNAME)
    @NonNull
    private String branchname;

    @ColumnInfo(name = COLUMN_SERVICE_POINT)
    private String service_point;

    public Branch_data(String timestamp, String date, Boolean userfeeds,
                       @NonNull String branchname, String service_point) {
        this.timestamp = timestamp;
        this.date = date;
        this.userfeeds = userfeeds;
        this.branchname = branchname;
        this.service_point = service_point;
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

    @NonNull
    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(@NonNull String branchname) {
        this.branchname = branchname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService_point() {
        return service_point;
    }

    public void setService_point(String service_point) {
        this.service_point = service_point;
    }
}
