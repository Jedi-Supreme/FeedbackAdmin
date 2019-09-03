package com.softedge.feedbackadmin.activities;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softedge.feedbackadmin.R;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText et_login_password,et_login_email;

    //===========================================ON CREATE==========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_login_password = findViewById(R.id.et_login_password);
        et_login_email = findViewById(R.id.et_login_email);

        TextInputLayout inpt_login_pass = findViewById(R.id.input_login_password);

        et_login_password.setOnFocusChangeListener((v, hasFocus) ->
                inpt_login_pass.setPasswordVisibilityToggleEnabled(hasFocus));

    }
    //===========================================ON CREATE==========================================



}
