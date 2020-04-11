package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Room;

import java.util.List;

public class DummyMeetingService implements ApiService {

    private List<Meeting> meetings = DummyApiServiceGenerator.generateMeetings();
    private List<Room> rooms = DummyApiServiceGenerator.generateRooms();
    private List<Employee> employees = DummyApiServiceGenerator.generateEmployees();
    private List<Employee> participants = DummyApiServiceGenerator.generateParticipants();
    private String participantsMail;

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Room> getRooms() {return rooms;}

    @Override
    public List<Employee> getEmployees() { return employees; }

    @Override
    public String getParticipants() {
        String participant = null;
        for (Employee employee:participants){
            participantsMail = employee.getMail();
            if (participant == null) participant = participantsMail;
            else participant = participant.concat(", ").concat(participantsMail);
            participantsMail = participant;
        }
        return participantsMail;
    }

    @Override
    public void deleteMeeting(Meeting meeting) { meetings.remove(meeting); }

    @Override
    public void addMeeting(Meeting meeting) { meetings.add(meeting); }
}
