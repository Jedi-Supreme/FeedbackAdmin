package com.softedge.feedbackadmin.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.softedge.feedbackadmin.models.Branch_data;
import com.softedge.feedbackadmin.models.Duty_roster;
import com.softedge.feedbackadmin.models.Team_Feedback_join;
import com.softedge.feedbackadmin.models.Shift;

import java.util.List;

@Dao
public interface Feedback_Access_Obj {

    String selectTable = "SELECT COUNT(*) FROM " + Branch_data.TABLE;

    //=============================================FEEDBACK=========================================
    @Query(selectTable)
    int countAll();

    @Query("SELECT DISTINCT " + Branch_data.COLUMN_SERVICE_POINT + " FROM " + Branch_data.TABLE + " ORDER BY "
            + Branch_data.COLUMN_SERVICE_POINT + " ASC")
    List<String> service_points_list();

    @Query("SELECT DISTINCT " + Branch_data.COLUMN_BRANCHNAME + " FROM " + Branch_data.TABLE + " WHERE "
            + Branch_data.COLUMN_SERVICE_POINT + " == :servPoint " + " ORDER BY "
            + Branch_data.COLUMN_BRANCHNAME + " ASC")
    String bname_servPoint(String servPoint);

    @Query("SELECT COUNT(*) FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_SERVICE_POINT + " == :service_point")
    int count_serv_point(String service_point);

    //--------------------------------------------------------------------------------------------------------------------------
    @Query("SELECT " + Branch_data.COLUMN_BRANCHNAME + " FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_SERVICE_POINT
            + " == :service_point GROUP BY " + Branch_data.COLUMN_BRANCHNAME + " ORDER BY " + Branch_data.COLUMN_BRANCHNAME + " ASC")
    String[] count_serv_point_branchname(String service_point); //Returns branch names for each service point

    @Query("SELECT COUNT(" + Branch_data.COLUMN_BRANCHNAME + ") FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_SERVICE_POINT
            + " == :service_point GROUP BY " + Branch_data.COLUMN_BRANCHNAME + " ORDER BY " + Branch_data.COLUMN_BRANCHNAME + " ASC")
    int[] count_serv_point_totalnumb(String service_point); //Returns count grouped by branchname
    //--------------------------------------------------------------------------------------------------------------------------

    @Query("SELECT COUNT(" + Branch_data.COLUMN_BRANCHNAME + ") FROM " + Branch_data.TABLE + " WHERE "
            + Branch_data.COLUMN_SERVICE_POINT + " == :servicepoint AND "
            + Branch_data.COLUMN_FEEDBACKS + " == :feedback AND "
            + Branch_data.COLUMN_BRANCHNAME + " == :branchname ")
    int count_serv_feedback(String servicepoint, String branchname, Boolean feedback);

    @Query("SELECT DISTINCT " + Branch_data.COLUMN_BRANCHNAME + " FROM " + Branch_data.TABLE +
            " ORDER BY " + Branch_data.COLUMN_BRANCHNAME + " ASC")
    String[] branch_names();

    @Query(selectTable + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " LIKE :branchname")
    int count_branchName(String branchname);

    //@Query(selectTable + " GROUP BY " + Branch_data.COLUMN_DATE)
    //int[] count_date();

    @Query(selectTable + " WHERE " + Branch_data.COLUMN_FEEDBACKS + " == :userfeed" )
    int count_feedback(Boolean userfeed);

    @Query(selectTable + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " LIKE :branchname AND "
            + Branch_data.COLUMN_FEEDBACKS + " == :feedback")
    int count_branchname_feedback(String branchname, Boolean feedback);

    @Query("SELECT * FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " == :branchname ")
    List<Branch_data> branch_feedbacks(String branchname);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addFeedback(Branch_data branch_data);

    //TODO FIX DELETE QUERY
    @Query("DELETE FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " == :branchname")
    void delete_all_branchData(String branchname);

    @Query(selectTable + " WHERE :query")
    int custom_count_query(String query);
    //=============================================FEEDBACK=========================================


    //===========================================DUTY ROSTER========================================
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDuty_roster(Duty_roster duty_roster);

    @Query("SELECT DISTINCT " + Branch_data.COLUMN_BRANCHNAME + " FROM " + Duty_roster.TABLE
            + " WHERE " + Duty_roster.COLUMN_TEAM_NAME + " == :teamName ORDER BY " + Branch_data.COLUMN_BRANCHNAME + " ASC")
    String branchName(String teamName);

    @Query("SELECT DISTINCT * FROM " + Duty_roster.TABLE + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " == :branchname")
    List<Duty_roster> getDuty_rosters(String branchname);

    //For matching team and populating join table
    @Query("SELECT " + Duty_roster.COLUMN_TEAM_NAME + " FROM " + Duty_roster.TABLE
            + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " == :branchname AND :date_time BETWEEN "
            + Shift.COLUMN_START_TIME + " AND " + Shift.COLUMN_END_TIME)
    String team_shift(String date_time, String branchname);
    //===========================================DUTY ROSTER========================================

    //=======================================FEEDBACK ROSTER JOIN===================================
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert_teamFeedback(Team_Feedback_join team_feedback);

    @Query("SELECT DISTINCT " + Team_Feedback_join.COLUMN_TEAM_NAME + " FROM " + Team_Feedback_join.TABLE + " ORDER BY "
            + Branch_data.COLUMN_BRANCHNAME + " ASC")
    List<String> distinct_teamname();

    @Query("SELECT COUNT(*) FROM " + Team_Feedback_join.TABLE + " WHERE "
            + Team_Feedback_join.COLUMN_TEAM_NAME + " == :teamName AND " + Team_Feedback_join.COLUMN_FEEDBACK + " == :feedback" )
    int cont_team_feedback(String teamName,Boolean feedback);

    @Query("SELECT * FROM " + Team_Feedback_join.TABLE)
    Team_Feedback_join[] getTeamFeedbacks();

    @Query("SELECT COUNT(*) FROM " + Team_Feedback_join.TABLE +  " WHERE " + Team_Feedback_join.COLUMN_TEAM_NAME + " == :teamName")
    int count_team_total(String teamName);
    //=======================================FEEDBACK ROSTER JOIN===================================



}
