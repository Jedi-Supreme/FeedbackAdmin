package com.softedge.feedbackadmin.fragments;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.adapters.duty_roster_recy_Adapter;
import com.softedge.feedbackadmin.common;
import com.softedge.feedbackadmin.databases.AppDatabase;
import com.softedge.feedbackadmin.models.Branch_data;
import com.softedge.feedbackadmin.models.Duty_roster;
import com.softedge.feedbackadmin.models.Shift;
import com.softedge.feedbackadmin.models.Shifts.Afternoon_shift;
import com.softedge.feedbackadmin.models.Shifts.Morning_shift;
import com.softedge.feedbackadmin.models.Shifts.Night_shift;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Add_team_fragment extends Fragment implements View.OnClickListener {

    TextInputEditText et_team_start, et_team_end, et_team_name;
    Button bt_team_add;
    RecyclerView recy_add_team;
    AppCompatSpinner sp_team_shift;

    ViewGroup parent_view;

    String bname;

    AppDatabase appDB;

    DatePickerDialog startDate_picker, endDate_picker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_add_team,container,false);
        parent_view = container;

        if (getArguments()!=null){
            bname = getArguments().getString(Branch_data.COLUMN_BRANCHNAME);
        }

        et_team_name = view.findViewById(R.id.et_frag_teamname);
        et_team_start = view.findViewById(R.id.et_frag_teamstart);
        et_team_end = view.findViewById(R.id.et_frag_team_end);

        bt_team_add = view.findViewById(R.id.bt_add_team);
        recy_add_team = view.findViewById(R.id.recy_add_team);
        sp_team_shift = view.findViewById(R.id.sp_shift_options);

        bt_team_add.setOnClickListener(this);
        et_team_start.setOnClickListener(this);
        et_team_end.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        startDate_picker = new DatePickerDialog(parent_view.getContext(), R.style.DatePickerTheme, start_dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        endDate_picker = new DatePickerDialog(parent_view.getContext(), R.style.DatePickerTheme, end_dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        appDB = AppDatabase.getInstance(parent_view.getContext());

        try {
            refresh_list();
        }catch (Exception ignored){}

        return  view;
    }

    //---------------------------------------OVERRIDE METHOD----------------------------------------


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ActionBar actionBar = getActivity().getActionBar();

        if (actionBar!= null){
            actionBar.setTitle("Add Team");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.bt_add_team:
                testinput();
                break;

            case R.id.et_frag_teamstart:
                pick_start_date();
                break;

            case R.id.et_frag_team_end:
                pick_end_date();
                break;

        }

        /*if (bname != null){
            Toast.makeText(getActivity().getApplicationContext(),
                    bname,Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (getArguments() != null) {
            getArguments().clear();
        }
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
        }else if (!validDate(et_team_start.getText().toString(),et_team_end.getText().toString())){
            common.Mysnackbar(parent_view,"Invalid Dates entered", Snackbar.LENGTH_SHORT).show();
        }else {

            Duty_roster roster;
            Shift team_shift;

            switch (sp_team_shift.getSelectedItemPosition()){

                case 1:
                    team_shift = new Morning_shift();
                    roster = new Duty_roster(
                            et_team_start.getText().toString(),
                            et_team_end.getText().toString(),
                            et_team_name.getText().toString(),team_shift,bname);

                    appDB.feedbackDAO().addDuty_roster(roster);
                    refresh_list();
                    break;

                case 2:
                    team_shift = new Afternoon_shift();
                    roster = new Duty_roster(
                            et_team_start.getText().toString(),
                            et_team_end.getText().toString(),
                            et_team_name.getText().toString(),team_shift,bname);

                    appDB.feedbackDAO().addDuty_roster(roster);
                    refresh_list();
                    break;

                case 3:
                    team_shift = new Night_shift();
                    roster = new Duty_roster(
                            et_team_start.getText().toString(),
                            et_team_end.getText().toString(),
                            et_team_name.getText().toString(),team_shift,bname);

                    appDB.feedbackDAO().addDuty_roster(roster);
                    refresh_list();
                    break;
            }

            //Todo add roster to DB and refresh recycler view

            //Toast.makeText(parent_view.getContext(),"Success",Toast.LENGTH_SHORT).show();
        }
    }

    //-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^_^-^-^-^-^-^-^-DATE-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-
    void pick_start_date(){
        startDate_picker.show();
    }
    void pick_end_date(){
        endDate_picker.show();
    }

    DatePickerDialog.OnDateSetListener start_dateSetListener = (view, year, month, dayOfMonth)
            -> showStartDate(year, month, dayOfMonth);

    DatePickerDialog.OnDateSetListener end_dateSetListener = (view, year, month, dayOfMonth)
            -> showEndDate(year, month, dayOfMonth);

    public void showStartDate(int year, int month, int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(common.db_date_format, Locale.getDefault());
        SimpleDateFormat parseDateFormat = new SimpleDateFormat(common.db_date_format, Locale.getDefault());
        String userdate;
        Date date;

        if (month+1<10){
            userdate =  day + "-" + "0" + (month + 1) + "-" + year;
            //et_reg_dob.setText(userdate);
            try {
                date = parseDateFormat.parse(userdate);
                et_team_start.setText(simpleDateFormat.format(date));
            } catch (ParseException e) {
                Toast.makeText(parent_view.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }

        }else{
            userdate = day + "-" + (month + 1) + "-" + year;
            //et_reg_dob.setText(userdate);
            try {
                date = parseDateFormat.parse(userdate);
                et_team_start.setText(simpleDateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(parent_view.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void showEndDate(int year, int month, int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(common.db_date_format, Locale.getDefault());
        SimpleDateFormat parseDateFormat = new SimpleDateFormat(common.db_date_format, Locale.getDefault());
        String userdate;
        Date date;

        if (month+1<10){
            userdate =  day + "-" + "0" + (month + 1) + "-" + year;
            //et_reg_dob.setText(userdate);
            try {
                date = parseDateFormat.parse(userdate);
                et_team_end.setText(simpleDateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(parent_view.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }

        }else{
            userdate = day + "-" + (month + 1) + "-" + year;
            //et_reg_dob.setText(userdate);
            try {
                date = parseDateFormat.parse(userdate);
                et_team_end.setText(simpleDateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(parent_view.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    //-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^_^-^-^-^-^-^-^-DATE-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-

    Boolean validDate(String startdate, String enddate){

        SimpleDateFormat parseDateFormat = new SimpleDateFormat(common.db_date_format, Locale.getDefault());
        //Toast.makeText(parent_view.getContext(),enddate,Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();

        try {
            calendar.setTime(parseDateFormat.parse(startdate));
            Date start_date = calendar.getTime();

            calendar1.setTime(parseDateFormat.parse(enddate));
            Date end_date = calendar1.getTime();

            return !end_date.before(start_date);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(parent_view.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    //TODO create input clearer method

    void refresh_list(){
        duty_roster_recy_Adapter dutyAdapter = new duty_roster_recy_Adapter(appDB.feedbackDAO().getDuty_rosters(bname));
        recy_add_team.setLayoutManager(new LinearLayoutManager(parent_view.getContext()));
        recy_add_team.setAdapter(dutyAdapter);
    }
    //======================================DEFINED METHODS=========================================
}
