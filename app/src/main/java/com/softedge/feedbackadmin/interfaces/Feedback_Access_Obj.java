package com.softedge.feedbackadmin.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.softedge.feedbackadmin.models.Branch_data;
import com.softedge.feedbackadmin.models.Duty_roster;
import com.softedge.feedbackadmin.models.Feedback_team_join;
import com.softedge.feedbackadmin.models.Shift;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface Feedback_Access_Obj {

    String selectTable = "SELECT COUNT(*) FROM " + Branch_data.TABLE;

    //=============================================FEEDBACK=========================================
    @Query(selectTable)
    int countAll();

    @Query("SELECT DISTINCT " + Branch_data.COLUMN_BRANCHNAME + " FROM " + Branch_data.TABLE +
            " ORDER BY " + Branch_data.COLUMN_BRANCHNAME + " ASC")
    String[] branch_names();

    @Query(selectTable + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " LIKE :branchname")
    int count_branchName(String branchname);

    @Query(selectTable + " GROUP BY " + Branch_data.COLUMN_DATE)
    int[] count_date();

    @Query(selectTable + " WHERE " + Branch_data.COLUMN_FEEDBACKS + " == :userfeed" )
    int count_feedback(Boolean userfeed);

    @Query(selectTable + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " LIKE :branchname AND "
            + Branch_data.COLUMN_DATE + " == :date")
    int count_branchname_date(String branchname, String date);

    @Query(selectTable + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " LIKE :branchname AND "
            + Branch_data.COLUMN_FEEDBACKS + " == :feedback")
    int count_branchname_feedback(String branchname, Boolean feedback);

    @Query(selectTable + " WHERE " + Branch_data.COLUMN_FEEDBACKS + " == :feedback AND " + Branch_data.COLUMN_DATE + " == :date")
    int count_date_feedback(String date, Boolean feedback);

    @Query("SELECT * FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " == :branchname ")
    List<Branch_data> branch_feedbacks(String branchname);

    @Insert
    void addFeedback(Branch_data branch_data);

    @Query("DELETE FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " == :branchname")
    void delete_all_branchData(String branchname);

    @Query(selectTable + " WHERE :query")
    int custom_count_query(String query);
    //=============================================FEEDBACK=========================================


    //===========================================DUTY ROSTER========================================
    @Insert
    void addDuty_roster(Duty_roster duty_roster);

    //TODO WORK ON DUTY ROSTER QUERIES

    @Query("SELECT DISTINCT * FROM " + Duty_roster.TABLE + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " == :branchname")
    List<Duty_roster> getDuty_rosters(String branchname);

    @Query("SELECT " + Duty_roster.COLUMN_TEAM_NAME + " FROM " + Duty_roster.TABLE
            + " WHERE :date BETWEEN " + Duty_roster.COLUMN_START_DATE + " AND " + Duty_roster.COLUMN_END_DATE
            + " AND :time BETWEEN " + Shift.COLUMN_START_TIME + " AND " + Shift.COLUMN_END_TIME + " AND "
            + Branch_data.COLUMN_BRANCHNAME + " == :branchname")
    String team_on_duty(String date, String time, String branchname);
    //===========================================DUTY ROSTER========================================

    //=======================================FEEDBACK ROSTER JOIN===================================
    @Insert
    void insert_teamFeedback(Feedback_team_join team_feedback);

    @Query("SELECT DISTINCT " + Feedback_team_join.COLUMN_TEAM_NAME + " FROM " + Feedback_team_join.TABLE)
    String[] distinct_teamname();
    @Query("SELECT * FROM " + Feedback_team_join.TABLE)
    Feedback_team_join[] getTeamRosters();

    @Query("SELECT COUNT(*) FROM " + Feedback_team_join.TABLE +  " WHERE " + Feedback_team_join.COLUMN_TEAM_NAME + " == :teamName")
    int count_byTeamName(String teamName);

    @Query("SELECT COUNT(*) FROM " + Feedback_team_join.TABLE + " WHERE "
            + Feedback_team_join.COLUMN_TEAM_NAME + " == :teamName AND " + Feedback_team_join.COLUMN_FEEDBACK + " == :feedback")
    int count_feedback_team(String teamName, Boolean feedback);
    //=======================================FEEDBACK ROSTER JOIN===================================



}
