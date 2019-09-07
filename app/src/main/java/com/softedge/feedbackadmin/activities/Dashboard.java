package com.softedge.feedbackadmin.activities;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.common;
import com.softedge.feedbackadmin.models.Company_details;

import java.lang.ref.WeakReference;

public class Dashboard extends AppCompatActivity {

    WeakReference<Dashboard> weak_dash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        weak_dash = new WeakReference<>(this);

        ActionBar actionBar = getSupportActionBar();

        SharedPreferences app_pref = common.app_pref(weak_dash.get());
        String comp_name = app_pref.getString(Company_details.COMPANY_NAME,"");
        String branch_name = app_pref.getString(Company_details.BRANCH_NAME,"");

        String all = comp_name + " - " + branch_name;
        if (actionBar != null){
            actionBar.setTitle(all);
        }
    }

    public void Click_listener(View view) {

        switch (view.getId()){

            case R.id.bt_dash_report:
                break;
            case R.id.bt_dash_settings:
                break;
            case R.id.bt_dash_logout:
                break;

        }
    }
}
