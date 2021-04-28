package com.galvanize;

public class Booking {
    public enum Type {
        CONFERENCE_ROOM("Conference Room"),
        SUITE("Suite"),
        AUDITORIUM("Auditorium"),
        CLASSROOM("Classroom");

        private String type;

        private Type(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return this.type;
        }
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

    public static Booking parse(String input) {
        String[] bookingInfo = input.split("-");
        char roomTypeInput = bookingInfo[0].charAt(0);
        Type roomType = Type.CONFERENCE_ROOM;

        if (roomTypeInput == 'r') {
            roomType = Type.CONFERENCE_ROOM;
        } else if (roomTypeInput == 's') {
            roomType = Type.SUITE;
        } else if (roomTypeInput == 'a') {
            roomType = Type.AUDITORIUM;
        } else if (roomTypeInput == 'c') {
            roomType = Type.CLASSROOM;
        }

        return new Booking(roomType, bookingInfo[0].substring(1), bookingInfo[1], bookingInfo[2]);
    }
}
