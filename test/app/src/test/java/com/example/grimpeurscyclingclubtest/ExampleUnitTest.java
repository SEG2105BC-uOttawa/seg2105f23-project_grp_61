package com.example.grimpeurscyclingclubtest;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.example.grimpeurscyclingclubtest.TextInputValidation.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void validateSkillLevelExpectTrue(){assertEquals(true, validateSkillLevel("10"));}
    @Test
    public void validateSkillLevelExpectFalse(){assertEquals(false, validateSkillLevel("ten"));}
}