package javase.unit3.task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LocaleApp {

    private final static Locale defaultLocale = Locale.ENGLISH;
    private final static String resourceBundleUrl = "messages";
    private final static String questionOutputFormat = "%d) %s";

    private List<Question> questions;
    private Locale choosenLocale;
    private Map<String, Locale> supportedLocales;
    private ResourceBundle resourceBundle;
    private BufferedReader bufferedReader;

    public static void main(String[] args) {
        LocaleApp localeApp = LocaleApp.newInstance();
        localeApp.run();
    }

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
                            questions.get(userInput - 1).getAnswerKey())
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
                    questionOutputFormat,
                    i + 1,
                    resourceBundle.getString(questions.get(i).getQuestionKey()))
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
            String questionKey = String.format(Question.QUESTION_KEY_FORMAT, i);
            String answerKey = String.format(Question.ANSWER_KEY_FORMAT, i);
            if (resourceBundle.containsKey(questionKey)) {
                questions.add(new Question(questionKey,answerKey));
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
