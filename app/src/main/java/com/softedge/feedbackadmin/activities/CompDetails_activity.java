package com.softedge.feedbackadmin.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softedge.feedbackadmin.R;

public class CompDetails_activity extends AppCompatActivity {

    //===========================================ON CREATE==========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_details);

        try {
            load_enterprise_list();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error loading names " + e.toString(), Toast.LENGTH_LONG).show();
        }


    }
    //===========================================ON CREATE==========================================

    //-------------------------------------------------DEFINED METHODS------------------------------
    void load_enterprise_list(){
        DatabaseReference ent_ref = FirebaseDatabase.getInstance().getReference("Enterprise_Details");

        ent_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot name : dataSnapshot.getChildren()){
                    String ent_name = name.getValue(String.class);
                    if (ent_name != null && !ent_name.equals("")){
                        Toast.makeText(getApplicationContext(), ent_name, Toast.LENGTH_SHORT).show();
                    }
                }

                ent_ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //-------------------------------------------------DEFINED METHODS------------------------------

    public void VerifyInfo(View view) {
    }
}
