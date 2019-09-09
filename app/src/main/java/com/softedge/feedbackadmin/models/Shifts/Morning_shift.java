package com.softedge.feedbackadmin.models.Shifts;

import com.softedge.feedbackadmin.models.Shift;

public class Morning_shift extends Shift {

    private static final String START_TIME = "08:00";
    private static final String END_TIME = "13:59";

    public Morning_shift() {
        super(START_TIME, END_TIME);
    }
}
