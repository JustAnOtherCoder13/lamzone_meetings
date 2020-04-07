package com.picone.lamzonemeetings.controller.event;

import androidx.fragment.app.Fragment;

public class AddMeetingEvent {

    public Fragment fragment;

    public AddMeetingEvent(Fragment fragment){
        this.fragment = fragment;
    }
}
