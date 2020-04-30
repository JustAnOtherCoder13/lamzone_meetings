package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.model.Town;

import java.util.Date;
import java.util.List;

public interface ApiService {

    List<Meeting> getMeetings();

    List<Room> getRooms();

    List<Employee> getEmployees();

    List<Town> getTowns();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);
}
