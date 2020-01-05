package com.qbros.utils;

import com.qbros.ContextManager;

import java.time.Duration;
import java.time.Instant;

import static com.qbros.utils.StringUtils.printMsgInConsole;

public class Logger {

    private final static String SWITCH_TO_DEBUG_MODE_HINT = "Error Happened, to see full stack trace run with option " +
            "-d (Debug mode)";

    /**
     * tracks the time it takes to complete a runnable
     *
     * @param msg      message to be shown on the logDebug
     * @param runnable the runnable that we want to track the time of its execution
     */
    public static void logTime(String msg, Runnable runnable) {
        if (ContextManager.getContext().isDebug()) {
            Instant before = Instant.now();
            runnable.run();
            Instant after = Instant.now();
            printMsgInConsole(msg + Duration.between(before, after));
        } else {
            runnable.run();
        }
    }

    public static void logDebug(String msg) {
        if (ContextManager.getContext().isDebug()) {
            printMsgInConsole(msg);
        }
    }

    /**
     * This method logs the exception and also tells the user to switch to {@link com.qbros.model.Context#debug}
     * to see the stack trace of the exception
     * @param msg exception message to be shown in the console
     */
    public static void logException(String... msg) {
        printMsgInConsole(SWITCH_TO_DEBUG_MODE_HINT);
        printMsgInConsole(msg);
    }

}
