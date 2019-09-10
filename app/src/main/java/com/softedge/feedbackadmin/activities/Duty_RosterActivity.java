package com.softedge.feedbackadmin.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.fragments.Add_team_fragment;

public class Duty_RosterActivity extends AppCompatActivity {


    //====================================ON CREATE=================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_roster);

        //Bundle app_bundle = getIntent().getExtras();


            try {
                Fragment fragment = new Add_team_fragment();
                loadFragment(fragment);
            }catch (Exception ignored){}

    }
    //====================================ON CREATE=================================================

    //-----------------------------------------------DEFINED METHODS--------------------------------
    void loadFragment(Fragment fragment){
        FragmentManager frag_manager = getSupportFragmentManager();
        FragmentTransaction frag_transact = frag_manager.beginTransaction();
        frag_transact.replace(R.id.frag_roster_view,fragment);
        frag_transact.commit();
    }
    //-----------------------------------------------DEFINED METHODS--------------------------------
}
