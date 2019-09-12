package com.softedge.feedbackadmin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.common;
import com.softedge.feedbackadmin.databases.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public class Teams_feedback_Adapter extends RecyclerView.Adapter {

    private List<String> team_feedbacks;

    //TODO Work on adpter for team data grouping

    public Teams_feedback_Adapter(List<String> team_feedbacks) {
        this.team_feedbacks = team_feedbacks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_report_team_list, parent, false);
        return new branchnames_list_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((branchnames_list_holder) holder).bind_views(team_feedbacks.get(position));
    }

    @Override
    public int getItemCount() {
        return team_feedbacks.size();
    }

    public class branchnames_list_holder extends RecyclerView.ViewHolder{

        TextView tv_teams_name, tv_teams_branch, tv_teams_total, tv_teams_good, tv_teams_bad;
        WeakReference<Context> weak_mcontext;
        AppDatabase appDb;

        branchnames_list_holder(View itemView) {
            super(itemView);

            weak_mcontext = new WeakReference<>(itemView.getContext());

            tv_teams_name = itemView.findViewById(R.id.tv_teams_name);
            tv_teams_total = itemView.findViewById(R.id.tv_teams_total);
            tv_teams_branch = itemView.findViewById(R.id.tv_teams_branch);
            tv_teams_good = itemView.findViewById(R.id.tv_teams_good);
            tv_teams_bad = itemView.findViewById(R.id.tv_teams_bad);

            appDb = AppDatabase.getInstance(weak_mcontext.get());
        }

        void bind_views(String teamName) {

            int total = appDb.feedbackDAO().count_team_total(teamName);
            int team_good = appDb.feedbackDAO().cont_team_feedback(teamName, common.GOOD_REVIEW);
            int team_bad = appDb.feedbackDAO().cont_team_feedback(teamName, common.BAD_REVIEW);

            tv_teams_branch.setText(appDb.feedbackDAO().branchName(teamName));
            tv_teams_name.setText(teamName);

            double good_value = common.percentage(team_good,total);
            double bad_value = common.percentage(team_bad,total);

            if (bad_value > 1){
                tv_teams_bad.setTextColor(weak_mcontext.get().getResources().getColor(R.color.colorPrimary));
            }else {
                tv_teams_bad.setTextColor(weak_mcontext.get().getResources().getColor(R.color.green));
            }

            String total_rev = "Total Reviews - " + total;
            tv_teams_total.setText(total_rev);

            String good_perc = "Positive Reviews - "
                    + team_good + " (" + String.format(Locale.getDefault(),"%.2f",good_value) + "%)";
            tv_teams_good.setText(good_perc);

            String bad_perc = "Negative Reviews - "
                    + team_bad + " (" + String.format(Locale.getDefault(),"%.2f",bad_value) + "%)";
            tv_teams_bad.setText(bad_perc);

        }

    }
}
