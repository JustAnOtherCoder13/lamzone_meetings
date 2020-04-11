package com.picone.lamzonemeetings.controller.event;

import androidx.fragment.app.Fragment;

import com.picone.lamzonemeetings.model.Employee;

import java.util.List;

public class AddMeetingEvent {

    public Fragment fragment;

    public AddMeetingEvent(Fragment fragment){
        this.fragment = fragment;
    }
}
