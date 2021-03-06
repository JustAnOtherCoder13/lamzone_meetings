package com.picone.lamzonemeetings;

import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.model.Town;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.picone.lamzonemeetings.controller.service.ApiServiceGenerator.DUMMY_EMPLOYEES;
import static com.picone.lamzonemeetings.controller.service.ApiServiceGenerator.DUMMY_MEETINGS;
import static com.picone.lamzonemeetings.controller.service.ApiServiceGenerator.DUMMY_ROOMS;
import static com.picone.lamzonemeetings.controller.service.ApiServiceGenerator.DUMMY_TOWN;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_DAY_OF_MONTH;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_MONTH;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_YEAR;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MeetingServiceUnitTest {

    private ApiService service;

    @Before
    public void setup() {
        service = DI.getMeetingApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DUMMY_MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void getRoomsWithSuccess() {
        List<Room> rooms = service.getRooms();
        List<Room> expectedRooms = DUMMY_ROOMS;
        assertThat(rooms, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedRooms.toArray()));
    }

    @Test
    public void getEmployeeWithSuccess() {
        List<Employee> employees = service.getEmployees();
        List<Employee> expectedEmployees = DUMMY_EMPLOYEES;
        assertThat(employees, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedEmployees.toArray()));
    }

    @Test
    public void getTownWithSuccess() {
        List<Town> towns = service.getTowns();
        List<Town> expectedTowns = DUMMY_TOWN;
        assertThat(towns, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedTowns.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting meetingToAdd = new Meeting("", "subject", "Mario", new ArrayList<>(), new Date());
        service.addMeeting(meetingToAdd);
        assertTrue(service.getMeetings().contains(meetingToAdd));
    }
}