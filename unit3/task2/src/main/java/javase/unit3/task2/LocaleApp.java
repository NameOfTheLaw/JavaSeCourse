package javase.unit3.task2;

import java.io.*;
import java.util.*;

public class LocaleApp {

    private final static Locale defaultLocale = Locale.ENGLISH;
    private final static String resourceBundleUrl = "messages";

    private Locale chosenLocale;
    private Map<String, Locale> supportedLocales;
    private BufferedReader bufferedReader;
    private ResourcesContainer resourcesContainer;

    public static void main(String[] args) {
        LocaleApp localeApp = new  LocaleApp();
        localeApp.run();
    }

    public LocaleApp(InputStream inputStream) {
        resourcesContainer = new ResourcesContainer(resourceBundleUrl, defaultLocale);

        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        supportedLocales = loadSupportedLocales();
    }

    public LocaleApp() {
        this(System.in);
    }

    public void run() {

            while (!isLocaleSet()) {
                System.out.println(resourcesContainer.getString("choose.locale"));

                String userInput;
                System.out.print(">");
                try {
                    userInput = bufferedReader.readLine();
                } catch (IOException e) {
                    System.out.println(resourcesContainer.getString("error.input"));
                    continue;
                }

                tryToSetLocale(userInput);
            }

            while (true) {
                resourcesContainer.printAll(System.out);

                int userInput;
                System.out.print("> ");
                try {
                    userInput = Integer.valueOf(bufferedReader.readLine());
                } catch (NumberFormatException | IOException e) {
                    System.out.println(resourcesContainer.getString("error.input"));
                    System.out.println();
                    continue;
                }

                try {
                    System.out.println(resourcesContainer.getAnswer(userInput));
                } catch (IllegalArgumentException e) {
                    System.out.println(resourcesContainer.getString("error.outofindex"));
                }

                System.out.println();
            }
    }

    private void tryToSetLocale(String userTypedLocale) {
        if (userTypedLocale != null && !userTypedLocale.isEmpty() && supportedLocales.containsKey(userTypedLocale)) {
            chosenLocale = supportedLocales.get(userTypedLocale);
            resourcesContainer = new ResourcesContainer(resourceBundleUrl, chosenLocale);
        } else {
            System.out.println(resourcesContainer.getString("error.input"));
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
