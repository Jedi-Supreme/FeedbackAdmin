package com.softedge.feedbackadmin.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

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

public class LoadScreen extends AppCompatActivity {

    WeakReference<LoadScreen> weak_load;
    ImageView iv_loading;
    TextView tv_load_text;

    //===========================================ON CREATE==========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadscreen);

        weak_load = new WeakReference<>(this);

        tv_load_text = findViewById(R.id.tv_load_txt);
        iv_loading = findViewById(R.id.iv_loading);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1200); //You can manage the blinking time with this parameter
        anim.setStartOffset(200);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        iv_loading.startAnimation(anim);

        SharedPreferences app_pref = common.app_pref(weak_load.get());
        String comp_name = app_pref.getString(Company_details.COMPANY_NAME,"");

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            login_intent();
        }else {

            if (comp_name.equals("")){
                load_company_Data();
            }else {
                toDashboard();
            }
        }

    }
    //===========================================ON CREATE==========================================

    //-------------------------------------------------DEFINED METHODS------------------------------
    void load_company_Data(){

        String uid;

        DatabaseReference comp_ref = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.fb_reg_comp));

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            comp_ref.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Company_details company = dataSnapshot.getValue(Company_details.class);

                    if (company != null){

                        SharedPreferences.Editor pref_editor = common.app_pref(weak_load.get()).edit();

                        pref_editor.putString(Company_details.COMPANY_NAME,company.getComp_name());
                        pref_editor.putString(Company_details.BRANCH_NAME, company.getBranch_name());

                        if (FirebaseAuth.getInstance().getCurrentUser() != null){
                            pref_editor.putString(Company_details.COMPANY_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }

                        pref_editor.apply();
                        toDashboard();
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
        Intent comp_details_intent = new Intent(getApplicationContext(), Registration_activity.class);
        comp_details_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(comp_details_intent);
        finish();
    }

    void toDashboard(){

        common.load_all_online_feedbacks(weak_load.get(),true);

        Intent dash_intent = new Intent(getApplicationContext(),Dashboard.class);
        dash_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dash_intent);
        finish();
    }


}
