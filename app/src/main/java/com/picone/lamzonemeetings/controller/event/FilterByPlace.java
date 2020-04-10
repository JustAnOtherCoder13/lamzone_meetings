package com.picone.lamzonemeetings.controller.event;

import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public class FilterByPlace {
    public List<Meeting> meetings;

    public FilterByPlace(List<Meeting> meetings){
        this.meetings = meetings;
    }
}
