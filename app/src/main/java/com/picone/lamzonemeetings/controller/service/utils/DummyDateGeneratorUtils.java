package com.picone.lamzonemeetings.controller.service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DummyDateGeneratorUtils {
    private final static Calendar CALENDAR = Calendar.getInstance();
    private final static int DAY_OF_MONTH = CALENDAR.get(Calendar.DAY_OF_MONTH);
    private final static int MONTH = CALENDAR.get(Calendar.MONTH);
    private final static int YEAR = CALENDAR.get(Calendar.YEAR);
    private static int DAY_OF_MONTH2 = CALENDAR.get(Calendar.DAY_OF_MONTH) - 1;
    public static Date TODAY;
    public static Date OTHER_DAY;

    private static int getOtherDay() {
        if (DAY_OF_MONTH2 == 0) {
            DAY_OF_MONTH2 = CALENDAR.get(Calendar.DAY_OF_MONTH) + 1;
        }
        return DAY_OF_MONTH2;
    }

    static {
        try {
            TODAY = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(DAY_OF_MONTH + "/" + MONTH + "/" + YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            OTHER_DAY = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(getOtherDay() + "/" + MONTH + "/" + YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
