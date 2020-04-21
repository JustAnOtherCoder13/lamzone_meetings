package com.picone.lamzonemeetings.model;

import androidx.annotation.NonNull;

public class Employee {
    private String name;
    public Employee(String name){this.name = name;}

    public String getName() { return name;}

    public String getMail() {return name.toLowerCase().concat("@lamzone.com");}

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
