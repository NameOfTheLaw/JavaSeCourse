package javase.unit3.task1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Classic logger without log types.
 *
 * Created by andrey on 25.02.2017.
 */
public class CrazyLogger {
    private StringBuilder logBuilder = new StringBuilder();
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
        Objects.requireNonNull(message);

        if (separatorIn(message)) {
            throw new IllegalArgumentException("String has CrazyLogger's separator symbol \"" + messagesSeparator +"\".");
        }

        if (!message.isEmpty()) {
            logBuilder.append(LocalDateTime.now().format(dateTimeFormatter))
                    .append(messageInnerSeparator)
                    .append(message)
                    .append(messagesSeparator);
        }
    }

    /**
     * Returns all of the log.
     *
     * @return
     */
    public String getLog() {
        return logBuilder.toString();
    }

    /**
     * Returns the first log record where string argument is substring of the log message.
     *
     * @param stringToFind
     * @return
     */
    public String findFirst(String stringToFind) {
        Objects.requireNonNull(stringToFind);

        if (separatorIn(stringToFind)) {
            throw new IllegalArgumentException("String has CrazyLogger's separator symbol \"" + messagesSeparator +"\".");
        }

        int index = logBuilder.indexOf(stringToFind);

        return getLogRecord(index, stringToFind.length());
    }

    /**
     * Returns the last log record where string argument is substring of the log message.
     *
     * @param stringToFind
     * @return
     */
    public String findLast(String stringToFind) {
        Objects.requireNonNull(stringToFind);

        if (separatorIn(stringToFind)) {
            throw new IllegalArgumentException("String has CrazyLogger's separator symbol \"" + messagesSeparator +"\".");
        }

        int index = logBuilder.lastIndexOf(stringToFind);

        return getLogRecord(index, stringToFind.length());
    }

    /**
     * Returns all of the log records where string argument is substring of the log messages.
     *
     * @param stringToFind
     * @return
     */
    public String findAll(String stringToFind) {
        Objects.requireNonNull(stringToFind);

        if (separatorIn(stringToFind)) {
            throw new IllegalArgumentException("String has CrazyLogger's separator symbol \"" + messagesSeparator +"\".");
        }

        StringBuilder resultBuilder = new StringBuilder();
        int nextIndex = 0;
        do {
            nextIndex = logBuilder.indexOf(stringToFind, nextIndex);
            resultBuilder.append(getLogRecord(nextIndex, stringToFind.length()));
            nextIndex += 1;
        } while (nextIndex > 0);

        return resultBuilder.toString();
    }

    private boolean separatorIn(String stringToFindSeparatorIn) {
        return stringToFindSeparatorIn.contains(messagesSeparator);
    }

    private String getLogRecord(int index, int stringLength) {

        if (index >= 0) {
            int currentRecordStartIndex = logBuilder.lastIndexOf(messagesSeparator, index);
            if (currentRecordStartIndex < 0) currentRecordStartIndex = 0;

            int nextRecordStartIndex = logBuilder.indexOf(messagesSeparator, index + stringLength - 1);
            return logBuilder.substring(currentRecordStartIndex, nextRecordStartIndex);
        }

        return "";
    }
}
