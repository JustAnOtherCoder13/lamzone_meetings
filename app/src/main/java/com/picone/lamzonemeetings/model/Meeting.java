package com.picone.lamzonemeetings.model;

public class Meeting {

    private int hour;
    private String place;
    private String subject;
    private String participants;

    public Meeting(int hour, String subject,String place, String participants) {

        this.hour = hour;
        this.participants = participants;
        this.place = place;
        this.subject = subject;

    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) { this.hour = hour; }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}

