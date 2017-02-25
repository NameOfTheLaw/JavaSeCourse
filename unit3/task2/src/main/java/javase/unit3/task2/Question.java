package javase.unit3.task2;

/**
 * Created by andrey on 25.02.2017.
 */
public class Question {
    public final static String inputPattern = "question.%d";
    public final static String inputAnswerPattern = "question.%d.answer";

    private String questionPropKey;
    private String answerPropKey;

    public Question(String questionPropKey, String answerPropKey) {
        if (questionPropKey == null || answerPropKey == null) {
            throw new NullPointerException();
        }

        this.questionPropKey = questionPropKey;
        this.answerPropKey = answerPropKey;
    }

    public String getQuestionPropKey() {
        return questionPropKey;
    }

    public String getAnswerPropKey() {
        return answerPropKey;
    }
}
