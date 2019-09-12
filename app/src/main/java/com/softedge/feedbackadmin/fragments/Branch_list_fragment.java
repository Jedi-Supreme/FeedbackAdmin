package com.softedge.feedbackadmin.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.softedge.feedbackadmin.R;
import com.softedge.feedbackadmin.adapters.frag_Bnames_Adapter;
import com.softedge.feedbackadmin.databases.AppDatabase;

import java.lang.ref.WeakReference;

public class Branch_list_fragment extends Fragment {

    RecyclerView recy_frag_branches;
    AppDatabase appDB;

    WeakReference<Context> weakcontext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_branch_list,container,false);

        weakcontext = new WeakReference<>(view.getContext());

        appDB = AppDatabase.getInstance(weakcontext.get());


        recy_frag_branches = view.findViewById(R.id.recy_frag_branches);

        try {
            load_branches();
        }catch (Exception e){
            Toast.makeText(weakcontext.get(),"Error loading fragment:" + e.toString(),Toast.LENGTH_LONG).show();
        }

        return view;
    }

    void load_branches(){
        frag_Bnames_Adapter adapter = new frag_Bnames_Adapter(appDB.feedbackDAO().branch_names());
        recy_frag_branches.setLayoutManager(new LinearLayoutManager(weakcontext.get()));
        recy_frag_branches.setAdapter(adapter);
    }


}
