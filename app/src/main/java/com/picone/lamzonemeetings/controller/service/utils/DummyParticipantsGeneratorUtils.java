package com.picone.lamzonemeetings.controller.service.utils;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyParticipantsGeneratorUtils {

    private static List<Employee> generateDummyParticipantsList(List<Employee> employees){
        List<Employee> dummyParticipants = new ArrayList<>();
        int numberOfParticipants = new Random().nextInt(2)+2;
        for (int i = 0; i < numberOfParticipants; i++) {
            int employeeIndex = new Random().nextInt(12);
            Employee participant = employees.get(employeeIndex);
            if (!dummyParticipants.contains(participant)) dummyParticipants.add(participant);
            else i--;
        }
        return dummyParticipants;
    }

    public static void generateDummyParticipants(List<Meeting> meetings, List<Employee> employees){
        for (int i =1; i<meetings.size();i++) {
            List<Employee> participants = generateDummyParticipantsList(employees);
            meetings.get(i).setParticipants(participants);
        }
    }
}
