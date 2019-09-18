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
import com.softedge.feedbackadmin.models.Shifts.Afternoon_shift;
import com.softedge.feedbackadmin.models.Team_Feedback_join;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
 import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

        common.load_all_online_feedbacks(getApplicationContext(),true);

        try {
            insert_feedback_teamJoin();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Entity join error: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    //-------------------------------------------OVERRIDE METHODS-----------------------------------


    //===============================================DEFINED METHODS================================

    void insert_feedback_teamJoin(){

        Executor executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            for (String branchname: appDB.feedbackDAO().branch_names()){

                List<Branch_data> branchFeedbackList =  appDB.feedbackDAO().branch_feedbacks(branchname);

                for (Branch_data b_data: branchFeedbackList){

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    SimpleDateFormat db_date_format = new SimpleDateFormat(common.db_date_format,Locale.getDefault());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(b_data.getTimestamp()));

                    Afternoon_shift afternoon_shift = new Afternoon_shift();

                    String time = timeFormat.format(calendar.getTime());
                    String date = db_date_format.format(calendar.getTime());

                    try {
                        if (calendar.getTime().after(timeFormat.parse(afternoon_shift.getEnd_time()))){
                            //String teamname = appDB.feedbackDAO().night_duty(date,time,b_data.getBranchname());
                            String teamname = appDB.feedbackDAO().team_shift(date+" "+time,b_data.getBranchname());

                            Team_Feedback_join team_joinObj = new Team_Feedback_join(
                                    teamname,
                                    b_data.getUserfeeds(),
                                    b_data.getBranchname(),b_data.getTimestamp());

                            appDB.feedbackDAO().insert_teamFeedback(team_joinObj);

                        }else {
                            //String teamname = appDB.feedbackDAO().team_on_duty(date,time,b_data.getBranchname());
                            String teamname = appDB.feedbackDAO().team_shift(date+" "+time,b_data.getBranchname());

                            Team_Feedback_join team_joinObj = new Team_Feedback_join(
                                    teamname,
                                    b_data.getUserfeeds(),
                                    b_data.getBranchname(),b_data.getTimestamp());

                            appDB.feedbackDAO().insert_teamFeedback(team_joinObj);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Time parse error: "
                        //      + e.toString(),Toast.LENGTH_LONG).show();
                    }


                }
            }
        });

        /*for (String branchname: appDB.feedbackDAO().branch_names()){

            List<Branch_data> branchFeedbackList =  appDB.feedbackDAO().branch_feedbacks(branchname);

            for (Branch_data b_feedback: branchFeedbackList){

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                SimpleDateFormat db_date_format = new SimpleDateFormat(common.db_date_format,Locale.getDefault());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(b_feedback.getTimestamp()));

                String time = timeFormat.format(calendar.getTime());
                String date = db_date_format.format(calendar.getTime());

                Afternoon_shift afternoon_shift = new Afternoon_shift();

                try {
                    if (calendar.getTime().after(timeFormat.parse(afternoon_shift.getEnd_time()))){
                        //String teamname = appDB.feedbackDAO().night_duty(date,time,b_feedback.getBranchname());
                        String teamname = appDB.feedbackDAO().team_shift(date+" "+time,b_feedback.getBranchname());

                        Team_Feedback_join team_joinObj = new Team_Feedback_join(
                                teamname,
                                b_feedback.getUserfeeds(),
                                b_feedback.getBranchname(),b_feedback.getTimestamp());

                        appDB.feedbackDAO().insert_teamFeedback(team_joinObj);
                        Toast.makeText(getApplicationContext(),"Data: " + teamname, Toast.LENGTH_SHORT).show();

                    }else {
                        String teamname = appDB.feedbackDAO().team_shift(date+" "+time,b_feedback.getBranchname());

                        Team_Feedback_join team_joinObj = new Team_Feedback_join(
                                teamname,
                                b_feedback.getUserfeeds(),
                                b_feedback.getBranchname(),b_feedback.getTimestamp());

                        appDB.feedbackDAO().insert_teamFeedback(team_joinObj);
                        Toast.makeText(getApplicationContext(),"Data: " + teamname, Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Time parse error: "
                            + e.toString(),Toast.LENGTH_LONG).show();
                }

                //TODO FIX TEAM NAME FETCH



            }

        }*/

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
                //Toast.makeText(getApplicationContext(),"Count: " + appDB.feedbackDAO().getAllDuty_rosters().size(),
                 //       Toast.LENGTH_LONG).show();
                String[] branchnames = appDB.feedbackDAO().count_serv_point_branchname("Pharmacy");
                int[] serv_counts = appDB.feedbackDAO().count_serv_point_totalnumb("Pharmacy");
                //int[] serv_goodcount = appDB.feedbackDAO().count_serv_feedback("Pharmacy",common.GOOD_REVIEW);
                //int[] serv_badCount = appDB.feedbackDAO().count_serv_feedback("Pharmacy",common.BAD_REVIEW);


                if (branchnames.length == serv_counts.length){

                    for (int x = 0; x< branchnames.length; x++){
                        Toast.makeText(getApplicationContext(),branchnames[x] + " - " + serv_counts[x]
                                        +"\n Good: " + appDB.feedbackDAO().count_serv_feedback("Pharmacy",branchnames[x],common.GOOD_REVIEW)
                                        + " - Bad: " + appDB.feedbackDAO().count_serv_feedback("Pharmacy",branchnames[x],common.BAD_REVIEW),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.bt_dash_logout:
                for (Team_Feedback_join join : appDB.feedbackDAO().getTeamFeedbacks()){

                    if (join != null){
                        Toast.makeText(getApplicationContext(),
                                "time: " + join.getTimeStamp() + "\n branch: " + join.getBranchName()
                                        + "\n feedback: " + join.getFeedBacks() + "\n Team: " + join.getTeamName()
                                        + "\n Count: " + appDB.feedbackDAO().getTeamFeedbacks().length, Toast.LENGTH_SHORT).show();
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
