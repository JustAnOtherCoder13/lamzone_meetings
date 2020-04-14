package com.picone.lamzonemeetings.controller.event;

import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public class FilterByPlaceEvent {
    public List<Meeting> meetings;

    public FilterByPlaceEvent(List<Meeting> meetings){
        this.meetings = meetings;
    }
}
