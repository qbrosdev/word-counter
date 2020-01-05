package com.qbros.utils;

import com.qbros.ContextManager;
import com.qbros.model.Context;

import static com.qbros.utils.StringUtils.strAppend;

/**
 * class that handles exceptions and shows proper message based on {@link Context#isDebug()} value.
 */
public class ExceptionUtil {

    private final static String EXCEPTION_MSG_PREFIX = "Exception Message: ";

    public static void handleException(Throwable e, String... msgParts) {
        if (ContextManager.getContext().isDebug()) {
            e.printStackTrace();
        } else {
            Logger.logException(strAppend(msgParts), EXCEPTION_MSG_PREFIX, e.getMessage());
        }
    }
}
