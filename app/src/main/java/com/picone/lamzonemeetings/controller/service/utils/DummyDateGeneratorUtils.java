package com.picone.lamzonemeetings.controller.service.utils;

import com.picone.lamzonemeetings.model.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DummyDateGeneratorUtils {
    public final static Calendar CALENDAR = Calendar.getInstance();
    private final static int DAY_OF_MONTH = CALENDAR.get(Calendar.DAY_OF_MONTH);
    private final static int MONTH = CALENDAR.get(Calendar.MONTH);
    private final static int YEAR = CALENDAR.get(Calendar.YEAR);
    private static Date HOUR;
    private static Date MINUTE;
    public static Date TODAY;
    private static Date DUMMY_DATE;
    private static Random RANDOM = new Random();

    private static Date generateRandomDate() {
        int bound = 0;
        if (MONTH == 2) {
            bound = 28;
        } else if (MONTH % 2 == 0 || MONTH == 9 || MONTH == 11 && MONTH != 8 && MONTH != 10 && MONTH != 12) {
            bound = 29;
        } else {
            bound = 30;
        }
        int randomDay = 0;
        //for test, to have only first meeting date on today
        do {
            randomDay = new Random().nextInt(bound) + 1;
        } while (randomDay == DAY_OF_MONTH);

        try {
            DUMMY_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(randomDay + "/" + MONTH + "/" + YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DUMMY_DATE;
    }

    static {
        try {
            TODAY = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(DAY_OF_MONTH + "/" + MONTH + "/" + YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void generateDummyDate(List<Meeting> meetings) {
        for (int i = 0; i < meetings.size(); i++) {
            if (i != 0) meetings.get(i).setDate(generateRandomDate());
            else meetings.get(0).setDate(TODAY);
        }
    }

    private static String generateRandomHour() {
        int randomHour = RANDOM.nextInt(9) + 9;
        int randomMinute = RANDOM.nextInt(60);
        try {
            HOUR = new SimpleDateFormat("HH", Locale.FRANCE).parse(String.valueOf(randomHour));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            MINUTE = new SimpleDateFormat("mm", Locale.FRANCE).parse(String.valueOf(randomMinute));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatterHour = new SimpleDateFormat("HH", Locale.FRANCE);
        SimpleDateFormat formatterMinute = new SimpleDateFormat("mm", Locale.FRANCE);
        return formatterHour.format(HOUR).concat("h").concat(formatterMinute.format(MINUTE));
    }

    public static void generateDummyHour(List<Meeting> meetings) {
        for (int i = 1; i < meetings.size(); i++) {
            meetings.get(i).setHour(generateRandomHour());
        }
    }
    //for test
    public static String getHour() {
        int hour = CALENDAR.get(Calendar.HOUR_OF_DAY);
        int minute = CALENDAR.get(Calendar.MINUTE);

        try {
            HOUR = new SimpleDateFormat("HH", Locale.FRANCE).parse(String.valueOf(hour));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            MINUTE = new SimpleDateFormat("mm", Locale.FRANCE).parse(String.valueOf(minute));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("HH", Locale.FRANCE).format(HOUR).concat("h").concat(new SimpleDateFormat("mm", Locale.FRANCE).format(MINUTE));
    }

}
