package javase.unit3.task2;

import java.util.Objects;

/**
 * Created by andrey on 25.02.2017.
 */
public class Question {
    public final static String QUESTION_KEY_FORMAT = "question.%d";
    public final static String ANSWER_KEY_FORMAT = "question.%d.answer";

    private String questionKey;
    private String answerKey;

    public Question(String questionKey, String answerKey) {
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

}
