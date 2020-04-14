package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class DummyApiServiceGenerator {
    private final static Calendar CALENDAR = Calendar.getInstance();
    private final static int DAY_OF_MONTH = CALENDAR.get(Calendar.DAY_OF_MONTH);
    private final static int MONTH = CALENDAR.get(Calendar.MONTH);
    private final static int YEAR = CALENDAR.get(Calendar.YEAR);
    private static int DAY_OF_MONTH2 = CALENDAR.get(Calendar.DAY_OF_MONTH)-1;

    static int getOtherDay(){
        if (DAY_OF_MONTH2==0){ DAY_OF_MONTH2 = CALENDAR.get(Calendar.DAY_OF_MONTH)+1;}
        return DAY_OF_MONTH2;
    }

    private static Date TODAY;
    private static Date OTHER_DAY;

    static {
        try {
            TODAY = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE).parse(DAY_OF_MONTH + "/" + MONTH + "/" + YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            OTHER_DAY = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE).parse(getOtherDay() + "/" + MONTH + "/" + YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    //Meetings list
    static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("15", "Réunion I", "Peach", "", TODAY),
            new Meeting("08", "Réunion G", "Mario", "", TODAY),
            new Meeting("10", "Réunion C", "Luigi", "", TODAY),
            new Meeting("11", "Réunion A", "Toad", "", TODAY),
            new Meeting("09","Réunion E", "Wario", "", TODAY),
            new Meeting("17", "Réunion F", "Kong", "", OTHER_DAY),
            new Meeting("14", "Réunion D", "Browser", "", OTHER_DAY),
            new Meeting("16", "Réunion H", "Yoshi", "", OTHER_DAY),
            new Meeting("17", "Réunion B", "Waluigi", "", OTHER_DAY),
            new Meeting("10", "Réunion J", "Roi Boo", "", OTHER_DAY)
    );

    public static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    //Rooms list
    static List<Room> DUMMY_ROOMS = Arrays.asList(
            new Room(null),
            new Room("Peach"),
            new Room("Mario"),
            new Room("Luigi"),
            new Room("Toad"),
            new Room("Wario"),
            new Room("Kong"),
            new Room("Browser"),
            new Room("Yoshi"),
            new Room("Waluigi"),
            new Room("Roi Boo")
    );

    public static List<Room> generateRooms() {return new ArrayList<>(DUMMY_ROOMS);}

    //Participants List
    static List<Employee> dummyEmployees = Arrays.asList(
            new Employee("Maxime"),
            new Employee("Alex"),
            new Employee("Paul"),
            new Employee("Viviane"),
            new Employee("Helene"),
            new Employee("Adrien"),
            new Employee("Charlotte"),
            new Employee("Lauren"),
            new Employee("Romane"),
            new Employee("Lino"),
            new Employee("Margaux"),
            new Employee("Roméo")
    );

    public static List<Employee> generateEmployees(){return new ArrayList<>(dummyEmployees);}

}
