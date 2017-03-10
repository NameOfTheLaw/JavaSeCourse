package javase.unit4.task4;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class UI-shell for {@link FilmCollection} handling.
 */
public class FilmsApp {

    private static String collectionFileName = "serialCollection";
    private static Pattern argumentPattern = Pattern.compile("\"([\\w ]+)\"");
    private static int argumentPatternGroup = 1;

    public static void main(String[] args) {
        System.out.printf("FilmsApp.%n%n");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            FilmCollection filmCollection;
            try {
                filmCollection = FilmCollection.load(collectionFileName);
            } catch (FileNotFoundException e) {
                filmCollection = createNewFilmCollection();
            }

            showAvailableCommands();

            boolean isRunning = true;
            while (isRunning) {
                isRunning = handleInput(bufferedReader.readLine(), filmCollection);
            }

        } catch (IOException e) {
            System.out.printf("Something wrong with file %s.%n", collectionFileName);
        } catch (ClassNotFoundException e) {
            System.out.println("Your film collection are broken. Remove or repair it.");
        }
    }

    private static boolean handleInput(String userInput, FilmCollection filmCollection) throws IOException {

        String command = getCommandFromInput(userInput);

        switch (command) {

            case "/add": {
                Matcher matcher = getMatcherOfCommandArguments(userInput, command);

                String filmName = getFilmName(matcher);
                if (filmName == null) break;

                Set<Actor> actorsSet = getActors(matcher);

                if (!actorsSet.isEmpty()) {
                    filmCollection.add(new Film(filmName, actorsSet.toArray(new Actor[]{})));
                    System.out.println("Film was added into collection.");
                } else {
                    System.out.println("Wrong number of arguments.");
                    break;
                }

                break;
            }

            case "/remove": {
                Matcher matcher = getMatcherOfCommandArguments(userInput, command);

                String filmName = getFilmName(matcher);
                if (filmName == null) break;

                Set<Actor> actorsSet = getActors(matcher);

                if (!actorsSet.isEmpty()) {
                    filmCollection.remove(new Film(filmName, actorsSet.toArray(new Actor[]{})));
                    System.out.println("Film was removed from collection.");
                } else {
                    System.out.println("Wrong number of arguments.");
                    break;
                }

                break;
            }

            case "/show":
                filmCollection.getFilms()
                        .forEach(System.out::println);
                break;

            case "/exit":
                filmCollection.save(collectionFileName);
                return false;

            default:
                System.out.println("Where is no such command.");
        }

        return true;
    }

    private static Matcher getMatcherOfCommandArguments(String userInput, String command) {
        String arguments = userInput.substring(command.length());

        return argumentPattern.matcher(arguments);
    }

    private static String getCommandFromInput(String userInput) {
        int separatorIndex = userInput.indexOf(" ");
        String command;
        if (separatorIndex > 0) {
            command = userInput.substring(0, separatorIndex);
        } else {
            command = userInput;
        }
        return command;
    }

    private static FilmCollection createNewFilmCollection() {
        FilmCollection filmCollection;
        System.out.println("Creating your first collection...");
        filmCollection = new FilmCollection();
        System.out.printf("Your first collection was created.%n%n");
        return filmCollection;
    }

    private static void showAvailableCommands() {
        System.out.printf("You can use this commands:%n" +
                "/add \"filmName\" \"actor\"[ \"actor\"]%n" +
                "/rem \"filmName\" \"actor\"[ \"actor\"]%n" +
                "/show%n" +
                "/exit%n%n");
    }

    private static Set<Actor> getActors(Matcher matcher) {
        Set<Actor> actorsSet = new HashSet<>();
        while (matcher.find()) {
            actorsSet.add(new Actor(matcher.group(argumentPatternGroup)));
        }
        return actorsSet;
    }

    private static String getFilmName(Matcher matcher) {
        if (matcher.find()) {
            return matcher.group(argumentPatternGroup);
        } else {
            System.out.println("Wrong number of arguments.");
            return null;
        }
    }
}
