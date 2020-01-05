package com.qbros.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * General utility class for appending and printing properly formatted message
 */
public class StringUtils {

    private static final String SPACE = " ";
    private static final String EMPTY = "";

    /**
     * @param messageParts message parts are concatenated together and then shown on the default
     *                     console, which is STDOUT {@code System.out.println}.
     */
    public static void printMsgInConsole(String... messageParts) {
        System.out.println(strAppend(messageParts));
    }

    public static String strAppend(String... parts) {
        if (parts == null) {
            return EMPTY;
        } else {
            return Stream.of(parts).collect(Collectors.joining(SPACE));
        }
    }
}
