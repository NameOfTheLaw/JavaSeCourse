package javase.unit3.task2;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionAndAnswerTest {

    @Test
    public void testCreating() {
        QuestionAndAnswer questionAndAnswer = new QuestionAndAnswer("", "");
        assertNotNull(questionAndAnswer.getQuestionKey());
        assertNotNull(questionAndAnswer.getAnswerKey());
    }

    @Test(expected = NullPointerException.class)
    public void testCreatingWithNullArg1() {
        new QuestionAndAnswer(null, "");
    }

    @Test(expected = NullPointerException.class)
    public void testCreatingWithNullArg2() {
        new QuestionAndAnswer("", null);
    }

    @Test(expected = NullPointerException.class)
    public void testCreatingQuestionWithNullArgs() {
        new QuestionAndAnswer(null, null);
    }

}
