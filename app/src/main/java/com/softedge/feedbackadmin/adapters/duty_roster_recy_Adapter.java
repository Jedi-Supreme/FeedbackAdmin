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
import com.softedge.feedbackadmin.models.Duty_roster;

import java.lang.ref.WeakReference;
import java.util.List;

public class duty_roster_recy_Adapter extends RecyclerView.Adapter {

    private static List<Duty_roster> dutyRosters;

    public duty_roster_recy_Adapter(List<Duty_roster> dutyRosters) {
        duty_roster_recy_Adapter.dutyRosters = dutyRosters;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_teams_roster, parent, false);
        return new item_list_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((item_list_holder) holder).bind_views(dutyRosters.get(position));
    }

    @Override
    public int getItemCount() {
        return dutyRosters.size();
    }

    public class item_list_holder extends RecyclerView.ViewHolder {

        TextView tv_teamName, tv_roster_Date, tv_shift_name;

        item_list_holder(View itemView) {
            super(itemView);

            WeakReference<Context> weak_mcontext = new WeakReference<>(itemView.getContext());

            tv_teamName = itemView.findViewById(R.id.tv_frag_teamName);
            tv_roster_Date = itemView.findViewById(R.id.tv_frag_teamDate);
            tv_shift_name = itemView.findViewById(R.id.tv_frag_team_shift);
        }

        void bind_views(final Duty_roster team_duty) {

            tv_teamName.setText(team_duty.getTeam_name());
            String date_text = common.humanDate(team_duty.getStart_date()) + " - " + common.humanDate(team_duty.getEnd_date());
            tv_roster_Date.setText(date_text);
            tv_shift_name.setText(team_duty.getShift().getShift_name());
        }

    }

}
