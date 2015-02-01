package com.quizme.api.security;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by jbeale on 1/30/15.
 */
public class TokenGeneratorTest {
    @Test
    public void testGeneratedTokensShouldBeRandom() {
        System.out.println(TokenGenerator.getNewToken());
        System.out.println(TokenGenerator.getNewToken());
        System.out.println(TokenGenerator.getNewToken());
        System.out.println(TokenGenerator.getNewToken());
        System.out.println(TokenGenerator.getNewToken());
        System.out.println(TokenGenerator.getNewToken());

    }
}
