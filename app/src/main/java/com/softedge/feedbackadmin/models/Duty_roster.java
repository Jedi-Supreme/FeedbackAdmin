package com.softedge.feedbackadmin.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(
        tableName = "WeeklyRoster",
        foreignKeys = @ForeignKey(
                entity = Branch_data.class,
                parentColumns = Branch_data.COLUMN_ID,
                childColumns = Branch_data.COLUMN_BRANCHNAME))
public class Duty_roster {

    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_TEAM_NAME = "team_name";
    public static final String COLUMN_SHIFT = "shift";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = COLUMN_START_DATE)
    private String start_date;

    @ColumnInfo(name = COLUMN_END_DATE)
    private String end_date;

    @ColumnInfo(name = COLUMN_TEAM_NAME)
    private String team_name;

    @ColumnInfo(name = COLUMN_SHIFT)
    private Shift shift;

    @ColumnInfo(name = Branch_data.COLUMN_BRANCHNAME)
    private String branch_name;

    public Duty_roster(String start_date, String end_date,
                       String team_name, Shift shift, String branch_name) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.team_name = team_name;
        this.shift = shift;
        this.branch_name = branch_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
}
