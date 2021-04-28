package com.galvanize.formatters;

import com.galvanize.Booking;

public class CSVFormatter implements Formatter {
    @Override
    public String format(Booking booking) {
        return String.format("{\n\t\"type\": \"%s\",\n\t\"roomNumber\": %s," +
                        "\n\t\"startTime\": \"%s\",\n\t\"endTime\": \"%s\"\n}",
                booking.getRoomType().getValue(), booking.getRoomNumber(),
                booking.getStartTime(),booking.getEndTime());
    }
}
