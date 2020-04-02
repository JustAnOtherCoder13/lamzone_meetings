package com.picone.lamzonemeetings.controller.event;

import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public class SortByPlaceEvent {
    public List<Meeting> meetings;

    public SortByPlaceEvent(List<Meeting> meetings){
        this.meetings = meetings;
    }
}
