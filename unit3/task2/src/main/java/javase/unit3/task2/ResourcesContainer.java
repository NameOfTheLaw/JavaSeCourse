package javase.unit3.task2;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class ResourcesContainer {

    private final static String questionInListFormat = "%d) %s";

    private ResourceBundle resourceBundle;
    private List<QuestionAndAnswer> questionAndAnswers;
    private Charset outputCharset;

    public ResourcesContainer(ResourceBundle resourceBundle, Charset outputCharset) {
        this.resourceBundle = resourceBundle;
        this.outputCharset = outputCharset;
        questionAndAnswers = loadQuestionsFrom(resourceBundle);
    }

    public ResourcesContainer(ResourceBundle resourceBundle) {
        this(resourceBundle, Charset.defaultCharset());
    }

    public ResourcesContainer(String resourceBundleUrl, Locale locale) {
        this(ResourceBundle.getBundle(resourceBundleUrl, locale), Charset.defaultCharset());
    }

    public ResourcesContainer(String resourceBundleUrl, Locale locale, Charset outputCharset) {
        this(ResourceBundle.getBundle(resourceBundleUrl, locale), outputCharset);
    }

    public void printAllQuestions(PrintStream printStream) throws UnsupportedEncodingException {
        printStream.println(getStringWithEncoding("questions.intro"));
        for (int i = 0; i < questionAndAnswers.size(); i++) {
            printStream.println(String.format(
                    questionInListFormat,
                    i + 1,
                    getStringWithEncoding(questionAndAnswers.get(i).getQuestionKey()))
            );
        }
    }

    public void printAllQuestions() throws UnsupportedEncodingException {
        printAllQuestions(System.out);
    }

    public String getQuestion(int index) {
        checkIndexToBeLegal(index);

        return getStringWithEncoding(
                questionAndAnswers.get(index - 1).getQuestionKey());
    }

    public String getAnswer(int index) {
        checkIndexToBeLegal(index);

        return getStringWithEncoding(
                questionAndAnswers.get(index - 1).getAnswerKey());
    }

    public List<String> getAllQuestions() {
        return questionAndAnswers.stream()
                .map((x) -> getStringWithEncoding(x.getQuestionKey()))
                .collect(Collectors.toList());
    }

    public String getString(String key) {
        return getStringWithEncoding(key);
    }

    private String getStringWithEncoding(String key) {
        String rawString = resourceBundle.getString(key);
        return new String(rawString.getBytes(outputCharset));
    }

    private void checkIndexToBeLegal(int index) {
        if (index > questionAndAnswers.size() || index < 1) {
            throw new IllegalArgumentException();
        }
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
