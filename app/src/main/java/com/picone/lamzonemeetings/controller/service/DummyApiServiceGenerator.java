package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.OTHER_DAY;
import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.TODAY;

public abstract class DummyApiServiceGenerator {

    //Meetings list
    static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("15", "Réunion I", "Peach", new ArrayList<>(), TODAY),
            new Meeting("08", "Réunion G", "Mario", new ArrayList<>(), TODAY),
            new Meeting("10", "Réunion C", "Luigi", new ArrayList<>(), TODAY),
            new Meeting("11", "Réunion A", "Toad", new ArrayList<>(), TODAY),
            new Meeting("09","Réunion E", "Wario", new ArrayList<>(), TODAY),
            new Meeting("17", "Réunion F", "Kong", new ArrayList<>(), OTHER_DAY),
            new Meeting("14", "Réunion D", "Browser", new ArrayList<>(), OTHER_DAY),
            new Meeting("16", "Réunion H", "Yoshi", new ArrayList<>(), OTHER_DAY),
            new Meeting("17", "Réunion B", "Waluigi", new ArrayList<>(), OTHER_DAY),
            new Meeting("10", "Réunion J", "Roi Boo", new ArrayList<>(), OTHER_DAY)
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

    //Employees List
    static List<Employee> DUMMY_EMPLOYEES = Arrays.asList(
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

    public static List<Employee> generateEmployees(){return new ArrayList<>(DUMMY_EMPLOYEES);}

    //Participants List
    //static List<Employee> DUMMY_PARTICIPANTS = Arrays.asList();

    //public static List<Employee> generateParticipants(){return new ArrayList<>(DUMMY_PARTICIPANTS);}

}

