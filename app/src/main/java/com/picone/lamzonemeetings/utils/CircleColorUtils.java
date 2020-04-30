package com.picone.lamzonemeetings.utils;

import com.picone.lamzonemeetings.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_DAY_OF_MONTH;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_MONTH;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_YEAR;

public class CircleColorUtils {

    public CircleColorUtils() {
    }

    public static int setCircleColor(Date date) {
        if (date.compareTo(today()) == 0) {
            return R.color.today_meeting;
        } else if (date.compareTo(today()) < 0) {
            return R.color.passed_meeting;
        } else return R.color.future_meeting;
    }

    private static Date today() {
        Date value = null;
        try {
            value = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(MY_DAY_OF_MONTH + "/" + MY_MONTH + "/" + MY_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }
}