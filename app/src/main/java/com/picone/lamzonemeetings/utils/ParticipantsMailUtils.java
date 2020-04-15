package com.picone.lamzonemeetings.utils;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public class ParticipantsMailUtils {

    public static String getParticipantsMail(Meeting meeting){
        List<Employee> participants = meeting.getParticipants();
        String participantsMail = null;
        for (Employee participant:participants) {
            if (participantsMail == null) participantsMail = participant.getMail();
            else participantsMail = participantsMail.concat(", ").concat(participant.getMail());
        }
        return participantsMail;
    }
}
