package com.picone.lamzonemeetings.controller.event;

import com.picone.lamzonemeetings.model.Meeting;

public class CreateNewMeetingEvent {

    public Meeting meeting;

    public CreateNewMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}

