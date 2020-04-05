package com.picone.lamzonemeetings.model;

import androidx.annotation.NonNull;

public class Participant {
    private String name;

    public Participant(String name){this.name = name;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
