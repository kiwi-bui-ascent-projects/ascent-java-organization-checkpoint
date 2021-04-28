package com.galvanize.formatters;

import com.galvanize.Booking;

public class CSVFormatter implements Formatter {
    @Override
    public String format(Booking booking) {
        return String.format("type,room number,start time,end time\n" +
                        "%s,%s,%s,%s",
                booking.getRoomType().getValue(), booking.getRoomNumber(),
                booking.getStartTime(),booking.getEndTime());
    }
}
