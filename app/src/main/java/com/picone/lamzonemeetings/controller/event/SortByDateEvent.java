package com.picone.lamzonemeetings.controller.event;

import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public class SortByDateEvent {
    public List<Meeting> meetings;

    public SortByDateEvent(List<Meeting> meetings){
        this.meetings = meetings;
    }
}

