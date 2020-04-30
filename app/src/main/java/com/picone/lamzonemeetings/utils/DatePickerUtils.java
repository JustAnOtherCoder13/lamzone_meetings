package com.picone.lamzonemeetings.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatePickerUtils {

    public static Date formatPickedDate(int dayOfMonth, int monthOfYear, int year) {
        Date value = null;
        try {
            value = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(dayOfMonth + "/" + monthOfYear + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String formatPickedHour(int hour, int minute) {
        return new SimpleDateFormat("HH", Locale.FRANCE).format(formatHour("HH", hour))
                .concat("h")
                .concat(new SimpleDateFormat("mm", Locale.FRANCE).format(formatHour("mm", minute)));
    }

    private static Date formatHour(String format, int time) {
        Date value = null;
        try {
            value = new SimpleDateFormat(format, Locale.FRANCE).parse(String.valueOf(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }
}
