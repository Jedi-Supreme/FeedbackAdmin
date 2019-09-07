package com.softedge.feedbackadmin.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.softedge.feedbackadmin.models.Branch_data;

@Dao
public interface Feedback_Access_Obj {

    @Query("SELECT COUNT(*) FROM " + Branch_data.TABLE)
    int countAll();

    @Query("SELECT COUNT(*) FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " LIKE :branchname")
    int count_by_branchname(String branchname);

    @Query("SELECT COUNT(*) FROM " + Branch_data.TABLE + " GROUP BY " + Branch_data.COLUMN_DATE)
    int count_by_date();

    @Insert
    void addFeedback(Branch_data branch_data);

    @Query("DELETE FROM " + Branch_data.TABLE + " WHERE " + Branch_data.COLUMN_BRANCHNAME + " LIKE :branchname")
    void delete_all_branchData(String branchname);

    //Todo bild Database and finish queries
}
