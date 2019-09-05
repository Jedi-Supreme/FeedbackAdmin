package com.softedge.feedbackadmin.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.ArrayList;

public class Registration_activity extends AppCompatActivity {

    ArrayList<String> enterprice_list;
    WeakReference<Registration_activity> weak_comp_reg;
    Spinner sp_ent_list;
    ConstraintLayout const_comp_reg;
    TextInputEditText et_reg_branchname, et_reg_npass, et_reg_confpass;

    //===========================================ON CREATE==========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_details);

        weak_comp_reg = new WeakReference<>(this);
        enterprice_list = new ArrayList<>();

        sp_ent_list = findViewById(R.id.sp_ent_list);
        const_comp_reg = findViewById(R.id.const_comp_reg);
        et_reg_branchname = findViewById(R.id.et_reg_bn);
        et_reg_npass = findViewById(R.id.et_reg_npass);
        et_reg_confpass = findViewById(R.id.et_reg_conf_pass);

        TextInputLayout input_reg_npass = findViewById(R.id.input_reg_npass),
                input_reg_confpass = findViewById(R.id.input_reg_conf_pass);

        et_reg_npass.setOnFocusChangeListener((v, hasFocus) -> input_reg_npass.setPasswordVisibilityToggleEnabled(hasFocus));
        et_reg_confpass.setOnFocusChangeListener((v, hasFocus) -> input_reg_confpass.setPasswordVisibilityToggleEnabled(hasFocus));

        try {
            load_enterprise_list();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error loading names " + e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    //===========================================ON CREATE==========================================

    //-------------------------------------------------DEFINED METHODS------------------------------

    void test_input(){
        if (sp_ent_list.getSelectedItemPosition() <= 0){
            common.Mysnackbar(const_comp_reg,"Select Company name.", Snackbar.LENGTH_SHORT).show();
        }else if (et_reg_branchname.getText().toString().isEmpty() || et_reg_branchname.getText().toString().equals("")){
            common.Mysnackbar(const_comp_reg,"Enter branch name.", Snackbar.LENGTH_SHORT).show();
        }else if (et_reg_npass.getText().toString().isEmpty() || et_reg_npass.getText().toString().equals("")){
            common.Mysnackbar(const_comp_reg,"Enter new password.", Snackbar.LENGTH_SHORT).show();
        }else if (!et_reg_confpass.getText().toString().equals(et_reg_npass.getText().toString())){
            common.Mysnackbar(const_comp_reg,"Passwords do not match.", Snackbar.LENGTH_SHORT).show();
        }else {

            if (FirebaseAuth.getInstance().getCurrentUser() != null){
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(et_reg_confpass.getText().toString());
            }

            Company_details companyDetails = new Company_details(
                    sp_ent_list.getItemAtPosition(sp_ent_list.getSelectedItemPosition()).toString(),
                    et_reg_branchname.getText().toString()
                    );
            save_toFB(companyDetails);
        }
    }

    void save_toFB(Company_details companyDetails){

        String uid;
        String companyconfirm = companyDetails.getComp_name() + " - " + companyDetails.getBranch_name();

        DatabaseReference company_ref = FirebaseDatabase.getInstance().getReference("Registered_Companies");

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            company_ref.child(uid).setValue(companyDetails).addOnCompleteListener(task -> {
                if (task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Registration Successful for "
                            + companyconfirm , Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor pref_editor = common.app_pref(weak_comp_reg.get()).edit();

                    pref_editor.putString(Company_details.COMPANY_NAME,companyDetails.getComp_name());
                    pref_editor.putString(Company_details.BRANCH_NAME,companyDetails.getBranch_name());
                    pref_editor.putString(Company_details.COMPANY_ID,uid);
                    pref_editor.apply();

                    toDashboard();
                }else {

                    Toast.makeText(getApplicationContext(), "Registration failed for "
                            + companyconfirm + "\n Try again later.", Toast.LENGTH_SHORT).show();

                }
            });
        }



    }

    void load_enterprise_list(){
        DatabaseReference ent_ref = FirebaseDatabase.getInstance().getReference("Enterprise_Details");

        ent_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot name : dataSnapshot.getChildren()){
                    String ent_name = name.getValue(String.class);
                    if (ent_name != null && !ent_name.equals("")){
                        //Toast.makeText(getApplicationContext(), ent_name, Toast.LENGTH_SHORT).show();
                        enterprice_list.add(ent_name);
                    }
                }

                if (enterprice_list.size() > 0){
                    ArrayAdapter<String> sp_adapter = new ArrayAdapter<>(weak_comp_reg.get(),android.R.layout.simple_spinner_dropdown_item,
                            enterprice_list);
                    sp_ent_list.setAdapter(sp_adapter);
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
        test_input();
    }

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=INTENTS-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=---=-
    void toDashboard(){
        Intent dash_intent = new Intent(getApplicationContext(),Dashboard.class);
        dash_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dash_intent);
        finish();
    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=INTENTS-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=---=-
}
