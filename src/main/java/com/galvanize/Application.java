package com.galvanize;

import com.galvanize.formatters.*;

public class Application {
    public static Formatter getFormatter(String outputType) {
        switch (outputType) {
            case "json":
                return new JSONFormatter();
            case "csv":
                return new CSVFormatter();
            case "html":
                return new HTMLFormatter();
        }

        return new JSONFormatter();
    }

    public static void main(String[] args) {
        String input = args[0];
        String outputType = args[1];

        Formatter formatter = getFormatter(outputType);

        Booking booking = Booking.parse(input);

        System.out.println(formatter.format(booking));
    }
}