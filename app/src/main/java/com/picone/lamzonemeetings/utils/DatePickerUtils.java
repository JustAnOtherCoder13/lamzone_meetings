package com.picone.lamzonemeetings.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatePickerUtils {
    public static String FULL_DATE;
    public static Date PICKED_DATE;
    public static String FULL_HOUR;
    public static Date PICKED_HOUR;

    public static void formatPickedDate(int dayOfMonth, int monthOfYear, int year) {

        try {
            PICKED_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(dayOfMonth + "/" + monthOfYear + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        FULL_DATE = formatter.format(PICKED_DATE);
    }

    public static void formatPickedHour(int hour, int minute) {
        try {
            PICKED_HOUR = new SimpleDateFormat("HH:mm", Locale.FRANCE).parse(String.valueOf(hour).concat(":").concat(String.valueOf(minute)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        FULL_HOUR = formatter.format(PICKED_HOUR);
    }
}
