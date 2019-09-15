package com.softedge.feedbackadmin.models.Shifts;

import com.softedge.feedbackadmin.models.Shift;

public class Night_shift extends Shift {

    private static final String START_TIME = "20:00:00";
    private static final String END_TIME = "07:59:00";
    private static final String SHIFT_NAME = "Night";

    public Night_shift() {
        super(SHIFT_NAME,START_TIME, END_TIME);
    }
}
