package javase.unit3.task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by andrey on 25.02.2017.
 */
public class LocaleApp {

    private final static Locale defaultLocale = Locale.ENGLISH;
    private final static String resourceBundleUrl = "messages";
    private final static String questionOutputPattern = "%d) %s";

    private List<Question> questions;
    private Locale choosenLocale;
    private Map<String, Locale> supportedLocales;
    private ResourceBundle resourceBundle;
    private BufferedReader bufferedReader;

    private LocaleApp() {}

    public static LocaleApp newInstance() {
        LocaleApp localeApp = new LocaleApp();
        localeApp.init();
        return localeApp;
    }

    public void run() {
            while (choosenLocale == null) {
                System.out.println(resourceBundle.getString("choose.locale"));

                String userInput;
                System.out.print("> ");
                try {
                    userInput = bufferedReader.readLine();
                } catch (IOException e) {
                    System.out.println(resourceBundle.getString("error.input"));
                    continue;
                }
                if (userInput != null && !userInput.isEmpty() && supportedLocales.containsKey(userInput)) {
                    choosenLocale = supportedLocales.get(userInput);
                    resourceBundle = ResourceBundle.getBundle(resourceBundleUrl, choosenLocale);
                } else {
                    System.out.println(resourceBundle.getString("error.input"));
                }
            }

            while (true) {
                printQuestions();

                int userInput;
                System.out.print("> ");
                try {
                    userInput = Integer.valueOf(bufferedReader.readLine());
                } catch (Exception e) {
                    System.out.println(resourceBundle.getString("error.input"));
                    System.out.println();
                    continue;
                }

                if (userInput > 0 && userInput <= questions.size()) {
                    System.out.println(resourceBundle.getString(
                            questions.get(userInput - 1).getAnswerPropKey())
                    );
                } else {
                    System.out.println(resourceBundle.getString("error.outofindex"));
                }

                System.out.println();
            }
    }

    private void printQuestions() {
        System.out.println(resourceBundle.getString("questions.intro"));
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(String.format(
                    questionOutputPattern,
                    i + 1,
                    resourceBundle.getString(questions.get(i).getQuestionPropKey()))
            );
        }
    }

    private void init() {
        resourceBundle = ResourceBundle.getBundle(resourceBundleUrl, defaultLocale);

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        supportedLocales = loadSupportedLocales();

        questions = loadQuestions();
    }

    private List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<> ();

        int i = 1;
        while (true) {
            String questionUrl = String.format(Question.inputPattern, i);
            String answerUrl = String.format(Question.inputAnswerPattern, i);
            if (resourceBundle.containsKey(questionUrl)) {
                questions.add(new Question(
                        questionUrl,
                        answerUrl)
                );
            } else {
                return questions;
            }
            i++;
        }
    }

    private HashMap<String, Locale> loadSupportedLocales() {
        HashMap<String, Locale> supportedLocales = new HashMap<>();

        supportedLocales.put("en", Locale.ENGLISH);
        supportedLocales.put("ru", Locale.forLanguageTag("ru"));

        return supportedLocales;
    }
}
