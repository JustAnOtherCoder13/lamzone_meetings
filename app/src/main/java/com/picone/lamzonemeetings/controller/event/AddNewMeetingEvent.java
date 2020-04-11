package com.picone.lamzonemeetings.controller.event;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public class AddNewMeetingEvent {

    public Meeting meeting;
    public List<Employee> participants;

    public AddNewMeetingEvent(Meeting meeting, List<Employee> participants){
        this.participants = participants;
        this.meeting = meeting; }
}

