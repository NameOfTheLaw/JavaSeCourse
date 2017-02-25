package javase.unit3.task1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classic logger without log types.
 *
 * Created by andrey on 25.02.2017.
 */
public class CrazyLogger {
    private StringBuilder stringBuilder = new StringBuilder();
    public final static String dataTimePattern = "dd-MM-YYYY : HH-mm";
    public final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dataTimePattern);
    public final static String messagesSeparator = ";";
    public final static String messageInnerSeparator = " - ";

    /**
     * Adds message to the log.
     *
     * @param message
     */
    public void log(String  message) {
        if (message == null) {
            throw new NullPointerException();
        }

        if (hasSeparator(message)) {
            throw new IllegalArgumentException("String has CrazyLogger's separator symbol \"" + messagesSeparator +"\".");
        }

        if (!message.isEmpty()) {
            stringBuilder.append(LocalDateTime.now().format(dateTimeFormatter))
                    .append(messageInnerSeparator)
                    .append(message)
                    .append(messagesSeparator);
        }

        System.out.println(stringBuilder);
    }

    /**
     * Returns all of the log.
     *
     * @return
     */
    public String getLog() {
        return stringBuilder.toString();
    }

    /**
     * Returns the first log record where string argument is substring of the log message.
     *
     * @param stringToFind
     * @return
     */
    public String findFirst(String stringToFind) {
        if (hasSeparator(stringToFind)) {
            throw new IllegalArgumentException("String has CrazyLogger's separator symbol \"" + messagesSeparator +"\".");
        }

        int index = stringBuilder.indexOf(stringToFind);

        return getSubstringOf(index, stringToFind.length());
    }

    /**
     * Returns the last log record where string argument is substring of the log message.
     *
     * @param stringToFind
     * @return
     */
    public String findLast(String stringToFind) {
        if (hasSeparator(stringToFind)) {
            throw new IllegalArgumentException("String has CrazyLogger's separator symbol \"" + messagesSeparator +"\".");
        }

        int index = stringBuilder.lastIndexOf(stringToFind);

        return getSubstringOf(index, stringToFind.length());
    }

    /**
     * Returns all of the log records where string argument is substring of the log messages.
     *
     * @param stringToFind
     * @return
     */
    public String findAll(String stringToFind) {
        if (hasSeparator(stringToFind)) {
            throw new IllegalArgumentException("String has CrazyLogger's separator symbol \"" + messagesSeparator +"\".");
        }

        int nextIndex = 0;
        StringBuilder resultBuilder = new StringBuilder();
        do {
            nextIndex = stringBuilder.indexOf(stringToFind, nextIndex);
            resultBuilder.append(getSubstringOf(nextIndex, stringToFind.length()));
            nextIndex += 1;
        } while (nextIndex > 0);

        return resultBuilder.toString();
    }

    private boolean hasSeparator(String stringToFind) {
        return stringToFind.contains(messagesSeparator);
    }

    private String getSubstringOf(int index, int stringLength) {

        if (index >= 0) {
            int leftInclude = stringBuilder.lastIndexOf(messagesSeparator, index);
            if (leftInclude < 0) leftInclude = 0;

            int rightExclude = stringBuilder.indexOf(messagesSeparator, index + stringLength - 1);
            return stringBuilder.substring(leftInclude, rightExclude);
        }

        return "";
    }
}
