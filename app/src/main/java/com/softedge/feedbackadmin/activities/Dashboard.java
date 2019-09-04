package com.softedge.feedbackadmin.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.common;
import com.softedge.feedbackadmin.models.Company_details;

import java.lang.ref.WeakReference;

public class Dashboard extends AppCompatActivity {

    WeakReference<Dashboard> weak_dash;

    //===========================================ON CREATE==========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        weak_dash = new WeakReference<>(this);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            login_intent();
        }else {
            load_company_Data();
        }

    }
    //===========================================ON CREATE==========================================

    //-------------------------------------------------DEFINED METHODS------------------------------
    void load_company_Data(){

        String uid;

        DatabaseReference comp_ref = FirebaseDatabase.getInstance().getReference("Enterprise_Details");

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            comp_ref.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Company_details company = dataSnapshot.getValue(Company_details.class);

                    if (company != null){

                        SharedPreferences.Editor pref_editor = common.app_pref(weak_dash.get()).edit();

                        pref_editor.putString(Company_details.COMPANY_NAME,company.getComp_name());
                        pref_editor.putString(Company_details.BRANCH_NAME, company.getBranch_name());

                        if (FirebaseAuth.getInstance().getCurrentUser() != null){
                            pref_editor.putString(Company_details.COMPANY_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }

                        pref_editor.apply();
                    }else {
                        toComp_details();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
    //-------------------------------------------------DEFINED METHODS------------------------------

    void login_intent(){
        Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
        login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login_intent);
        finish();
    }

    private void toComp_details(){
        Intent comp_details_intent = new Intent(getApplicationContext(), CompDetails_activity.class);
        startActivity(comp_details_intent);
    }
}
