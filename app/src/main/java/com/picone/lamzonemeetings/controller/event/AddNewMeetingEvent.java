package com.picone.lamzonemeetings.controller.event;

import com.picone.lamzonemeetings.model.Meeting;

public class AddNewMeetingEvent {

    public Meeting meeting;
    public AddNewMeetingEvent(Meeting meeting){ this.meeting = meeting; }
}

