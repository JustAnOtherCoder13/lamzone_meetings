package com.picone.lamzonemeetings.controller.service.utils;

import com.picone.lamzonemeetings.model.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_DAY_OF_MONTH;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_MONTH;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_YEAR;

public class DateGeneratorUtils {


    private static Date generateRandomDate() {
        int bound;
        if (MY_MONTH == 2) {
            bound = 27;
        } else if (MY_MONTH % 2 == 0 || MY_MONTH == 9 || MY_MONTH == 11 && MY_MONTH != 8 && MY_MONTH != 10 && MY_MONTH != 12) {
            bound = 29;
        } else {
            bound = 30;
        }
        int randomDay;
        //for test, to have only first meeting's date on today
        do {
            randomDay = new Random().nextInt(bound) + 1;
        } while (randomDay == MY_DAY_OF_MONTH);

        return formatDateOrHour(randomDay);
    }

    public static void generateDate(List<Meeting> meetings) {
        for (int i = 0; i < meetings.size(); i++) {
            if (i != 0) meetings.get(i).setDate(generateRandomDate());
                //set first meeting's date to today for tests
            else meetings.get(0).setDate(formatDateOrHour(MY_DAY_OF_MONTH));
        }
    }

    private static String generateRandomHour() {
        int randomHour;
        int randomMinute;
        do {
            randomHour = new Random().nextInt(9) + 8;
        } while (randomHour == 12);
        do {
            randomMinute = new Random().nextInt(59);
        } while (randomHour == 11 && randomMinute > 15);
        return getHourToString(randomHour, randomMinute);
    }

    public static void generateHour(List<Meeting> meetings) {
        //skip the first meeting because hour is in DummyApiServiceGenerator
        for (int i = 1; i < meetings.size(); i++) {
            meetings.get(i).setHour(generateRandomHour());
        }
    }

    //methods to format hour or date
    private static Date formatDateOrHour(String format, int time) {
        Date value = null;
        try {
            value = new SimpleDateFormat(format, Locale.FRANCE).parse(String.valueOf(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    private static Date formatDateOrHour(int day) {
        Date value = null;
        try {
            value = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(day + "/" + MY_MONTH + "/" + MY_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    private static String getHourToString(int hour, int minute) {

        return new SimpleDateFormat("HH", Locale.FRANCE).format(formatDateOrHour("HH", hour))
                .concat("h")
                .concat(new SimpleDateFormat("mm", Locale.FRANCE).format(formatDateOrHour("mm", minute)));
    }
}
