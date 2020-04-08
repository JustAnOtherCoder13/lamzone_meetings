package com.picone.lamzonemeetings.model;

public class Meeting {

    private String hour;
    private String place;
    private String subject;
    private String participants;
    private String date;

    public Meeting(String hour, String subject,String place, String participants, String date) {

        this.hour = hour;
        this.participants = participants;
        this.place = place;
        this.subject = subject;
        this.date = date;

    }

    public String getDate() {return date;}

    public void setDate(String date) { this.date = date;}

    public String getHour() { return hour;}

    public void setHour(String hour) { this.hour = hour; }

    public String getPlace() { return place; }

    public void setPlace(String place) { this.place = place;}

    public String getSubject() { return subject;}

    public void setSubject(String subject) { this.subject = subject; }

    public String getParticipants() { return participants; }

    public void setParticipants(String participants) { this.participants = participants;}
}

