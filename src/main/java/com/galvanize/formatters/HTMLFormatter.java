package com.galvanize.formatters;

import com.galvanize.Booking;

public class HTMLFormatter implements Formatter {
    @Override
    public String format(Booking booking) {
        return String.format("<dl>\n" +
                        "\t<dt>Type</dt><dd>%s</dd>\n" +
                        "\t<dt>Room Number</dt><dd>%s</dd>\n" +
                        "\t<dt>Start Time</dt><dd>%s</dd>\n" +
                        "\t<dt>End Time</dt><dd>%s</dd>\n" +
                        "</dl>",
                booking.getRoomType().getValue(), booking.getRoomNumber(),
                booking.getStartTime(),booking.getEndTime());
    }
}
