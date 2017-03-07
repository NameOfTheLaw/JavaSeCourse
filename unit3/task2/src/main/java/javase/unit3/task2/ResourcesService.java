package javase.unit3.task2;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for handling question and answers.
 */
public class ResourcesService {

    private final static String questionInListFormat = "%d) %s";

    private ResourceBundle resourceBundle;
    private List<QuestionAndAnswer> questionAndAnswers;
    private Charset outputCharset;

    /**
     * Constructor.
     *
     * @param resourceBundle contains questions, answers and other info messages.
     * @param outputCharset charset for output objects (strings).
     */
    public ResourcesService(ResourceBundle resourceBundle, Charset outputCharset) {
        this.resourceBundle = resourceBundle;
        this.outputCharset = outputCharset;
        questionAndAnswers = loadQuestionsFrom(resourceBundle);
    }

    /**
     * Constructor.
     *
     * @param resourceBundle contains questions, answers and other info messages.
     */
    public ResourcesService(ResourceBundle resourceBundle) {
        this(resourceBundle, Charset.defaultCharset());
    }

    /**
     * Constructor.
     *
     * @param resourceBundlePath path for resourceBundle.
     * @param locale for strings in bundle.
     */
    public ResourcesService(String resourceBundlePath, Locale locale) {
        this(ResourceBundle.getBundle(resourceBundlePath, locale), Charset.defaultCharset());
    }

    /**
     * Constructor.
     *
     * @param resourceBundlePath path for resourceBundle.
     * @param locale for strings in bundle.
     * @param outputCharset charset for output objects (strings).
     */
    public ResourcesService(String resourceBundlePath, Locale locale, Charset outputCharset) {
        this(ResourceBundle.getBundle(resourceBundlePath, locale), outputCharset);
    }

    /**
     * Prints all questions from bundle into given PrintStream.
     *
     * @param printStream for print into.
     */
    public void printAllQuestions(PrintStream printStream) {
        printStream.println(getStringWithEncoding("questions.intro"));
        for (int i = 0; i < questionAndAnswers.size(); i++) {
            printStream.println(String.format(
                    questionInListFormat,
                    i + 1,
                    getStringWithEncoding(questionAndAnswers.get(i).getQuestionKey()))
            );
        }
    }

    /**
     * Prints all questions into <code>System.out</code>.
     */
    public void printAllQuestions() {
        printAllQuestions(System.out);
    }

    /**
     * Returns question by index.
     *
     * @param index
     * @return
     */
    public String getQuestion(int index) {
        checkIndexToBeLegal(index);

        return getStringWithEncoding(
                questionAndAnswers.get(index - 1).getQuestionKey());
    }

    /**
     * Returns answer by index.
     *
     * @param index
     * @return
     */
    public String getAnswer(int index) {
        checkIndexToBeLegal(index);

        return getStringWithEncoding(
                questionAndAnswers.get(index - 1).getAnswerKey());
    }

    /**
     * Returns list of all questions.
     *
     * @return
     */
    public List<String> getAllQuestions() {
        return questionAndAnswers.stream()
                .map((x) -> getStringWithEncoding(x.getQuestionKey()))
                .collect(Collectors.toList());
    }

    /**
     * Returns a string for given key.
     *
     * @param key
     * @return
     */
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
