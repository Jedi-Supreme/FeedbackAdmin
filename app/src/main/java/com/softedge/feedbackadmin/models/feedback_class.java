package com.softedge.feedbackadmin.models;

import com.google.firebase.analytics.FirebaseAnalytics;

public class feedback_class {

    private boolean user_feedback;
    private String time_stamp;
    private String service_point;

    public feedback_class() {
    }

    public feedback_class(boolean user_feedback, String time_stamp, String service_point){
        this.user_feedback = user_feedback;
        this.time_stamp = time_stamp;
        this.service_point = service_point;
    }

    public boolean isUser_feedback() {
        return user_feedback;
    }

    public void setUser_feedback(boolean user_feedback) {
        this.user_feedback = user_feedback;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getService_point() {
        return service_point;
    }

    public void setService_point(String service_point) {
        this.service_point = service_point;
    }
}
