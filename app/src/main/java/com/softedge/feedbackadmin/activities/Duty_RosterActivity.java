package com.softedge.feedbackadmin.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.common;
import com.softedge.feedbackadmin.models.Branch_data;

public class Duty_RosterActivity extends AppCompatActivity {

    ActionBar actionBar;

    //====================================ON CREATE=================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_roster);

        Bundle roster_bundle = getIntent().getExtras();
        actionBar = getActionBar();

        if (roster_bundle != null){

            if (roster_bundle.getInt("action") == common.ROSTER_LIST){
                loadRosters();
            }
        }


            /*try {
                Fragment fragment = new Add_team_fragment();
                loadFragment(fragment);
            }catch (Exception ignored){}*/

    }
    //====================================ON CREATE=================================================

    //-----------------------------------------------DEFINED METHODS--------------------------------
    public void loadFragment(Fragment fragment, String branchname){
        Bundle bundle = new Bundle();
        bundle.putString(Branch_data.COLUMN_BRANCHNAME,branchname);
        fragment.setArguments(bundle);

        FragmentManager frag_manager = getFragmentManager();
        FragmentTransaction frag_transact = frag_manager.beginTransaction();
        frag_transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        frag_transact.replace(R.id.frag_roster_container,fragment);
        frag_transact.addToBackStack(null);
        frag_transact.commit();
    }

    public void changeActivity_title(String title){

        if (actionBar != null){
            actionBar.setTitle(title);
        }
    }

    public void loadRosters(){
        //TODO load roster lists
    }
    //-----------------------------------------------DEFINED METHODS--------------------------------
}
