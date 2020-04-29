package com.picone.lamzonemeetings.model;

import androidx.annotation.NonNull;

public class Room {
    private String roomName;

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    @NonNull
    @Override
    public String toString() {
        return roomName;
    }
}
