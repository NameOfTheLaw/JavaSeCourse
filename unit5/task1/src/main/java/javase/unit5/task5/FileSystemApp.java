package javase.unit5.task5;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String command = getCommandFromInput(input);

        switch (command) {

            case "ls":
                controller.list().stream()
                        .map(File::getName)
                        .forEach(System.out::println);
                break;

            case "cd": {
                Matcher matcher = getMatcherOfCommandArguments(input, command);

                if (matcher.find()) {
                    try {
                        controller.move(matcher.group(argumentPatternGroup));
                    } catch (NotDirectoryException e) {
                        System.out.printf("%s is not a directory.%n", e.getMessage());
                    } catch (DirectoryNotFoundException e) {
                        System.out.printf("Directory %s was not found.%n", e.getMessage());
                    }
                } else {
                    throw new WrongArgumentsNumberException(1, 0);
                }

                break;
            }

            case "touch": {
                Matcher matcher = getMatcherOfCommandArguments(input, command);

                if (matcher.find()) {
                    try {
                        controller.create(matcher.group(argumentPatternGroup));
                    } catch (FileAlreadyExistsException e) {
                        System.out.printf("File %s already exists.%n", e.getFile());
                    } catch (IOException e) {
                        System.out.printf("File cannot be created: %s%n", e.getMessage());
                    }
                } else {
                    throw new WrongArgumentsNumberException(1, 0);
                }

                break;
            }

            case "del": {
                Matcher matcher = getMatcherOfCommandArguments(input, command);

                if (matcher.find()) {
                    try {
                        controller.delete(matcher.group(argumentPatternGroup));
                    } catch (FileNotFoundException e) {
                        System.out.printf("File was not found: %s.%n", e.getMessage());
                    }
                } else {
                    throw new WrongArgumentsNumberException(1, 0);
                }

                break;
            }

            case "echo": {
                Matcher matcher = getMatcherOfCommandArguments(input, command);

                if (matcher.find()) {
                    String filePath = matcher.group(argumentPatternGroup);

                    String message;
                    if (matcher.find()) {
                        message = matcher.group(argumentPatternGroup);
                    } else {
                        throw new WrongArgumentsNumberException(2, 1);
                    }

                    try {
                        controller.echo(filePath, message);
                    } catch (FileNotFoundException e) {
                        System.out.printf("File was not found: %s.%n", e.getMessage());
                    } catch (IOException e) {
                        System.out.printf("Cannot write in file: %s%n", e.getMessage());
                    }
                } else {
                    throw new WrongArgumentsNumberException(2, 0);
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
                "  exit%n%n");
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
}
