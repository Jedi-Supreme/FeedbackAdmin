package com.softedge.feedbackadmin.models.Shifts;

import com.softedge.feedbackadmin.models.Shift;

public class Afternoon_shift extends Shift {

    private static final String START_TIME = "14:00";
    private static final String END_TIME = "19:59";
    private static final String SHIFT_NAME = "Afternoon";

    public Afternoon_shift() {
        super(SHIFT_NAME,START_TIME, END_TIME);
    }
}
