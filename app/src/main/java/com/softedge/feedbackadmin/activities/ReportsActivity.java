package com.softedge.feedbackadmin.activities;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.adapters.Branch_report_recy_Adapter;
import com.softedge.feedbackadmin.adapters.ServPoint_report_Adapter;
import com.softedge.feedbackadmin.adapters.Teams_feedback_Adapter;
import com.softedge.feedbackadmin.common;
import com.softedge.feedbackadmin.databases.AppDatabase;
import com.softedge.feedbackadmin.models.Branch_data;
import com.softedge.feedbackadmin.models.Company_details;
import com.softedge.feedbackadmin.models.ServPoint_Count;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportsActivity extends AppCompatActivity {

    final String BRANCH_SUMMARY_TAG = "Branch Summary";
    //final String CUSTOM_TAG = "Query";
    final String SERVICE_POINT = "Service Points";
    final String TEAM_FEEDBACK_LIST = "Team Summary";
    TabHost report_tabhost;
    WeakReference<ReportsActivity> weak_report;

    AppDatabase appDB;

    View.OnClickListener delete_branch;

    TextView tv_sum_total_count, tv_sum_total_good, tv_sum_total_bad;
    RecyclerView recy_sum_report, recy_frag_listing, recy_serv_point;

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
        recy_frag_listing = findViewById(R.id.recy_frag_listing);
        recy_serv_point = findViewById(R.id.recy_frag_serv_point);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            SharedPreferences app_pref = common.app_pref(weak_report.get());
            actionBar.setTitle(app_pref.getString(Company_details.COMPANY_NAME,"Reports"));
        }

        //------------------------------------------TAB HOST----------------------------------------
        report_tabhost = findViewById(R.id.report_tabhost);
        report_tabhost.setup();

        TabHost.TabSpec serc_ptSpec = report_tabhost.newTabSpec(SERVICE_POINT);
        TabHost.TabSpec teamSpec = report_tabhost.newTabSpec(TEAM_FEEDBACK_LIST);
        TabHost.TabSpec sumSpec = report_tabhost.newTabSpec(BRANCH_SUMMARY_TAG);

        serc_ptSpec.setIndicator(SERVICE_POINT);
        teamSpec.setIndicator(TEAM_FEEDBACK_LIST);
        sumSpec.setIndicator(BRANCH_SUMMARY_TAG);

        serc_ptSpec.setContent(R.id.serv_point_report);
        teamSpec.setContent(R.id.team_feedback_list);
        sumSpec.setContent(R.id.branch_sum_report);

        report_tabhost.addTab(sumSpec);
        report_tabhost.addTab(teamSpec);
        report_tabhost.addTab(serc_ptSpec);
        //------------------------------------------TAB HOST----------------------------------------


        try {
            show_calculations();
             load_team_feedback_Calclations();
            load_serv_point_calc();
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

    void load_team_feedback_Calclations(){
        Teams_feedback_Adapter teams_feedback_adapter = new Teams_feedback_Adapter(appDB.feedbackDAO().distinct_teamname());
        recy_frag_listing.setLayoutManager(new LinearLayoutManager(weak_report.get()));
        recy_frag_listing.setAdapter(teams_feedback_adapter);
    }

    void load_serv_point_calc(){
        ServPoint_report_Adapter servPoint_adapter = new ServPoint_report_Adapter(servPoint_counts());
        recy_serv_point.setLayoutManager(new LinearLayoutManager(weak_report.get()));
        recy_serv_point.setAdapter(servPoint_adapter);
    }

    ArrayList<ServPoint_Count> servPoint_counts(){
        ArrayList<ServPoint_Count> servPointCounts = new ArrayList<>();

        List<String> distinct_servPoint_names = appDB.feedbackDAO().service_points_list();

        for (String servpoint_name : distinct_servPoint_names){

            String[] branchnames = appDB.feedbackDAO().count_serv_point_branchname(servpoint_name);
            int[] serv_counts = appDB.feedbackDAO().count_serv_point_totalnumb(servpoint_name);

            if (branchnames.length == serv_counts.length){

                for (int x = 0; x< branchnames.length; x++){
                    ServPoint_Count servCount = new ServPoint_Count(
                            branchnames[x],
                            servpoint_name,
                            serv_counts[x],
                            appDB.feedbackDAO().count_serv_feedback(servpoint_name,branchnames[x],common.GOOD_REVIEW),
                            appDB.feedbackDAO().count_serv_feedback(servpoint_name,branchnames[x],common.BAD_REVIEW)
                            );

                    servPointCounts.add(servCount);
                }

            }

        }

        return servPointCounts;
    }

    public void delete_function(String branchname){

         ExecutorService executor = Executors.newFixedThreadPool(2);

        AlertDialog alertDialog = new AlertDialog.Builder(weak_report.get()).create();

        alertDialog.setMessage("Are you sure you want to delete all data for " + branchname + "?");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> alertDialog.dismiss());

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", (dialog, which) -> {

            if (FirebaseAuth.getInstance().getCurrentUser() != null){

                //List<Branch_data> branchFeeds = appDB.feedbackDAO().branch_feedbacks(branchname);

                executor.execute(() -> delete_branch = v -> appDB.feedbackDAO().delete_all_branchData(branchname));

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference del_ref = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.fb_feedback));
                del_ref.child(uid).child(branchname).removeValue();

                if (executor.isTerminated()){
                load_branch_calculations();
                }
            }else {
                Toast.makeText(getApplicationContext(),"failed deleting", Toast.LENGTH_SHORT).show();
            }


            //recy_sum_report.getAdapter().notifyDataSetChanged();
        });

        alertDialog.show();

    }
    //-----------------------------------------------DEFINED METHODS--------------------------------
}
