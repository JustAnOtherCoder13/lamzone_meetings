package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyApiServiceGenerator {

    //Meetings list
    static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("15", "Réunion I", "Peach", "",""),
            new Meeting("08", "Réunion G", "Mario", "",""),
            new Meeting("10", "Réunion C", "Luigi", "",""),
            new Meeting("11", "Réunion A", "Toad", "",""),
            new Meeting("09","Réunion E", "Wario", "",""),
            new Meeting("17", "Réunion F", "Kong", "",""),
            new Meeting("14", "Réunion D", "Browser", "",""),
            new Meeting("16", "Réunion H", "Yoshi", "",""),
            new Meeting("17", "Réunion B", "Waluigi", "",""),
            new Meeting("10", "Réunion J", "Roi Boo", "","")
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
