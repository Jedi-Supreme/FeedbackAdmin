package com.softedge.feedbackadmin.activities;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.adapters.Branch_report_recy_Adapter;
import com.softedge.feedbackadmin.common;
import com.softedge.feedbackadmin.databases.AppDatabase;
import com.softedge.feedbackadmin.models.Company_details;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class ReportsActivity extends AppCompatActivity {

    final String SUMMARY_TAG = "Summary";
    final String CUSTOM_TAG = "Query";
    TabHost report_tabhost;
    WeakReference<ReportsActivity> weak_report;

    AppDatabase appDB;

    TextView tv_sum_total_count, tv_sum_total_good, tv_sum_total_bad;
    RecyclerView recy_sum_report;

    //=========================================ON CREATE============================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        weak_report = new WeakReference<>(this);
        appDB = AppDatabase.getInstance(weak_report.get());

        tv_sum_total_count = findViewById(R.id.tv_sum_total_count);
        tv_sum_total_good = findViewById(R.id.tv_sum_total_good);
        tv_sum_total_bad = findViewById(R.id.tv_sum_total_bad);
        recy_sum_report  = findViewById(R.id.recy_sum_report);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            SharedPreferences app_pref = common.app_pref(weak_report.get());
            actionBar.setTitle(app_pref.getString(Company_details.COMPANY_NAME,"Reports"));
        }

        //------------------------------------------TAB HOST----------------------------------------
        report_tabhost = findViewById(R.id.report_tabhost);
        report_tabhost.setup();

        TabHost.TabSpec querySpec = report_tabhost.newTabSpec(CUSTOM_TAG);
        TabHost.TabSpec sumSpec = report_tabhost.newTabSpec(SUMMARY_TAG);

        querySpec.setIndicator(CUSTOM_TAG);
        sumSpec.setIndicator(SUMMARY_TAG);

        querySpec.setContent(R.id.cust_report);
        sumSpec.setContent(R.id.sum_report);

        report_tabhost.addTab(sumSpec);
        report_tabhost.addTab(querySpec);
        //------------------------------------------TAB HOST----------------------------------------


        try {
            show_calculations();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error showing calculations: " + e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    //=========================================ON CREATE============================================

    //-----------------------------------------------DEFINED METHODS--------------------------------
    void show_calculations(){

        int total = appDB.feedbackDAO().countAll();
        int good_rev = appDB.feedbackDAO().count_feedback(common.GOOD_REVIEW);
        int bad_rev = appDB.feedbackDAO().count_feedback(common.BAD_REVIEW);

        String total_rev = "Total Reviews - " + total;
        tv_sum_total_count.setText(total_rev);

        String good_perc = "Positive Reviews - "
                + good_rev + " (" + String.format(Locale.getDefault(),"%.2f",common.percentage(good_rev,total)) + "%)";
        tv_sum_total_good.setText(good_perc);

        String bad_perc = "Negative Reviews - "
                + bad_rev + " (" + String.format(Locale.getDefault(),"%.2f",common.percentage(bad_rev,total)) + "%)";
        tv_sum_total_bad.setText(bad_perc);

        try {
            load_branch_calculations();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error loading Branches data: " + e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    void load_branch_calculations(){
        Branch_report_recy_Adapter bAdapter = new Branch_report_recy_Adapter(appDB.feedbackDAO().branch_names());
        recy_sum_report.setLayoutManager(new LinearLayoutManager(weak_report.get()));
        recy_sum_report.setAdapter(bAdapter);
    }

    //-----------------------------------------------DEFINED METHODS--------------------------------
}
