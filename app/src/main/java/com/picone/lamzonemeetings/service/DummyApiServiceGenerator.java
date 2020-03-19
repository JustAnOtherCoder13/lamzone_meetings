package com.picone.lamzonemeetings.service;

import com.picone.lamzonemeetings.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyApiServiceGenerator {

    static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(15, "Salle :", "Sujet de la reunion", "Participants :"),
            new Meeting(17, "Salle :", "Sujet de la reunion", "Participants :"),
            new Meeting(10, "Salle :", "Sujet de la reunion", "Participants :"),
            new Meeting(11, "Salle :", "Sujet de la reunion", "Participants :")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

}
