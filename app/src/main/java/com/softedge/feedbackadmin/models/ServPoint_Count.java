package com.softedge.feedbackadmin.models;

import android.arch.persistence.room.ColumnInfo;

public class ServPoint_Count {

    private String branchname;
    private String serv_point_name;

    private int total_count;
    private int good_count;
    private int bad_count;

    public ServPoint_Count (String branchname, String serv_point_name,
             int total_count, int good_count, int bad_count)
    {
        this.branchname = branchname;
        this.serv_point_name = serv_point_name;
        this.total_count = total_count;
        this.good_count = good_count;
        this.bad_count = bad_count;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getServ_point_name() {
        return serv_point_name;
    }

    public void setServ_point_name(String serv_point_name) {
        this.serv_point_name = serv_point_name;
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }

    public int getBad_count() {
        return bad_count;
    }

    public void setBad_count(int bad_count) {
        this.bad_count = bad_count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
}
