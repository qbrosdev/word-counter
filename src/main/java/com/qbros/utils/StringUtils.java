package com.qbros.utils;

/**
 * General utility class for appending and printing properly formatted message
 */
public class StringUtils {

    private static final String SPACE = " ";

    /**
     *
     * @param messageParts message parts are concatenated together and then shown on the default
     *                     console, which is STDOUT {@code System.out.println}.
     */
    public static void printMsgInConsole(String... messageParts) {
        System.out.println(strAppend(messageParts));
    }

    public static String strAppend(String... parts) {
        if (parts.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String part : parts) {
                stringBuilder.append(part);
                stringBuilder.append(SPACE);
            }
            return stringBuilder.toString();
        } else {
            return parts[0];
        }
    }
}
