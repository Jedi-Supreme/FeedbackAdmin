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
import java.util.Locale;

public class Branch_report_recy_Adapter extends RecyclerView.Adapter {

    private String[] branch_names;

    public Branch_report_recy_Adapter(String[] branch_names) {
        this.branch_names = branch_names;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_report_branch_list, parent, false);
        return new branchnames_list_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((branchnames_list_holder) holder).bind_views(branch_names[position]);
    }

    @Override
    public int getItemCount() {
        return branch_names.length;
    }

    public class branchnames_list_holder extends RecyclerView.ViewHolder{

        TextView tv_row_branchname, tv_row_branch_total, tv_row_branch_good, tv_row_branch_bad;
        WeakReference<Context> weak_mcontext;
        AppDatabase appDb;

        branchnames_list_holder(View itemView) {
            super(itemView);

            weak_mcontext = new WeakReference<>(itemView.getContext());
            //Todo initialise delete button and add functions

            tv_row_branchname = itemView.findViewById(R.id.tv_row_branchname);
            tv_row_branch_total = itemView.findViewById(R.id.tv_row_branch_total);
            tv_row_branch_good = itemView.findViewById(R.id.tv_row_branch_good);
            tv_row_branch_bad = itemView.findViewById(R.id.tv_row_branch_bad);

            appDb = AppDatabase.getInstance(weak_mcontext.get());
        }

        void bind_views(String branch) {

            int total = appDb.feedbackDAO().count_branchName(branch);
            int branch_good = appDb.feedbackDAO().count_branchname_feedback(branch, common.GOOD_REVIEW);
            int branch_bad = appDb.feedbackDAO().count_branchname_feedback(branch, common.BAD_REVIEW);


            tv_row_branchname.setText(branch);

            String total_rev = "Total Reviews - " + total;
            tv_row_branch_total.setText(total_rev);

            String good_perc = "Positive Reviews - "
                    + branch_good + " (" + String.format(Locale.getDefault(),"%.2f",common.percentage(branch_good,total)) + "%)";
            tv_row_branch_good.setText(good_perc);

            String bad_perc = "Negative Reviews - "
                    + branch_bad + " (" + String.format(Locale.getDefault(),"%.2f",common.percentage(branch_bad,total)) + "%)";
            tv_row_branch_bad.setText(bad_perc);

        }

    }
}
