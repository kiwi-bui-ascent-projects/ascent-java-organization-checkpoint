package com.galvanize.formatters;

import com.galvanize.Booking;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONFormatterTest {
    @Test
    public void JSONFormatterFormatTest() {
        Booking booking = new Booking(Booking.Type.CONFERENCE_ROOM, "4", "12:00pm", "02:00pm");
        Formatter formatter = new JSONFormatter();

        String expected = "{\n" +
                "  \"type\": \"Conference Room\",\n" +
                "  \"roomNumber\": 4,\n" +
                "  \"startTime\": \"12:00pm\",\n" +
                "  \"endTime\": \"02:00pm\"\n" +
                "}";
        String actual = formatter.format(booking);

        assertEquals(expected, actual, "Test JSONFormatter format method");
    }
}
