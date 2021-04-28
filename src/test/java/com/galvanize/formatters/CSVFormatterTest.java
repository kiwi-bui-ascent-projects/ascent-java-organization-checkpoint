package com.galvanize.formatters;

import com.galvanize.Booking;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVFormatterTest {
    @Test
    public void CSVFormatterFormatTest() {
        Booking booking = new Booking(Booking.Type.CONFERENCE_ROOM, "4", "12:00pm", "02:00pm");
        Formatter formatter = new CSVFormatter();

        String expected = "type,room number,start time,end time\n" +
                        "Conference Room,4,12:00pm,02:00pm";
        String actual = formatter.format(booking);

        assertEquals(expected, actual, "Test CSVFormatter format method");
    }
}
