package javase.unit3.task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by andrey on 25.02.2017.
 */
public class LocaleApp {

    private static String resourceBundleUrl = "messages";
    private static String questionOutputPattern = "%d) %s";
    private static List<Question> questions = new ArrayList<> ();

    private static Locale choosenLocale;
    private static Map<String, Locale> supportedLocales;
    private static Locale defaultLocale = Locale.ENGLISH;;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleUrl, defaultLocale);

    public static void main(String[] args) {
        init();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

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
            showQuestions();

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
                        questions.get(userInput - 1).getAnswerUrl())
                );
            } else {
                System.out.println(resourceBundle.getString("error.outofindex"));
            }

            System.out.println();
        }
    }

    private static void showQuestions() {
        System.out.println(resourceBundle.getString("questions.intro"));
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(String.format(
                    questionOutputPattern,
                    i + 1,
                    resourceBundle.getString(questions.get(i).getQuestionUrl()))
            );
        }
    }

    private static void init() {
        supportedLocales = new HashMap<>();
        supportedLocales.put("en", Locale.ENGLISH);
        supportedLocales.put("ru", Locale.forLanguageTag("ru"));

        loadQuestions();
    }

    private static void loadQuestions() {
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
                return;
            }
            i++;
        }
    }
}
