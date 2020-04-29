package com.picone.lamzonemeetings.controller.event;

import com.picone.lamzonemeetings.model.Meeting;

public class DeleteMeetingEvent {

    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
