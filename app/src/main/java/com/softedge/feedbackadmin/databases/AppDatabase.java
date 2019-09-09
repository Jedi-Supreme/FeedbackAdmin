package com.softedge.feedbackadmin.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.softedge.feedbackadmin.interfaces.Feedback_Access_Obj;
import com.softedge.feedbackadmin.models.Branch_data;
import com.softedge.feedbackadmin.models.Duty_roster;

@Database(entities = {Branch_data.class, Duty_roster.class},exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDB_instance;

    public static AppDatabase getInstance(Context context){

        if (appDB_instance == null){

            String DB_NAME = "Admin_DB";
            appDB_instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return appDB_instance;
    }

    public abstract Feedback_Access_Obj feedbackDAO();


}
