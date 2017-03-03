package javase.unit3.task2;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class LocaleApp {

    private final static Locale defaultLocale = Locale.ENGLISH;
    private final static Charset defaultConsoleCharset = Charset.forName("ISO-8859-1");
    private final static String resourceBundleUrl = "messages";

    private Locale chosenLocale;
    private Map<String, Locale> supportedLocales;
    private BufferedReader bufferedReader;
    private ResourcesContainer resourcesContainer;
    private PrintStream printStream;

    public static void main(String[] args) throws UnsupportedEncodingException {
        LocaleApp localeApp = new  LocaleApp();
        localeApp.run();
    }

    public LocaleApp(InputStream inputStream, OutputStream outputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        printStream = new PrintStream(outputStream);

        resourcesContainer = new ResourcesContainer(resourceBundleUrl, defaultLocale, defaultConsoleCharset);

        supportedLocales = loadSupportedLocales();
    }

    public LocaleApp() {
        this(System.in, System.out);
    }

    public void run() throws UnsupportedEncodingException {

            while (!isLocaleSet()) {
                printStream.println(resourcesContainer.getString("choose.locale"));

                String userInput;
                printStream.print(">");
                try {
                    userInput = bufferedReader.readLine();
                } catch (IOException e) {
                    printStream.println(resourcesContainer.getString("error.input"));
                    continue;
                }

                tryToSetLocale(userInput);
            }

            while (true) {
                resourcesContainer.printAllQuestions(printStream);

                int userInput;
                printStream.print("> ");
                try {
                    userInput = Integer.valueOf(bufferedReader.readLine());
                } catch (NumberFormatException | IOException e) {
                    printStream.println(resourcesContainer.getString("error.input"));
                    printStream.println();
                    continue;
                }

                try {
                    printStream.println(resourcesContainer.getAnswer(userInput));
                } catch (IllegalArgumentException e) {
                    printStream.println(resourcesContainer.getString("error.outofindex"));
                }

                printStream.println();
            }
    }

    private void tryToSetLocale(String userTypedLocale) {
        if (userTypedLocale != null && !userTypedLocale.isEmpty() && supportedLocales.containsKey(userTypedLocale)) {
            chosenLocale = supportedLocales.get(userTypedLocale);
            resourcesContainer = new ResourcesContainer(resourceBundleUrl, chosenLocale, defaultConsoleCharset);
        } else {
            printStream.println(resourcesContainer.getString("error.input"));
        }
    }

    private boolean isLocaleSet() {
        return chosenLocale != null;
    }

    private HashMap<String, Locale> loadSupportedLocales() {
        HashMap<String, Locale> supportedLocales = new HashMap<>();

        supportedLocales.put("en", Locale.ENGLISH);
        supportedLocales.put("ru", Locale.forLanguageTag("ru"));

        return supportedLocales;
    }
}
