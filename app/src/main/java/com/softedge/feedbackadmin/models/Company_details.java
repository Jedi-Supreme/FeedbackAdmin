package com.softedge.feedbackadmin.models;

public class Company_details {

    public static final String COMPANY_ID = "company_id";
    public static final String COMPANY_NAME = "company_name";
    public static final String BRANCH_NAME = "branch_name";
    static final String SERVICE_POINT = "service_point";

    private String comp_name;
    private String branch_name;
    private String service_point;

    public Company_details() {
    }

    public Company_details(String comp_name, String branch_name) {
        this.comp_name = comp_name;
        this.branch_name = branch_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getService_point() {
        return service_point;
    }

    public void setService_point(String service_point) {
        this.service_point = service_point;
    }
}
