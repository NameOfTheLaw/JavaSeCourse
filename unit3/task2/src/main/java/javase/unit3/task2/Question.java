package javase.unit3.task2;

/**
 * Created by andrey on 25.02.2017.
 */
public class Question {
    public final static String inputPattern = "question.%d";
    public final static String inputAnswerPattern = "question.%d.answer";

    private String questionUrl;
    private String answerUrl;

    public Question(String questionUrl, String answerUrl) {
        if (questionUrl == null || answerUrl == null) {
            throw new NullPointerException();
        }
        this.questionUrl = questionUrl;
        this.answerUrl = answerUrl;
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public String getAnswerUrl() {
        return answerUrl;
    }
}
