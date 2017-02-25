package javase.unit3.task2;

import org.junit.Before;
import org.junit.Test;

import java.util.ResourceBundle;

import static org.junit.Assert.*;

/**
 * Created by andrey on 25.02.2017.
 */
public class LocaleAppTest {

    @Test(expected = NullPointerException.class)
    public void testCreateQuestionWithNullArg1() {
        new Question(null, "");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateQuestionWithNullArg2() {
        new Question("", null);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateQuestionWithNullArgs() {
        new Question(null, null);
    }

}