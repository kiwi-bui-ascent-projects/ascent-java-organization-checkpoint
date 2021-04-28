package com.galvanize;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.galvanize.Booking;

public class BookingTest {
    @Test
    public void testBookingTestGetters() {
        Booking actual = new Booking(Booking.Type.CONFERENCE_ROOM, "4", "12:00pm", "02:00pm");

        assertEquals(Booking.Type.CONFERENCE_ROOM, actual.getRoomType());
        assertEquals("4", actual.getRoomNumber());
        assertEquals("12:00pm", actual.getStartTime());
        assertEquals("02:00pm", actual.getEndTime());
    }

    @Test
    public void testBookingParseMethod() {
        Booking actual = Booking.parse("r4-12:00pm-02:00pm");

        assertEquals(Booking.Type.CONFERENCE_ROOM, actual.getRoomType());
        assertEquals("4", actual.getRoomNumber());
        assertEquals("12:00pm", actual.getStartTime());
        assertEquals("02:00pm", actual.getEndTime());
    }
}
