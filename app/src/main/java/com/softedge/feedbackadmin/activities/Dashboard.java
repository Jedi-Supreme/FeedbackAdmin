package com.softedge.feedbackadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.softedge.feedbackadmin.R;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            login_intent();
        }

    }



    void login_intent(){
        Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
        login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login_intent);
        finish();
    }
}
