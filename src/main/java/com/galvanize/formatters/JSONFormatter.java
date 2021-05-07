package com.galvanize.formatters;

import com.galvanize.Booking;

public class JSONFormatter implements Formatter {
    @Override
    public String format(Booking booking) {
        return String.format("{\n" +
                        "  \"type\": \"%s\",\n" +
                        "  \"roomNumber\": %s,\n" +
                        "  \"startTime\": \"%s\",\n" +
                        "  \"endTime\": \"%s\"\n" +
                        "}",
                booking.getRoomType().toString(), booking.getRoomNumber(),
                booking.getStartTime(),booking.getEndTime());
    }
}
