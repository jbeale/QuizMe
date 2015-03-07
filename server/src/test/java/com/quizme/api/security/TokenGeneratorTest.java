package com.quizme.api.security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quizme.api.model.canonical.MultipleChoice;
import com.quizme.api.model.canonical.Option;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
    @Test
    public void testMultChoiceJson() {
        MultipleChoice mc = new MultipleChoice();
        mc.prompt = "HELLO!";
        mc.choices = new ArrayList<Option>();
        Option a = new Option(); a.correct = false; a.text="Option A";
        Option b = new Option(); b.correct = true; b.text="option b";
        Option c = new Option(); c.correct = false; b.text="opt c";
        mc.choices.add(a);
        mc.choices.add(b);
        mc.choices.add(c);
        Gson g = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
        String json = g.toJson(mc);
        System.out.print(json);
    }
}
