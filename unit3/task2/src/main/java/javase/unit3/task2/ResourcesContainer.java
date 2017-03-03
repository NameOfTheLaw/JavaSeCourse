package javase.unit3.task2;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by andrey on 02.03.2017.
 */
public class ResourcesContainer {

    private final static String questionInListFormat = "%d) %s";

    private ResourceBundle resourceBundle;
    private List<QuestionAndAnswer> questionAndAnswers;

    public ResourcesContainer(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        questionAndAnswers = loadQuestionsFrom(resourceBundle);
    }

    public ResourcesContainer(String resourceBundleUrl, Locale locale) {
        this(ResourceBundle.getBundle(resourceBundleUrl, locale));
    }

    public void printAll(PrintStream printStream) {
        System.out.println(resourceBundle.getString("questions.intro"));
        for (int i = 0; i < questionAndAnswers.size(); i++) {
            printStream.println(String.format(
                    questionInListFormat,
                    i + 1,
                    resourceBundle.getString(questionAndAnswers.get(i).getQuestionKey()))
            );
        }
    }

    public void printAll() {
        printAll(System.out);
    }

    public String getQuestion(int index) {
        if (index >= questionAndAnswers.size() || index < 0) {
            throw new IllegalArgumentException();
        }

        return resourceBundle.getString(
                questionAndAnswers.get(index).getQuestionKey());
    }

    public String getAnswer(int index) {
        if (index >= questionAndAnswers.size() || index < 0) {
            throw new IllegalArgumentException();
        }

        return resourceBundle.getString(
                questionAndAnswers.get(index).getAnswerKey());
    }

    public List<String> getAllQuestions() {
        return questionAndAnswers.stream()
                .map((x) -> resourceBundle.getString(x.getQuestionKey()))
                .collect(Collectors.toList());
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    private List<QuestionAndAnswer> loadQuestionsFrom(ResourceBundle resourceBundle) {
        List<QuestionAndAnswer> questionAndAnswers = new ArrayList<>();

        int i = 1;
        while (true) {
            String questionKey = String.format(QuestionAndAnswer.QUESTION_KEY_FORMAT, i);
            String answerKey = String.format(QuestionAndAnswer.ANSWER_KEY_FORMAT, i);
            if (resourceBundle.containsKey(questionKey)) {
                questionAndAnswers.add(new QuestionAndAnswer(questionKey,answerKey));
            } else {
                return questionAndAnswers;
            }
            i++;
        }
    }
}
