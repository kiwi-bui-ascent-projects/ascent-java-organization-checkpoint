package com.galvanize.formatters;

import com.galvanize.Booking;

public class HTMLFormatter implements Formatter {
    @Override
    public String format(Booking booking) {
        return String.format("<dl>\n" +
                        "  <dt>Type</dt><dd>%s</dd>\n" +
                        "  <dt>Room Number</dt><dd>%s</dd>\n" +
                        "  <dt>Start Time</dt><dd>%s</dd>\n" +
                        "  <dt>End Time</dt><dd>%s</dd>\n" +
                        "</dl>",
                booking.getRoomType().toString(), booking.getRoomNumber(),
                booking.getStartTime(),booking.getEndTime());
    }
}
