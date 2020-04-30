package com.picone.lamzonemeetings.utils;

import com.picone.lamzonemeetings.model.Employee;

import java.util.List;

public class ParticipantsMailUtils {
    //participant meeting
    public static String getParticipantsMail(List<Employee> participants) {
        String participantsMail = null;
        for (Employee participant : participants) {
            if (participantsMail == null) participantsMail = participant.getMail();
            else participantsMail = participantsMail.concat(", ").concat(participant.getMail());
        }
        return participantsMail;
    }
}
