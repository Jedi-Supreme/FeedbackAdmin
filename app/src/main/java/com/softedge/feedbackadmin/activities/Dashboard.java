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
import com.softedge.feedbackadmin.models.Company_details;

import java.lang.ref.WeakReference;

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
    }
    //-------------------------------------------OVERRIDE METHODS-----------------------------------


    //===============================================DEFINED METHODS================================

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
                Toast.makeText(getApplicationContext(),"Feedback count:"
                        + appDB.feedbackDAO().count_feedback(common.GOOD_REVIEW),Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_dash_logout:

                for (String branchname : appDB.feedbackDAO().branch_names()){
                    Toast.makeText(getApplicationContext(),"Branch count: " + branchname
                                    + " - " + appDB.feedbackDAO().count_branchName(branchname)
                            ,Toast.LENGTH_SHORT).show();
                }


                break;

        }
    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-CLICK LISTENERS-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    //----------------------------------------------INTENTS-----------------------------------------
    void toReport(){
        Intent report_intent = new Intent(getApplicationContext(),ReportsActivity.class);
        startActivity(report_intent);
    }
    //----------------------------------------------INTENTS-----------------------------------------



}
