package com.picone.lamzonemeetings.utils;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.model.Meeting;

import java.util.Date;

import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.TODAY;

public class CircleColorUtils {

    public static int setCircleColor(Meeting meeting) {
        Date date = meeting.getDate();
        if (date.compareTo(TODAY) == 0) {
            return R.color.today_meeting;
        } else if (date.compareTo(TODAY) < 0) {
            return R.color.passed_meeting;
        } else return R.color.future_meeting;
    }
}
