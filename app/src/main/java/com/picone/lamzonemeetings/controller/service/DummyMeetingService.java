package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public class DummyMeetingService implements ApiService {

    private List<Meeting> meetings = DummyApiServiceGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) { meetings.remove(meeting); }

    @Override
    public void addMeeting(Meeting meeting) { meetings.add(meeting); }
}
