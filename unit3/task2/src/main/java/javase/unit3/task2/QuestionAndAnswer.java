package javase.unit3.task2;

import java.util.Objects;

/**
 * Created by andrey on 25.02.2017.
 */
public class QuestionAndAnswer {
    public final static String QUESTION_KEY_FORMAT = "question.%d";
    public final static String ANSWER_KEY_FORMAT = "question.%d.answer";

    private String questionKey;
    private String answerKey;

    public QuestionAndAnswer(String questionKey, String answerKey) {
        Objects.requireNonNull(questionKey);
        Objects.requireNonNull(answerKey);

        this.questionKey = questionKey;
        this.answerKey = answerKey;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public static String formatQuestionKey(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index of the question must be more or equals to 0");
        }

        return String.format(QUESTION_KEY_FORMAT, index);
    }

    public static String formatAnswerKey(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index of the question must be more or equals to 0");
        }

        return String.format(ANSWER_KEY_FORMAT, index);
    }

}
