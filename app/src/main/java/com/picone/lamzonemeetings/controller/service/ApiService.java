package com.picone.lamzonemeetings.controller.service;

import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.model.Town;

import java.util.List;

public interface ApiService {
    /**
     * Get all Meeting
     * @return {@link List}
     */
    List<Meeting> getMeetings();
    List<Room> getRooms();
    List<Employee> getEmployees();
    List<Town> getTowns();
    /**
     * Deletes a meeting
     * @param meeting
     */
    void deleteMeeting (Meeting meeting);
    /**
     * Add a meeting
     * @param meeting
     */
    void addMeeting (Meeting meeting);
}
