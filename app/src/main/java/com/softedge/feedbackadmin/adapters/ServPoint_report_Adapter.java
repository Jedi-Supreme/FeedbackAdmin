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
import com.softedge.feedbackadmin.models.ServPoint_Count;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

public class ServPoint_report_Adapter extends RecyclerView.Adapter {

    private ArrayList<ServPoint_Count> servPoint_count_obj;

    //TODO Work on adapter for service point branch grouping

    public ServPoint_report_Adapter(ArrayList<ServPoint_Count> servPoint_count_obj) {
        this.servPoint_count_obj = servPoint_count_obj;
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
        ((servPoint_list_holder) holder).bind_views(servPoint_count_obj.get(position));
    }

    @Override
    public int getItemCount() {
        return servPoint_count_obj.size();
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

        void bind_views(ServPoint_Count servPoint) {

            int total = servPoint.getTotal_count();
            int serv_good = servPoint.getGood_count();//appDb.feedbackDAO().count_serv_feedback(servPoint, common.GOOD_REVIEW);
            int serv_bad = servPoint.getBad_count();//appDb.feedbackDAO().count_serv_feedback(servPoint, common.BAD_REVIEW);

            tv_serv_branch.setText(servPoint.getBranchname());
            tv_servPoint_name.setText(servPoint.getServ_point_name());

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
