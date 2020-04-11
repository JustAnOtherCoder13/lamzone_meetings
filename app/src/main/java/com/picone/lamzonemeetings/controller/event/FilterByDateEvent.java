package com.picone.lamzonemeetings.controller.event;

import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public class FilterByDateEvent {
    public List<Meeting> meetings;

    public FilterByDateEvent(List<Meeting> meetings){
        this.meetings = meetings;
    }
}

