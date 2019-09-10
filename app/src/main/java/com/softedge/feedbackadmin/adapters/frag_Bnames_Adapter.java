package com.softedge.feedbackadmin.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.activities.Duty_RosterActivity;
import com.softedge.feedbackadmin.fragments.Add_team_fragment;

import java.lang.ref.WeakReference;

public class frag_Bnames_Adapter extends RecyclerView.Adapter {

    private String[] branch_names;

    public frag_Bnames_Adapter(String[] branch_names) {
        this.branch_names = branch_names;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_frag_branch_names, parent, false);
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

    public class branchnames_list_holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_row_branchname;
        WeakReference<Context> weak_mcontext;
        String bname;

        branchnames_list_holder(View itemView) {
            super(itemView);

            weak_mcontext = new WeakReference<>(itemView.getContext());
            tv_row_branchname = itemView.findViewById(R.id.tv_frag_branchname);
            itemView.setOnClickListener(this);
        }

        void bind_views(String branch) {
            bname = branch;
            tv_row_branchname.setText(branch);
        }

        @Override
        public void onClick(View v) {

            if (weak_mcontext.get() instanceof Duty_RosterActivity){
                //Toast.makeText(weak_mcontext.get(),"Adapter contect matches Duty activity", Toast.LENGTH_LONG).show();
                Fragment fragment = new Add_team_fragment();
                ((Duty_RosterActivity) weak_mcontext.get()).loadFragment(fragment,bname);
            }
        }

    }
}
