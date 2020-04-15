package com.picone.lamzonemeetings.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatePickerUtils {
    public static String FULL_DATE;
    public static Date PICKED_DATE;

    public static void formatPickedDate(int dayOfMonth, int monthOfYear, int year) {

        try {
            PICKED_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(dayOfMonth + "/" + monthOfYear + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
        FULL_DATE = formatter.format(PICKED_DATE);

    }
}
