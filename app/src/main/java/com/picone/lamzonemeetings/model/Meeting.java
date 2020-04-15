package com.picone.lamzonemeetings.model;

import java.util.Date;
import java.util.List;

public class Meeting {

    private String hour;
    private String place;
    private String subject;
    private List<Employee> participants;
    private Date date;

    public Meeting(String hour, String subject,String place, List<Employee> participants, Date date) {

        this.hour = hour;
        this.participants = participants;
        this.place = place;
        this.subject = subject;
        this.date = date;

    }

    public Date getDate() {return date;}

    public void setDate(Date date) { this.date = date;}

    public String getHour() { return hour;}

    public void setHour(String hour) { this.hour = hour; }

    public String getPlace() { return place; }

    public void setPlace(String place) { this.place = place;}

    public String getSubject() { return subject;}

    public void setSubject(String subject) { this.subject = subject; }

    public List<Employee> getParticipants() { return participants; }

    public void setParticipants(List<Employee> participants) { this.participants = participants;}
}

