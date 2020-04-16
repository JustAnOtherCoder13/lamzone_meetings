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
    private final static Calendar CALENDAR = Calendar.getInstance();
    private final static int DAY_OF_MONTH = CALENDAR.get(Calendar.DAY_OF_MONTH);
    private final static int MONTH = CALENDAR.get(Calendar.MONTH);
    private final static int YEAR = CALENDAR.get(Calendar.YEAR);
    private static Date HOUR;
    private static int DAY_OF_MONTH2 = CALENDAR.get(Calendar.DAY_OF_MONTH) - 1;
    public static Date TODAY;
    public static Date OTHER_DAY;

    private static int getOtherDay() {
        if (DAY_OF_MONTH2 == 0) {
            DAY_OF_MONTH2 = CALENDAR.get(DAY_OF_MONTH) + 1;
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

    private static String getRandomHour() {
        Random random = new Random();
        int randomHour = random.nextInt(9)+9;
        int randomMinute = random.nextInt(60);
        try {
            HOUR = new SimpleDateFormat("HH:mm", Locale.FRANCE).parse(String.valueOf(randomHour).concat(":").concat(String.valueOf(randomMinute)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        return formatter.format(HOUR);
    }
    public static void generateDummyHour(List<Meeting> meetings){
        for (Meeting meeting:meetings) {
            meeting.setHour(getRandomHour());
        }
}}
