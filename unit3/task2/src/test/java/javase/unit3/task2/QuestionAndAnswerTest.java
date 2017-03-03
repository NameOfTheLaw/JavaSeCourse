package javase.unit3.task2;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionAndAnswerTest {

    @Test
    public void testCreateQuestion() {
        QuestionAndAnswer questionAndAnswer = new QuestionAndAnswer("", "");
        assertNotNull(questionAndAnswer.getQuestionKey());
        assertNotNull(questionAndAnswer.getAnswerKey());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateQuestionWithNullArg1() {
        new QuestionAndAnswer(null, "");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateQuestionWithNullArg2() {
        new QuestionAndAnswer("", null);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateQuestionWithNullArgs() {
        new QuestionAndAnswer(null, null);
    }

}
