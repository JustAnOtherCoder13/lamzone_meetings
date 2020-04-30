package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.model.Town;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingService implements ApiService {

    private List<Meeting> meetings = ApiServiceGenerator.generateMeetings();
    private List<Meeting> originalsMeetings = new ArrayList<>(meetings);
    private List<Room> rooms = ApiServiceGenerator.generateRooms();
    private List<Employee> employees = ApiServiceGenerator.generateEmployees();
    private List<Meeting> filteredMeetings = new ArrayList<>();
    private List<Town> towns = ApiServiceGenerator.generateTown();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public List<Town> getTowns() {
        return towns;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void getFilteredMeetingsByDate(Date date) {
        filteredMeetings.clear();
        for (Meeting meeting : meetings) {
            if (meeting.getDate().compareTo(date) == 0) {
                filteredMeetings.add(meeting);
            }
        }
        initMeetings(filteredMeetings);
    }

    @Override
    public void getFilteredMeetingsByPlace(String placeToCompare) {
        filteredMeetings.clear();
        for (Meeting meeting : originalsMeetings) {
            if (meeting.getPlace().equals(placeToCompare)) {
                filteredMeetings.add(meeting);
            }
        }
        initMeetings(filteredMeetings);
    }

    @Override
    public void cancelFilter() {
        meetings.clear();
        meetings.addAll(originalsMeetings);
    }

    private void initMeetings(List<Meeting> meetingsList) {
        originalsMeetings.clear();
        originalsMeetings.addAll(meetings);
        meetings.clear();
        meetings.addAll(meetingsList);
    }
}