package com.galvanize;

public class Booking {
    public enum Type {
        NONE, CONFERENCE_ROOM, SUITE, AUDITORIUM, CLASSROOM;
    }

    private Type roomType;
    private String roomNumber;
    private String startTime;
    private String endTime;

    public Booking(Type roomType, String roomNumber, String startTime, String endTime) {
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Type getRoomType() {
        return roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Booking parse(String input) {

    }
}
