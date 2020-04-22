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
    public static int RIGHT_NOW_HOUR = CALENDAR.get(Calendar.HOUR_OF_DAY);
    public static int RIGHT_NOW_MINUTE = CALENDAR.get(Calendar.MINUTE);
    public static Date TODAY = dateOrHour(DAY_OF_MONTH, MONTH, YEAR);
    private static Random RANDOM = new Random();

    private static Date generateRandomDate() {
        int bound;
        if (MONTH == 2) {
            bound = 28;
        } else if (MONTH % 2 == 0 || MONTH == 9 || MONTH == 11 && MONTH != 8 && MONTH != 10 && MONTH != 12) {
            bound = 29;
        } else {
            bound = 30;
        }
        int randomDay;
        //for test, to have only first meeting date on today
        do {
            randomDay = new Random().nextInt(bound) + 1;
        } while (randomDay == DAY_OF_MONTH);

        return dateOrHour(randomDay, MONTH, YEAR);
    }


    public static void generateDummyDate(List<Meeting> meetings) {
        for (int i = 0; i < meetings.size(); i++) {
            if (i != 0) meetings.get(i).setDate(generateRandomDate());
                //set first meeting's date to today for tests
            else meetings.get(0).setDate(TODAY);
        }
    }

    private static String generateRandomHour() {
        int randomHour = RANDOM.nextInt(9) + 8;
        int randomMinute = RANDOM.nextInt(59);
        return formatHour(randomHour, randomMinute);
    }

    public static void generateDummyHour(List<Meeting> meetings) {
        //skip the first meeting because hour is in DummyApiServiceGenerator
        for (int i = 1; i < meetings.size(); i++) {
            meetings.get(i).setHour(generateRandomHour());
        }
    }

    //for test get right now
    public static String getRightNow() {

        return formatHour(RIGHT_NOW_HOUR, RIGHT_NOW_MINUTE);
    }

    //methods to format hour or date
    private static Date dateOrHour(String format, int time) {
        Date value = null;
        try {
            value = new SimpleDateFormat(format, Locale.FRANCE).parse(String.valueOf(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Date dateOrHour(int day, int month, int year) {
        Date value = null;
        try {
            value = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(day + "/" + month + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String formatHour(int hour, int minute) {

        return new SimpleDateFormat("HH", Locale.FRANCE).format(dateOrHour("HH", hour))
                .concat("h")
                .concat(new SimpleDateFormat("mm", Locale.FRANCE).format(dateOrHour("mm", minute)));
    }
}
