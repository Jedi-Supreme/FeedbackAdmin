package com.softedge.feedbackadmin.activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.common;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText et_login_password,et_login_email;
    ConstraintLayout const_login;
    ProgressBar probar_login;

    //===========================================ON CREATE==========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_login_password = findViewById(R.id.et_login_password);
        et_login_email = findViewById(R.id.et_login_email);
        const_login = findViewById(R.id.const_login);
        probar_login = findViewById(R.id.probar_login);

        TextInputLayout inpt_login_pass = findViewById(R.id.input_login_password);

        et_login_password.setOnFocusChangeListener((v, hasFocus) ->
                inpt_login_pass.setPasswordVisibilityToggleEnabled(hasFocus));

    }
    //===========================================ON CREATE==========================================

    void testInputs() {

        if (et_login_email.getText().toString().isEmpty() || et_login_email.getText().toString().equals("")) {
            common.Mysnackbar(const_login, "Enter Valid Email", Snackbar.LENGTH_SHORT).show();

        } else if (et_login_password.getText().toString().isEmpty() || et_login_password.getText().toString().equals("")) {
            common.Mysnackbar(const_login, "Enter Password", Snackbar.LENGTH_SHORT).show();

        } else {
            login_with_credentials(et_login_email.getText().toString(), et_login_password.getText().toString());


        }


    }

    void login_with_credentials(final String email, String password) {

        probar_login.setVisibility(View.VISIBLE);

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        toDashboard();
                        probar_login.setVisibility(View.INVISIBLE);
                        probar_login.clearAnimation();

                    } else {

                        // If sign in fails, display a message to the user.
                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {

                            common.Mysnackbar(const_login, "Invalid Email Address",
                                    Snackbar.LENGTH_SHORT).show();

                            probar_login.setVisibility(View.INVISIBLE);
                            probar_login.clearAnimation();

                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            common.Mysnackbar(const_login, "Wrong Password",
                                    Snackbar.LENGTH_SHORT).show();

                            probar_login.setVisibility(View.INVISIBLE);
                            probar_login.clearAnimation();
                        }


                    }

                });

    }

    private void toDashboard() {
        Intent dash_intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(dash_intent);
        finish();
    }


    public void login(View view) {
        testInputs();
    }


}
