package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Participant;
import com.picone.lamzonemeetings.model.Room;

import java.util.List;

public class DummyMeetingService implements ApiService {

    private List<Meeting> meetings = DummyApiServiceGenerator.generateMeetings();
    private List<Room> rooms = DummyApiServiceGenerator.generateRooms();
    private List<Participant> participants = DummyApiServiceGenerator.generateParticipant();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Room> getRooms() {return rooms;}

    @Override
    public List<Participant> getParticipants() { return participants; }

    @Override
    public void deleteMeeting(Meeting meeting) { meetings.remove(meeting); }

    @Override
    public void addMeeting(Meeting meeting) { meetings.add(meeting); }
}
