package javase.unit3.task2;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    @Test
    public void testCreateQuestion() {
        Question question = new Question("", "");
        assertNotNull(question.getQuestionKey());
        assertNotNull(question.getAnswerKey());
    }

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
