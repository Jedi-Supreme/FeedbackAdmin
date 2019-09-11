package com.softedge.feedbackadmin.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.common;
import com.softedge.feedbackadmin.databases.AppDatabase;
import com.softedge.feedbackadmin.models.Branch_data;
import com.softedge.feedbackadmin.models.Company_details;
import com.softedge.feedbackadmin.models.Duty_roster;
import com.softedge.feedbackadmin.models.Feedback_team_join;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
 import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    WeakReference<Dashboard> weak_dash;
    AppDatabase appDB;

    //=========================================ON CREATE============================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        weak_dash = new WeakReference<>(this);
        appDB = AppDatabase.getInstance(weak_dash.get());

        ActionBar actionBar = getSupportActionBar();

        SharedPreferences app_pref = common.app_pref(weak_dash.get());
        String comp_name = app_pref.getString(Company_details.COMPANY_NAME,"");
        String branch_name = app_pref.getString(Company_details.BRANCH_NAME,"");

        String all = comp_name + " - " + branch_name;
        if (actionBar != null){
            actionBar.setTitle(all);
        }
    }
    //=========================================ON CREATE============================================

    //-------------------------------------------OVERRIDE METHODS-----------------------------------
    @Override
    protected void onResume() {
        super.onResume();

        common.load_all_online_feedbacks(weak_dash.get(),true);

        //Insert feedback_team_join
        try {
            insert_feedback_teamJoin();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Entity join error: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    //-------------------------------------------OVERRIDE METHODS-----------------------------------


    //===============================================DEFINED METHODS================================

    void insert_feedback_teamJoin(){

        new Thread(() -> {

            try {

                for (String branchname: appDB.feedbackDAO().branch_names()){

                    List<Branch_data> branchFeedbackList =  appDB.feedbackDAO().branch_feedbacks(branchname);

                    for (Branch_data b_feedback: branchFeedbackList){

                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(b_feedback.getTimestamp()));

                        String time = timeFormat.format(calendar.getTime());
                        String teamname = appDB.feedbackDAO().team_on_duty(b_feedback.getDate(),time,b_feedback.getBranchname());

                        Feedback_team_join team_joinObj = new Feedback_team_join(
                                teamname,
                                b_feedback.getUserfeeds(),
                                b_feedback.getBranchname(),b_feedback.getTimestamp());

                        appDB.feedbackDAO().insert_teamFeedback(team_joinObj);

                    }

                }


            }catch (Exception ignored){

            }

        }).start();

    }

    //===============================================DEFINED METHODS================================


    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-CLICK LISTENERS-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void Click_listener(View view) {

        switch (view.getId()){

            case R.id.bt_dash_report:
                //Toast.makeText(getApplicationContext(),"All count: "
                  //      + appDB.feedbackDAO().countAll(),Toast.LENGTH_SHORT).show();
                toReport();
                break;

            case R.id.bt_dash_settings:
                Toast.makeText(getApplicationContext(),"Count: " + appDB.feedbackDAO().getTeamRosters().length,
                        Toast.LENGTH_LONG).show();
                /*for (Feedback_team_join join : appDB.feedbackDAO().getTeamRosters()){

                    if (join != null){
                        Toast.makeText(getApplicationContext(),
                                "time: " + join.getTimeStamp() + "\n branch: " + join.getBranchName()
                                        + "\n feedback: " + join.getFeedBacks() + "\n Team: " + join.getTeamName()
                                        + "\n Count: " + appDB.feedbackDAO().getTeamRosters().length, Toast.LENGTH_SHORT).show();
                    }
                }*/
                break;

                //TODO debug date problem on team join table and design report view for teams

            case R.id.bt_dash_logout:
                for (Feedback_team_join join : appDB.feedbackDAO().getTeamRosters()){

                    if (join != null){
                        Toast.makeText(getApplicationContext(),
                                "time: " + join.getTimeStamp() + "\n branch: " + join.getBranchName()
                                        + "\n feedback: " + join.getFeedBacks() + "\n Team: " + join.getTeamName()
                                        + "\n Count: " + appDB.feedbackDAO().getTeamRosters().length, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.bt_dash_duty_roster:
                toRoster();
                break;


        }
    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-CLICK LISTENERS-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    //----------------------------------------------INTENTS-----------------------------------------
    void toReport(){
        Intent report_intent = new Intent(getApplicationContext(),ReportsActivity.class);
        startActivity(report_intent);
    }

    void toRoster(){
        Intent roster_intent = new Intent(getApplicationContext(), Duty_RosterActivity.class);
        startActivity(roster_intent);
    }
    //----------------------------------------------INTENTS-----------------------------------------



}
