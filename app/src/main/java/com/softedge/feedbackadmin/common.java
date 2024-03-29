package com.softedge.feedbackadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softedge.feedbackadmin.databases.AppDatabase;
import com.softedge.feedbackadmin.models.Branch_data;
import com.softedge.feedbackadmin.models.feedback_class;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class common {

    public static final Boolean GOOD_REVIEW = true;
    public static final Boolean BAD_REVIEW = false;
    public static final int ROSTER_LIST = 12;
    public static final String db_date_format = "yyyy-MM-dd";

    private static String time_to_date(String timeMillis){

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(Long.parseLong(timeMillis));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS", Locale.getDefault());

        return  simpleDateFormat.format(calendar.getTime());
    }

    public static Snackbar Mysnackbar(View parent_view, String message, int lenght) {

        final Snackbar snackbar = Snackbar.make(parent_view, message, lenght);
        snackbar.setActionTextColor(parent_view.getContext().getResources().getColor(R.color.colorPrimary));
        snackbar.setAction("Close", v -> snackbar.dismiss());

        return snackbar;
    }

    public static SharedPreferences app_pref(Context context){
        return context.getSharedPreferences("Feedback_pref", Context.MODE_PRIVATE);
    }

    public static String humanDate(String date){
        SimpleDateFormat parseformat = new SimpleDateFormat(db_date_format,Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyy",Locale.getDefault());

        try {
            Date date1 = parseformat.parse(date);
            return dateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void load_all_online_feedbacks(Context context, Boolean remove_listener){

        AppDatabase appDb = AppDatabase.getInstance(context);

        DatabaseReference feedback_ref =
                FirebaseDatabase
                        .getInstance()
                        .getReference(context.getResources().getString(R.string.fb_feedback));

        Executor executor = Executors.newFixedThreadPool(2);

        if (FirebaseAuth.getInstance().getCurrentUser()!= null){
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            feedback_ref.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot branch_snap : dataSnapshot.getChildren()){

                        for (DataSnapshot year_snap : branch_snap.getChildren()){

                            for ( DataSnapshot month_snap : year_snap.getChildren()){

                                for (DataSnapshot day_snap : month_snap.getChildren()){

                                    for (DataSnapshot feed_snap : day_snap.getChildren()){

                                        feedback_class fb_obj = feed_snap.getValue(feedback_class.class);

                                        if (fb_obj != null){

                                            String date = time_to_date(fb_obj.getTime_stamp());
                                            //Toast.makeText(context, "text: " + feed_snap.getKey(), Toast.LENGTH_SHORT).show();
                                            Branch_data bdata = new Branch_data
                                                    (fb_obj.getTime_stamp(),date,fb_obj.isUser_feedback(),
                                                            Objects.requireNonNull(branch_snap.getKey()),
                                                            fb_obj.getService_point());

                                            executor.execute(() -> appDb.feedbackDAO().addFeedback(bdata));

                                        }
                                    }
                                }
                            }
                        }

                    }

                    if (remove_listener){
                        feedback_ref.removeEventListener(this);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        /*new Thread(() -> {

        }).start();*/
    }

    public static double percentage(double amount, double total){

        return (amount/total) * 100;
    }

}
