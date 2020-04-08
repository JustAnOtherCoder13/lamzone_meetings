package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Participant;
import com.picone.lamzonemeetings.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyApiServiceGenerator {

    //Meetings list
    static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("15", "Réunion I", "Peach", "Participants :",""),
            new Meeting("08", "Réunion G", "Mario", "Participants :",""),
            new Meeting("10", "Réunion C", "Luigi", "Participants :",""),
            new Meeting("11", "Réunion A", "Toad", "Participants :",""),
            new Meeting("09","Réunion E", "Wario", "Participants :",""),
            new Meeting("17", "Réunion F", "Kong", "Participants :",""),
            new Meeting("14", "Réunion D", "Browser", "Participants :",""),
            new Meeting("16", "Réunion H", "Yoshi", "Participants :",""),
            new Meeting("17", "Réunion B", "Waluigi", "Participants :",""),
            new Meeting("10", "Réunion J", "Roi Boo", "Participants :","")
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
    static List<Participant> DUMMY_PARTICIPANTS = Arrays.asList(
            new Participant(null),
            new Participant("Maxime"),
            new Participant("Alex"),
            new Participant("Paul"),
            new Participant("Viviane"),
            new Participant("Helene"),
            new Participant("Adrien"),
            new Participant("Charlotte"),
            new Participant("Lauren"),
            new Participant("Romane"),
            new Participant("Lino"),
            new Participant("Margaux"),
            new Participant("Roméo")
    );

    public static List<Participant> generateParticipant(){return new ArrayList<>(DUMMY_PARTICIPANTS);}
}
