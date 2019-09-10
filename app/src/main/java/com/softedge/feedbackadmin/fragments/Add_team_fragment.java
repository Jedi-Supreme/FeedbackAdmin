package com.softedge.feedbackadmin.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.common;
import com.softedge.feedbackadmin.models.Duty_roster;
import com.softedge.feedbackadmin.models.Shift;
import com.softedge.feedbackadmin.models.Shifts.Afternoon_shift;
import com.softedge.feedbackadmin.models.Shifts.Morning_shift;
import com.softedge.feedbackadmin.models.Shifts.Night_shift;

public class Add_team_fragment extends Fragment implements View.OnClickListener {

    TextInputEditText et_team_start, et_team_end, et_team_name;
    Button bt_team_add;
    RecyclerView recy_add_team;
    AppCompatSpinner sp_team_shift;

    ViewGroup parent_view;

    Duty_roster roster;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_add_team,container,false);
        parent_view = container;

        roster = new Duty_roster();

        et_team_name = view.findViewById(R.id.et_frag_teamname);
        et_team_start = view.findViewById(R.id.et_frag_teamstart);
        et_team_end = view.findViewById(R.id.et_frag_team_end);

        bt_team_add = view.findViewById(R.id.bt_add_team);
        recy_add_team = view.findViewById(R.id.recy_add_team);
        sp_team_shift = view.findViewById(R.id.sp_shift_options);

        bt_team_add.setOnClickListener(this);

        return  view;
    }

    //---------------------------------------OVERRIDE METHOD----------------------------------------
    @Override
    public void onClick(View v) {
        testinput();
    }
    //---------------------------------------OVERRIDE METHOD----------------------------------------

    //======================================DEFINED METHODS=========================================
    void testinput(){

        if (et_team_name.getText().toString().isEmpty() || et_team_name.getText().toString().equals("")){
            common.Mysnackbar(parent_view,"Enter Team name", Snackbar.LENGTH_SHORT).show();
        }else if (et_team_start.getText().toString().isEmpty() || et_team_start.getText().toString().equals("")){
            common.Mysnackbar(parent_view,"Pick start date", Snackbar.LENGTH_SHORT).show();
        }else if (et_team_end.getText().toString().isEmpty() || et_team_end.getText().toString().equals("")){
            common.Mysnackbar(parent_view,"Pick end date", Snackbar.LENGTH_SHORT).show();
        }else if (sp_team_shift.getSelectedItemPosition() <= 0){
            common.Mysnackbar(parent_view,"Select Shift", Snackbar.LENGTH_SHORT).show();
        }else {

            Shift team_shift;

            roster.setTeam_name(et_team_name.getText().toString());
            roster.setStart_date(et_team_start.getText().toString());
            roster.setEnd_date(et_team_end.getText().toString());

            switch (sp_team_shift.getSelectedItemPosition()){

                case 1:
                    team_shift = new Morning_shift();
                    roster.setShift(team_shift);
                    break;

                case 2:
                    team_shift = new Afternoon_shift();
                    roster.setShift(team_shift);
                    break;

                case 3:
                    team_shift = new Night_shift();
                    roster.setShift(team_shift);
                    break;

            }

            if (getActivity() != null){
                Toast.makeText(getActivity().getApplicationContext(),roster.getShift().getEnd_time(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    //======================================DEFINED METHODS=========================================
}