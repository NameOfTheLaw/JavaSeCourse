package javase.unit5.task1;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UI shell for managing FileSystemController.
 */
public class FileSystemApp {
    private static FileSystemController controller;
    private static Pattern argumentPattern = Pattern.compile("\"([\\w .\\d]+)\"");
    private static int argumentPatternGroup = 1;

    public static void main(String[] args) {
        try {
            controller = FileSystemController.fromCurrentDirectory();
        } catch (DirectoryNotFoundException | NotDirectoryException e) {
            System.out.println("Can't open current directory. Please try again in somewhere else.");
            e.printStackTrace();
        }

        System.out.println("Type commands or use \"help\" to show the list of useful commands.");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            boolean isRunning = true;
            while (isRunning) {
                isRunning = handleInput(bufferedReader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean handleInput(String input) {

        boolean isRunning = true;

        try {
            isRunning = handleCommand(input);
        } catch (WrongArgumentsNumberException | EmptyArgumentException e) {
            System.out.println(e.getMessage());
        }

        return isRunning;
    }

    private static boolean handleCommand(String input) {
        String command = getCommandFromInput(input);

        switch (command) {

            case "ls":
                controller.list().stream()
                        .map(File::getName)
                        .forEach(System.out::println);
                break;

            case "cd": {

                String directoryPath = getFirstArgFromInput(input, command);

                try {
                    controller.move(directoryPath);
                } catch (NotDirectoryException e) {
                    System.out.printf("%s is not a directory.%n", e.getMessage());
                } catch (DirectoryNotFoundException e) {
                    System.out.printf("Directory %s was not found.%n", e.getMessage());
                }

                break;
            }

            case "touch": {

                String filePathToCreate = getFirstArgFromInput(input, command);

                try {
                    controller.create(filePathToCreate);
                } catch (FileAlreadyExistsException e) {
                    System.out.printf("File %s already exists.%n", e.getFile());
                } catch (IOException e) {
                    System.out.printf("File cannot be created: %s%n", e.getMessage());
                }

                break;
            }

            case "del": {

                String filePathToDelete = getFirstArgFromInput(input, command);

                try {
                    controller.delete(filePathToDelete);
                } catch (FileNotFoundException e) {
                    System.out.printf("File was not found: %s.%n", e.getMessage());
                }

                break;
            }

            case "echo": {

                List<String> args = getArgsFromInput(input, command, 2);
                String filePath = args.get(0);
                String message = args.get(1);

                try {
                    controller.echo(filePath, message);
                } catch (FileNotFoundException e) {
                    System.out.printf("File was not found: %s.%n", e.getMessage());
                } catch (IOException e) {
                    System.out.printf("Cannot write in file: %s%n", e.getMessage());
                }

                break;
            }

            case "help":
                showAvailableCommands();
                break;

            case "exit":
                return false;

            default:
                System.out.println("Where is no such command.");
        }

        return true;
    }

    private static void showAvailableCommands() {
        System.out.printf("You can use this commands:%n" +
                "  cd \"directory\"%n" +
                "  ls%n" +
                "  touch \"file name\"%n" +
                "  del \"file name\"%n" +
                "  echo \"file name\" \"message\"%n" +
                "  help%n" +
                "  exit%n");
    }

    private static String getCommandFromInput(String input) {
        int separatorIndex = input.indexOf(" ");
        String command;
        if (separatorIndex > 0) {
            command = input.substring(0, separatorIndex);
        } else {
            command = input;
        }
        return command;
    }

    private static Matcher getMatcherOfCommandArguments(String userInput, String command) {
        String arguments = userInput.substring(command.length());

        return argumentPattern.matcher(arguments);
    }

    private static List<String> getArgsFromInput(String input, String command, int expectedArgsNum) {
        List<String> arguments = new ArrayList<>();

        Matcher matcher = getMatcherOfCommandArguments(input, command);
        while (matcher.find()) {
            String argument = matcher.group(argumentPatternGroup).trim();

            if (argument.isEmpty()) {
                throw new EmptyArgumentException(arguments.size() + 1);
            }

            arguments.add(argument);
        }

        if (arguments.size() != expectedArgsNum) {
            throw new WrongArgumentsNumberException(expectedArgsNum, arguments.size());
        }

        return arguments;
    }

    private static String getFirstArgFromInput(String input, String command) {
        return getArgsFromInput(input, command, 1).get(0);
    }
}
