package com.softedge.feedbackadmin.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = Team_Feedback_join.TABLE,
        indices = {@Index(value = {Team_Feedback_join.COLUMN_TIME_STAMP}, unique = true)})
public class Team_Feedback_join {

    public static final String TABLE = "Feedback_Team_join";
    public static final String COLUMN_ID = Branch_data.COLUMN_ID;
    public static final String COLUMN_TEAM_NAME = Duty_roster.COLUMN_TEAM_NAME;
    public static final String COLUMN_FEEDBACK = Branch_data.COLUMN_FEEDBACKS;
    private static final String COLUMN_BRANCHNAME = Branch_data.COLUMN_BRANCHNAME;
    static final String COLUMN_TIME_STAMP = Branch_data.COLUMN_TIMESTAMP;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = COLUMN_TEAM_NAME)
    @NonNull
    private String teamName;

    @ColumnInfo(name = COLUMN_FEEDBACK)
    private Boolean feedBacks;

    @ColumnInfo(name = COLUMN_BRANCHNAME)
    private String branchName;

    @ColumnInfo(name = COLUMN_TIME_STAMP)
    private String timeStamp;

    public Team_Feedback_join(@NonNull String teamName, Boolean feedBacks, String branchName, String timeStamp) {
        this.teamName = teamName;
        this.feedBacks = feedBacks;
        this.branchName = branchName;
        this.timeStamp = timeStamp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(@NonNull String teamName) {
        this.teamName = teamName;
    }

    public Boolean getFeedBacks() {
        return feedBacks;
    }

    public void setFeedBacks(Boolean feedBacks) {
        this.feedBacks = feedBacks;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
