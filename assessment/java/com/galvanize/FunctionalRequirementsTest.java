package com.galvanize;

import com.galvanize.util.ClassProxy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Functional Requirements")
public class FunctionalRequirementsTest {
    private ByteArrayOutputStream outContent;
    private PrintStream original;

    private ClassProxy Application = null;
    
    @BeforeEach
    private void ensureApplication() {
        Application = ClassProxy.classNamed("com.galvanize.Application").ensureMainMethod();
    }

    @BeforeEach
    private void setUpStreams() {
        original = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    private void cleanUpStreams() {
        System.setOut(original);
    }

    @DisplayName("JSON Format")
    @ParameterizedTest(name = "with ''{0}''")
    @MethodSource("bookingProvider")
    public void assertMainPrintsFormattedJSONResponse(
            String bookingString,
            String roomType,
            String roomNumber,
            String startTime,
            String endTime
    ) {
        String expected = "{\n" +
                "  \"type\": \"" + roomType + "\",\n" +
                "  \"roomNumber\": " + roomNumber + ",\n" +
                "  \"startTime\": \"" + startTime + "\",\n" +
                "  \"endTime\": \"" + endTime + "\"\n" +
                "}";

        String[] args = {bookingString, "json"};
        Application.invoke("main", new Object[]{args});

        assertEquals(expected, outContent.toString().trim(), "JSON output does not match");
    }

    @DisplayName("HTML Format")
    @ParameterizedTest(name = "with ''{0}''")
    @MethodSource("bookingProvider")
    public void assertMainPrintsFormattedHTMLResponse(
            String bookingString,
            String roomType,
            String roomNumber,
            String startTime,
            String endTime
    ) {
        String expected = "<dl>\n" +
                "  <dt>Type</dt><dd>" + roomType + "</dd>\n" +
                "  <dt>Room Number</dt><dd>" + roomNumber + "</dd>\n" +
                "  <dt>Start Time</dt><dd>" + startTime + "</dd>\n" +
                "  <dt>End Time</dt><dd>" + endTime + "</dd>\n" +
                "</dl>";

        String[] args = {bookingString, "html"};
        Application.invoke("main", new Object[]{args});

        assertEquals(expected, outContent.toString().trim(), "HTML output does not match");
    }

    @DisplayName("CSV Format")
    @ParameterizedTest(name = "with ''{0}''")
    @MethodSource("bookingProvider")
    public void assertMainPrintsFormattedCSVResponse(
            String bookingString,
            String roomType,
            String roomNumber,
            String startTime,
            String endTime
    ) {
        String expected = "type,room number,start time,end time\n" +
                String.format("%s,%s,%s,%s", roomType, roomNumber, startTime, endTime);

        String[] args = {bookingString, "csv"};
        Application.invoke("main", new Object[]{args});

        assertEquals(expected, outContent.toString().trim(), "CSV output does not match");
    }

    static Stream<Arguments> bookingProvider() {
        return Stream.of(
                Arguments.of("c112-10:30am-2:00pm", "Classroom", "112", "10:30am", "2:00pm"),
                Arguments.of("s12-10:30pm-11:00pm", "Suite", "12", "10:30pm", "11:00pm"),
                Arguments.of("r4-10:32pm-11:03pm", "Conference Room", "4", "10:32pm", "11:03pm"),
                Arguments.of("c88888-1:32pm-2:03pm", "Classroom", "88888", "1:32pm", "2:03pm")
        );
    }
}