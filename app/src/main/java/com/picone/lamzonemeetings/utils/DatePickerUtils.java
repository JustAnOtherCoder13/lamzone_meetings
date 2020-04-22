package com.picone.lamzonemeetings.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.dateOrHour;
import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.formatHour;

public class DatePickerUtils {
    public static String FULL_DATE;
    public static Date PICKED_DATE;
    public static String FULL_HOUR;

    public static void formatPickedDate(int dayOfMonth, int monthOfYear, int year) {

        PICKED_DATE = dateOrHour(dayOfMonth,monthOfYear,year);
        FULL_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(PICKED_DATE);
    }

    public static void formatPickedHour(int hour, int minute) {
        FULL_HOUR = formatHour(hour, minute);
    }
}
