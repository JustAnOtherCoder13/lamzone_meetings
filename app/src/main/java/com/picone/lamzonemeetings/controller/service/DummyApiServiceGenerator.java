package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.TODAY;
import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.generateRandomDate;

public abstract class DummyApiServiceGenerator {
    //Meetings list
    static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("", "Réunion I", "Peach", new ArrayList<>(), generateRandomDate()),
            new Meeting("", "Réunion G", "Mario", new ArrayList<>(), generateRandomDate()),
            new Meeting("", "Réunion C", "Luigi", new ArrayList<>(), generateRandomDate()),
            new Meeting("", "Réunion A", "Toad", new ArrayList<>(), generateRandomDate()),
            new Meeting("","Réunion E", "Wario", new ArrayList<>(), generateRandomDate()),
            new Meeting("", "Réunion F", "Kong", new ArrayList<>(), generateRandomDate()),
            new Meeting("", "Réunion D", "Browser", new ArrayList<>(), generateRandomDate()),
            new Meeting("", "Réunion H", "Yoshi", new ArrayList<>(), generateRandomDate()),
            new Meeting("", "Réunion B", "Waluigi", new ArrayList<>(), generateRandomDate()),
            new Meeting("", "Réunion J", "Roi Boo", new ArrayList<>(), generateRandomDate())
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

}

