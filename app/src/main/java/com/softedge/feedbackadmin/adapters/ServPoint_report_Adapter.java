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

public class ServPoint_report_Adapter extends RecyclerView.Adapter {

    private List<String> servPoint_feedbacks;

    //TODO Work on adpter for team data grouping

    public ServPoint_report_Adapter(List<String> servPoint_feedbacks) {
        this.servPoint_feedbacks = servPoint_feedbacks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_report_serv_point_list, parent, false);
        return new servPoint_list_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((servPoint_list_holder) holder).bind_views(servPoint_feedbacks.get(position));
    }

    @Override
    public int getItemCount() {
        return servPoint_feedbacks.size();
    }

    public class servPoint_list_holder extends RecyclerView.ViewHolder{

        TextView tv_servPoint_name, tv_serv_branch, tv_serv_total, tv_serv_good, tv_serv_bad;
        WeakReference<Context> weak_mcontext;
        AppDatabase appDb;

        servPoint_list_holder(View itemView) {
            super(itemView);

            weak_mcontext = new WeakReference<>(itemView.getContext());

            tv_servPoint_name = itemView.findViewById(R.id.tv_servpoint_name);
            tv_serv_total = itemView.findViewById(R.id.tv_servpoint_total);
            tv_serv_branch = itemView.findViewById(R.id.tv_servpoint_branch);
            tv_serv_good = itemView.findViewById(R.id.tv_servpoint_good);
            tv_serv_bad = itemView.findViewById(R.id.tv_servpoint_bad);

            appDb = AppDatabase.getInstance(weak_mcontext.get());
        }

        void bind_views(String servPoint) {

            int total = appDb.feedbackDAO().count_serv_point(servPoint);
            int serv_good = appDb.feedbackDAO().count_serv_feedback(servPoint, common.GOOD_REVIEW);
            int serv_bad = appDb.feedbackDAO().count_serv_feedback(servPoint, common.BAD_REVIEW);

            tv_serv_branch.setText(appDb.feedbackDAO().bname_servPoint(servPoint));
            tv_servPoint_name.setText(servPoint);

            double good_value = common.percentage(serv_good,total);
            double bad_value = common.percentage(serv_bad,total);

            if (bad_value > 1){
                tv_serv_bad.setTextColor(weak_mcontext.get().getResources().getColor(R.color.colorPrimary));
            }else {
                tv_serv_bad.setTextColor(weak_mcontext.get().getResources().getColor(R.color.green));
            }

            String total_rev = "Total Reviews - " + total;
            tv_serv_total.setText(total_rev);

            String good_perc = "Positive Reviews - "
                    + serv_good + " (" + String.format(Locale.getDefault(),"%.2f",good_value) + "%)";
            tv_serv_good.setText(good_perc);

            String bad_perc = "Negative Reviews - "
                    + serv_bad + " (" + String.format(Locale.getDefault(),"%.2f",bad_value) + "%)";
            tv_serv_bad.setText(bad_perc);

        }

    }
}
