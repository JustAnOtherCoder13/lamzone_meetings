package com.picone.lamzonemeetings.model;

import androidx.annotation.NonNull;

public class Town {

    private String townName;

    public Town(String townName) { this.townName = townName; }

    @NonNull
    @Override
    public String toString() {
        return townName;
    }
}
