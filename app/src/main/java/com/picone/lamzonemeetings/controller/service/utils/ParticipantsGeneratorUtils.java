package com.picone.lamzonemeetings.controller.service.utils;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticipantsGeneratorUtils {


    private static List<Employee> generateParticipantsList(List<Employee> employees) {
        List<Employee> dummyParticipants = new ArrayList<>();
        int numberOfParticipants = new Random().nextInt(2) + 2;
        Employee participant;
        for (int i = 0; i < numberOfParticipants; i++) {
            int employeeIndex = new Random().nextInt(12);
            participant = employees.get(employeeIndex);
            if (!dummyParticipants.contains(participant)) dummyParticipants.add(participant);
            else i--;
        }
        return dummyParticipants;
    }

    public static void generateParticipants(List<Meeting> meetings, List<Employee> employees) {

        for (int i = 0; i < meetings.size(); i++) {
            if (i != 0) {
                meetings.get(i).setParticipants(generateParticipantsList(employees));
            } else {
                //for ui test
                List<Employee> participants = new ArrayList<>();
                for (int i2 = 0; i2 < 3; i2++) {
                    participants.add(employees.get(i2));
                }
                meetings.get(i).setParticipants(participants);
            }
        }
    }
}
