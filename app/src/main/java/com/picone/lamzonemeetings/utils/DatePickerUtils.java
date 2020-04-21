package com.picone.lamzonemeetings.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatePickerUtils {
    public static String FULL_DATE;
    public static Date PICKED_DATE;
    public static String FULL_HOUR;
    private static Date PICKED_HOUR;
    private static Date PICKED_MINUTE;

    public static void formatPickedDate(int dayOfMonth, int monthOfYear, int year) {

        try {
            PICKED_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(dayOfMonth + "/" + monthOfYear + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        FULL_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(PICKED_DATE);
    }

    public static void formatPickedHour(int hour, int minute) {
        try {
            PICKED_HOUR = new SimpleDateFormat("HH", Locale.FRANCE).parse(String.valueOf(hour));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            PICKED_MINUTE = new SimpleDateFormat("mm", Locale.FRANCE).parse(String.valueOf(minute));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        FULL_HOUR = new SimpleDateFormat("HH", Locale.FRANCE).format(PICKED_HOUR).concat("h").concat(new SimpleDateFormat("mm", Locale.FRANCE).format(PICKED_MINUTE));
    }
}
