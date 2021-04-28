package com.galvanize;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    PrintStream original;
    ByteArrayOutputStream outContent;

    // This block captures everything written to System.out
    @BeforeEach
    public void setOut() {
        original = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    // This block resets System.out to whatever it was before
    @AfterEach
    public void restoreOut() {
        System.setOut(original);
    }

    @Test
    public void JSONTest() {
        String expected = "{\n\t\"type\": \"Conference Room\",\n\t\"roomNumber\": 4," +
                "\n\t\"startTime\": \"12:00pm\",\n\t\"endTime\": \"02:00pm\"\n}";

        Application.main(new String[]{ "r4-12:00pm-02:00pm", "JSON" });

        String actual = outContent.toString().trim();

        assertEquals(expected, actual, "Testing JSON output");
    }

    @Test
    public void CSVTest() {
        String expected = "type,room number,start time,end time\n" +
                "Conference Room,4,12:00pm,02:00pm";

        Application.main(new String[]{ "r4-12:00pm-02:00pm", "csv" });

        String actual = outContent.toString().trim();

        assertEquals(expected, actual, "Testing JSON output");
    }

    @Test
    public void HTMLTest() {
        String expected = "<dl>\n" +
                "\t<dt>Type</dt><dd>Conference Room</dd>\n" +
                "\t<dt>Room Number</dt><dd>4</dd>\n" +
                "\t<dt>Start Time</dt><dd>12:00pm</dd>\n" +
                "\t<dt>End Time</dt><dd>02:00pm</dd>\n" +
                "</dl>";

        Application.main(new String[]{ "r4-12:00pm-02:00pm", "html" });

        String actual = outContent.toString().trim();

        assertEquals(expected, actual, "Testing JSON output");
    }

}