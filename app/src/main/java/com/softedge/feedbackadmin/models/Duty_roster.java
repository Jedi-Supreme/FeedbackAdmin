package com.softedge.feedbackadmin.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = Duty_roster.TABLE,
        indices = {@Index(value = Shift.COLUMN_SHIFT_NAME)})
public class Duty_roster {

    private static final String COLUMN_ID = Branch_data.COLUMN_ID;
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_TEAM_NAME = "team_name";
    public static final String TABLE = "DUTY_ROSTER";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = COLUMN_START_DATE)
    private String start_date;

    @ColumnInfo(name = COLUMN_END_DATE)
    private String end_date;

    @ColumnInfo(name = COLUMN_TEAM_NAME)
    private String team_name;

    @Embedded
    @NonNull
    private Shift shift;

    @NonNull
    @ColumnInfo(name = Branch_data.COLUMN_BRANCHNAME)
    private String branch_name;

    public Duty_roster(String start_date, String end_date,
                       String team_name, @NonNull Shift shift, @NonNull String branch_name) {
        //MAKE START TIME AND END TIME FULL DATE-TIME STRING FOR BOTH START DATE AND END DATE,
        // START TIME = STARTDATE+STARTTIME
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

    @NonNull
    public Shift getShift() {
        return shift;
    }

    public void setShift(@NonNull Shift shift) {
        this.shift = shift;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(@NonNull String branch_name) {
        this.branch_name = branch_name;
    }
}
