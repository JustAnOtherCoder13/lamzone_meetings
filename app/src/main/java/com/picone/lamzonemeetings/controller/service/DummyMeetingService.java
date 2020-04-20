package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.model.Town;

import java.util.List;

import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.generateDummyDate;
import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.generateDummyHour;
import static com.picone.lamzonemeetings.controller.service.utils.DummyParticipantsGeneratorUtils.generateDummyParticipants;

public class DummyMeetingService implements ApiService {

    private List<Meeting> meetings = DummyApiServiceGenerator.generateMeetings();
    private List<Room> rooms = DummyApiServiceGenerator.generateRooms();
    private List<Employee> employees = DummyApiServiceGenerator.generateEmployees();
    private List<Town> towns = DummyApiServiceGenerator.generateTown();
    private List<Employee> participants = DummyApiServiceGenerator.generateParticipants();

    @Override
    public List<Meeting> getMeetings() { return meetings; }

    @Override
    public List<Room> getRooms() { return rooms; }

    @Override
    public List<Employee> getEmployees() { return employees; }

    @Override
    public List<Town> getTown() { return towns;}

    @Override
    public void getParticipants() { generateDummyParticipants(meetings,employees); }

    @Override
    public void getHour() { generateDummyHour(meetings); }

    @Override
    public void getDate() { generateDummyDate(meetings); }

    @Override
    public void deleteMeeting(Meeting meeting) { meetings.remove(meeting); }

    @Override
    public void addMeeting(Meeting meeting) { meetings.add(meeting); }
}
