package com.galvanize.formatters;

import com.galvanize.Booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTMLFormatterTest {
    @Test
    public void HTMLFormatterFormatTest() {
        Booking booking = new Booking(Booking.Type.CONFERENCE_ROOM, "4", "12:00pm", "02:00pm");
        Formatter formatter = new HTMLFormatter();

        String expected = "<dl>\n" +
                "\t<dt>Type</dt><dd>Conference Room</dd>\n" +
                "\t<dt>Room Number</dt><dd>4</dd>\n" +
                "\t<dt>Start Time</dt><dd>12:00pm</dd>\n" +
                "\t<dt>End Time</dt><dd>02:00pm</dd>\n" +
                "</dl>";
        String actual = formatter.format(booking);

        assertEquals(expected, actual, "Test HTMLFormatter format method");
    }
}
