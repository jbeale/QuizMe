package com.quizme.api.security;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by jbeale on 1/30/15.
 */
public class TokenGenerator {

    private static char[] TOKEN_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static int TOKEN_LENGTH = 64;

    public static String getNewToken() {
        SecureRandom srandom = new SecureRandom();
        Random random = new Random();
        char[] buffer = new char[TOKEN_LENGTH];

        for (int i =0; i < TOKEN_LENGTH; i++) {
            if ((i% 10) == 0) {
                random.setSeed(srandom.nextLong());
            }
            buffer[i] = TOKEN_CHARS[random.nextInt(TOKEN_CHARS.length)];
        }

        return new String(buffer);
        //return RandomStringUtils.randomAlphanumeric(64);
        //return new BigInteger(260, random).toString(32);
    }
}
