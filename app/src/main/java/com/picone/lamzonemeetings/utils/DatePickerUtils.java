package com.picone.lamzonemeetings.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatePickerUtils {
    public static String FULL_DATE;
    public static Date PICKED_DATE;

    public static void workWithDatePickerData(int dayOfMonth, int monthOfYear, int year) {

        String mYear = (String.valueOf(year));
        String mMonth = (String.valueOf(monthOfYear + 1));
        if (monthOfYear < 10)
            mMonth = ("0".concat(mMonth));
        String mDay = (String.valueOf(dayOfMonth));
        if (dayOfMonth < 10)
            mDay = ("0".concat(mDay));
        FULL_DATE = mDay.concat("/").concat(mMonth).concat("/").concat(mYear);
        try {
            PICKED_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(dayOfMonth + "/" + monthOfYear + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
