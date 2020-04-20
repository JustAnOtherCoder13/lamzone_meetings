package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.model.Town;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



public abstract class DummyApiServiceGenerator {
    //Meetings list
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("", "Réunion I", "Peach", new ArrayList<>(), new Date()),
            new Meeting("", "Réunion G", "Mario", new ArrayList<>(), new Date()),
            new Meeting("", "Réunion C", "Luigi", new ArrayList<>(), new Date()),
            new Meeting("", "Réunion A", "Toad", new ArrayList<>(), new Date()),
            new Meeting("","Réunion E", "Wario", new ArrayList<>(), new Date()),
            new Meeting("", "Réunion F", "Kong", new ArrayList<>(), new Date()),
            new Meeting("", "Réunion D", "Browser", new ArrayList<>(), new Date()),
            new Meeting("", "Réunion H", "Yoshi", new ArrayList<>(), new Date()),
            new Meeting("", "Réunion B", "Waluigi", new ArrayList<>(), new Date()),
            new Meeting("", "Réunion J", "Roi Boo", new ArrayList<>(), new Date())
    );

    public static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    //Rooms list
    public static List<Room> DUMMY_ROOMS = Arrays.asList(
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
    public static List<Employee> DUMMY_EMPLOYEES = Arrays.asList(
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

    public static List<Town> DUMMY_TOWN = Arrays.asList(
           new Town("Marseille"),
            new Town("Paris"),
            new Town("London"),
            new Town("Rome"),
            new Town("Madrid")
    );

    public static List<Town> generateTown(){return new ArrayList<>(DUMMY_TOWN);}

    public static Meeting DUMMY_MEETING_TO_ADD = new Meeting("","subject","Mario",new ArrayList<>(),new Date());

    public static List<Employee> DUMMY_PARTICIPANTS = Arrays.asList(
            new Employee("Roméo"),
            new Employee("Charlotte"),
            new Employee("Lino")
    );

    public static List<Employee> generateParticipants(){return new ArrayList<>(DUMMY_PARTICIPANTS);}


}

