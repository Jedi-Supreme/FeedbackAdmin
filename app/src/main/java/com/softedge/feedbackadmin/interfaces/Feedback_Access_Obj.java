package com.softedge.feedbackadmin.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.softedge.feedbackadmin.models.Branch_data;
import com.softedge.feedbackadmin.models.Duty_roster;

@Dao
public interface Feedback_Access_Obj {

    String selectTable = "SELECT COUNT(*) FROM " + Branch_data.TABLE;

    @Query(selectTable)
    int countAll();

    @Query("SELECT DISTINCT " + Branch_data.COLUMN_BRANCHNAME + " FROM " + Branch_data.TABLE + " ORDER BY " + Branch_data.COLUMN_BRANCHNAME + " ASC")
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

    @Insert
    void addFeedback(Branch_data branch_data);

    @Insert
    void addTeam_shift(Duty_roster duty_roster);

    @Query("DELETE FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " == :branchname")
    void delete_all_branchData(String branchname);

    @Query(selectTable + " WHERE :query")
    int custom_count_query(String query);

}
