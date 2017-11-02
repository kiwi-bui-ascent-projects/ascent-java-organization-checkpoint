package com.galvanize;

import com.galvanize.util.ClassProxy;
import com.galvanize.util.InstanceProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static com.galvanize.util.ClassProxy.classNamed;
import static com.galvanize.util.ClassProxy.interfaceNamed;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Technical Requirements")
public class TechnicalRequirements {

    @Test
    @DisplayName("Are met")
    public void applicationMainPassesAllTests() {
        ClassProxy Booking = classNamed("com.galvanize.Booking");

        Class enumClass = getEnumClass(Booking.getDelegate());
        if (enumClass == null) fail("Your Booking class must contain a nested enum.");

        Booking
                .ensureConstructor(enumClass, String.class, String.class, String.class)
                .ensureGetter("roomType", enumClass)
                .ensureGetter("roomNumber", String.class)
                .ensureGetter("startTime", String.class)
                .ensureGetter("endTime", String.class)
                .ensureMethod(builder -> builder.named("parse")
                        .isStatic()
                        .returns(builder.getDeclaringClass())
                        .withParameters(String.class)
                );

        ClassProxy Formatter = interfaceNamed("com.galvanize.formatters.Formatter")
                .ensureMethod(method -> method
                        .named("format")
                        .returns(String.class)
                        .withParameters(Booking));

        Booking.ensureMethod(method -> method
                .named("getFormattedBooking")
                .returns(String.class)
                .withParameters(Formatter));

        ClassProxy Application = classNamed("com.galvanize.Application")
                .ensureMethod(builder -> builder.named("getFormatter")
                        .withParameters(String.class)
                        .returns(Formatter)
                        .isStatic()
                );

        ClassProxy JSONFormatter = classNamed("com.galvanize.formatters.JSONFormatter")
                .ensureImplements(Formatter);

        ClassProxy HTMLFormatter = classNamed("com.galvanize.formatters.HTMLFormatter")
                .ensureImplements(Formatter);

        ClassProxy CSVFormatter = classNamed("com.galvanize.formatters.CSVFormatter")
                .ensureImplements(Formatter);

        List<Object> enumConstants = Arrays.asList(enumClass.getEnumConstants());
        if (enumConstants.isEmpty()) {
            fail(String.format(
                    "Expected %s to have 4 constants (1 for each room type) but found none",
                    enumClass.getSimpleName()
            ));
        } else if (enumConstants.size() != 4) {
            fail(String.format(
                    "Expected %s to have 4 constants (1 for each room type) but only found:\n\n%s\n",
                    enumClass.getSimpleName(),
                    enumConstants.stream().map(Object::toString).collect(joining("\n"))
            ));
        }

        Object roomType = enumConstants.get(0);
        String roomNumber = "12";
        String startTime = "8:32am";
        String endTime = "9:45am";

        final InstanceProxy booking = Booking.newInstance(roomType, roomNumber, startTime, endTime);

        validateBookingValues(booking, enumConstants, roomNumber, startTime, endTime);

        String stringToParse = "c112-10:30am-2:00pm";
        Object result = Booking.invoke("parse", stringToParse);
        if (result == null) {
            fail("Expected Booking.parse to return an instance of Booking, but it returned null.");
        }
        validateParsedBookingValues(
                InstanceProxy.wrap(result, Booking),
                stringToParse,
                enumClass,
                enumConstants,
                "112",
                "10:30am",
                "2:00pm");

        // -----------------------------------

        final InstanceProxy jsonFormatter = JSONFormatter.newInstance();
        String jsonResult = callWithoutPrinting(jsonFormatter, "format", booking.getDelegate());

        String expected = "{\n" +
                "  \"type\": \"" + roomType + "\",\n" +
                "  \"roomNumber\": " + roomNumber + ",\n" +
                "  \"start Time\": \"" + startTime + "\",\n" +
                "  \"endTime\": \"" + endTime + "\"\n" +
                "}";

        assertEquals(
                expected,
                jsonResult,
                "Expected the JSONFormatter to return the correctly formatted data"
        );

        final InstanceProxy htmlFormatter = HTMLFormatter.newInstance();
        String htmlResult = callWithoutPrinting(htmlFormatter, "format", booking.getDelegate());

        String expectedHTML = "<dl>\n" +
                "  <dt>Type</dt><dd>" + roomType + "</dd>\n" +
                "  <dt>Room Number</dt><dd>" + roomNumber + "</dd>\n" +
                "  <dt>Start Time</dt><dd>" + startTime + "</dd>\n" +
                "  <dt>End Time</dt><dd>" + endTime + "</dd>\n" +
                "</dl>";

        assertEquals(
                expectedHTML,
                htmlResult,
                "Expected the HTMLFormatter to return the correctly formatted data"
        );

        final InstanceProxy csvFormatter = CSVFormatter.newInstance();
        String csvResult = callWithoutPrinting(csvFormatter, "format", booking.getDelegate());

        String expectedCSV = "type,room number,start time,end time\n" +
                String.format("%s,%s,%s,%s", roomType, roomNumber, startTime, endTime);

        assertEquals(
                expectedCSV,
                csvResult,
                "Expected the CSVFormatter to return the correctly formatted data"
        );

        assertThatGetFormatterReturnsAJSONFormatter(Application, JSONFormatter);
        assertThatGetFormatterReturnsAHTMLFormatter(Application, HTMLFormatter);
        assertThatGetFormatterReturnsACSVFormatter(Application, CSVFormatter);
    }

    private void validateBookingValues(
            InstanceProxy booking,
            List<Object> enumConstants,
            String roomNumber,
            String startTime,
            String endTime
    ) {
        Object result = booking.invoke("getRoomType");
        assertEquals(enumConstants.get(0), result, "Expected getRoomType() to return the value passed to the constructor");

        result = booking.invoke("getRoomNumber");
        assertEquals(roomNumber, result, "Expected getRoomNumber() to return the value passed to the constructor");

        result = booking.invoke("getStartTime");
        assertEquals(startTime, result, "Expected getStartTime() to return the value passed to the constructor");

        result = booking.invoke("getEndTime");
        assertEquals(endTime, result, "Expected getEndTime() to return the value passed to the constructor");
    }

    private void validateParsedBookingValues(
            InstanceProxy booking,
            String stringToParse,
            Class enumClass,
            List<Object> enumConstants,
            String roomNumber,
            String startTime,
            String endTime
    ) {
        Object result = booking.invoke("getRoomType");
        if (!enumConstants.contains(result)) {
            fail(String.format(
                    "Expected getRoomType() to return an enum value from %s after parsing \"%s\", but got %s",
                    stringToParse,
                    enumClass.getSimpleName(),
                    result
            ));
        }

        result = booking.invoke("getRoomNumber");
        assertEquals(
                roomNumber,
                result,
                "Expected getRoomNumberMethod() to return the room number from \"" + stringToParse + "\""
        );

        result = booking.invoke("getStartTime");
        assertEquals(
                startTime,
                result,
                "Expected getStartTime() to return the startTime from \"" + stringToParse + "\""
        );

        result = booking.invoke("getEndTime");
        assertEquals(
                endTime,
                result,
                "Expected getStartTime() to return the endTime from \"" + stringToParse + "\""
        );
    }

    private void assertThatGetFormatterReturnsAJSONFormatter(ClassProxy Application, ClassProxy Formatter) {
        Object formatter = Application.invoke("getFormatter", "json");
        assertEquals(
                Formatter.getDelegate(),
                formatter.getClass(),
                "Your getFormatter method should return an instance of a JSONFormatter when given the argument 'json'"
        );
    }

    private void assertThatGetFormatterReturnsAHTMLFormatter(ClassProxy Application, ClassProxy Formatter) {
        Object formatter = Application.invoke("getFormatter", "html");
        assertEquals(
                Formatter.getDelegate(),
                formatter.getClass(),
                "Your getFormatter method should return an instance of an HTMLFormatter when given the argument 'html'"
        );
    }

    private void assertThatGetFormatterReturnsACSVFormatter(ClassProxy Application, ClassProxy Formatter) {
        Object formatter = Application.invoke("getFormatter", "csv");
        assertEquals(
                Formatter.getDelegate(),
                formatter.getClass(),
                "Your getFormatter method should return an instance of a CSVFormatter when given the argument 'csv'"
        );
    }

    private Class getEnumClass(Class bookingClass) {
        for (Class innerClass : bookingClass.getDeclaredClasses()) {
            if (innerClass.isEnum()) {
                return innerClass;
            }
        }
        return null;
    }

    private String callWithoutPrinting(InstanceProxy source, String method, Object... args) {
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        String result = (String) source.invoke(method, args);
        System.setOut(original);
        if (!output.toString().isEmpty()) {
            fail(String.format(
                    "Expected %s.%s to not print anything to System.out, but it printed \"%s\"",
                    source.getClass().getSimpleName(),
                    method,
                    output.toString().replace("\n", "\\n")
            ));
        }
        return result;
    }
}