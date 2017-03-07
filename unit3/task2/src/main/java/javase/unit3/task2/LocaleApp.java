package javase.unit3.task2;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Locale questions and answers app.
 */
public class LocaleApp {

    private final static Locale defaultLocale = Locale.ENGLISH;
    //default charset for IntelliJ embedded console
    private final static Charset defaultConsoleCharset = Charset.forName("ISO-8859-1");
    private final static String resourceBundleUrl = "messages";

    private Locale chosenLocale;
    private Map<String, Locale> supportedLocales;
    private BufferedReader bufferedReader;
    private ResourcesService resourcesService;
    private PrintStream printStream;

    public static void main(String[] args) {
        LocaleApp localeApp = new  LocaleApp();
        localeApp.run();
    }

    /**
     * Constructor.
     *
     * @param inputStream stream for user input.
     * @param outputStream stream for output.
     */
    public LocaleApp(InputStream inputStream, OutputStream outputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        printStream = new PrintStream(outputStream);

        resourcesService = new ResourcesService(resourceBundleUrl, defaultLocale, defaultConsoleCharset);

        supportedLocales = loadSupportedLocales();
    }

    public LocaleApp() {
        this(System.in, System.out);
    }

    /**
     * Runs the app.
     */
    public void run() {

            while (!isLocaleSet()) {
                printStream.println(resourcesService.getString("choose.locale"));

                String userInput;
                printStream.print(">");
                try {
                    userInput = bufferedReader.readLine();
                } catch (IOException e) {
                    printStream.println(resourcesService.getString("error.input"));
                    continue;
                }

                tryToSetLocale(userInput);
            }

            while (true) {
                resourcesService.printAllQuestions(printStream);

                int userInput;
                printStream.print("> ");
                try {
                    userInput = Integer.valueOf(bufferedReader.readLine());
                } catch (NumberFormatException | IOException e) {
                    printStream.println(resourcesService.getString("error.input"));
                    printStream.println();
                    continue;
                }

                try {
                    printStream.println(resourcesService.getAnswer(userInput));
                } catch (IllegalArgumentException e) {
                    printStream.println(resourcesService.getString("error.outofindex"));
                }

                printStream.println();
            }
    }

    private void tryToSetLocale(String userTypedLocale) {
        if (userTypedLocale != null && !userTypedLocale.isEmpty() && supportedLocales.containsKey(userTypedLocale)) {
            chosenLocale = supportedLocales.get(userTypedLocale);
            resourcesService = new ResourcesService(resourceBundleUrl, chosenLocale, defaultConsoleCharset);
        } else {
            printStream.println(resourcesService.getString("error.input"));
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
