package com.picone.lamzonemeetings.controller;

import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public interface ApiService {
    /**
     * Get all Meeting
     * @return {@link List}
     */
    List<Meeting> getMeetings();
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
