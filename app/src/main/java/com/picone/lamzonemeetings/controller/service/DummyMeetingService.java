package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.model.Town;

import java.util.List;

public class DummyMeetingService implements ApiService {

    private List<Meeting> meetings = DummyApiServiceGenerator.generateMeetings();
    private List<Room> rooms = DummyApiServiceGenerator.generateRooms();
    private List<Employee> employees = DummyApiServiceGenerator.generateEmployees();
    private List<Town> towns = DummyApiServiceGenerator.generateTown();

    @Override
    public List<Meeting> getMeetings() { return meetings;}

    @Override
    public List<Room> getRooms() { return rooms;}

    @Override
    public List<Employee> getEmployees() { return employees;}

    @Override
    public List<Town> getTowns() { return towns; }

    @Override
    public void deleteMeeting(Meeting meeting) { meetings.remove(meeting); }

    @Override
    public void addMeeting(Meeting meeting) { meetings.add(meeting);}
}
