package com.galvanize.formatters;

import com.galvanize.Booking;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVFormatterTest {
    @Test
    public void CSVFormatterFormatTest() {
        Booking booking = new Booking(Booking.Type.CONFERENCE_ROOM, "4", "12:00pm", "02:00pm");
        Formatter formatter = new CSVFormatter();

        String expected = "{\n\t\"type\": \"Conference Room\",\n\t\"roomNumber\": 4," +
                "\n\t\"startTime\": \"12:00pm\",\n\t\"endTime\": \"02:00pm\"\n}";
        String actual = formatter.format(booking);

        assertEquals(expected, actual, "Test JSONFormatter format method");
    }
}
