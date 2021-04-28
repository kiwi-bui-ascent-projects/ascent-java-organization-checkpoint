package com.galvanize;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.galvanize.Booking;

public class BookingTest {
    @Test
    public void testBookingTestGetters() {
        Booking actual = new Booking(Booking.Type.CONFERENCE_ROOM, "4", "12:00pm", "02:00pm");

        assertEquals(Booking.Type.CONFERENCE_ROOM, actual.getRoomType(), "Test Booking getRoomType method");
        assertEquals("4", actual.getRoomNumber(), "Test Booking getRoomNumber method");
        assertEquals("12:00pm", actual.getStartTime(), "Test Booking getStartTime method");
        assertEquals("02:00pm", actual.getEndTime(), "Test Booking getEndTime method");
    }

    @Test
    public void testBookingParseMethod() {
        Booking actual = Booking.parse("r4-12:00pm-02:00pm");

        assertEquals(Booking.Type.CONFERENCE_ROOM, actual.getRoomType(),
                "Test Booking parse method roomType value");
        assertEquals("4", actual.getRoomNumber(),
                "Test Booking parse method roomNumber value");
        assertEquals("12:00pm", actual.getStartTime(),
                "Test Booking parse method startTime value");
        assertEquals("02:00pm", actual.getEndTime(),
                "Test Booking parse method endTime value");
    }
}
