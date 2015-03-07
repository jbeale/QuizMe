package com.quizme.api.util;

/**
 * Created by jbeale on 2/26/15.
 */
public class UnixTime {
    public static Long get() {
        return System.currentTimeMillis()/1000L;
    }
}
