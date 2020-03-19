package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyApiServiceGenerator {

    static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(15, "Réunion A", "Peach", "Participants :"),
            new Meeting(13, "Réunion B", "Mario", "Participants :"),
            new Meeting(10, "Réunion C", "Luigi", "Participants :"),
            new Meeting(11, "Réunion D", "Toad", "Participants :"),
            new Meeting(9, "Réunion E", "Wario", "Participants :"),
            new Meeting(17, "Réunion F", "Kong", "Participants :"),
            new Meeting(14, "Réunion G", "Browser", "Participants :"),
            new Meeting(16, "Réunion H", "Yoshi", "Participants :"),
            new Meeting(17, "Réunion I", "Waluigi", "Participants :"),
            new Meeting(10, "Réunion J", "Roi Boo", "Participants :")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

}
