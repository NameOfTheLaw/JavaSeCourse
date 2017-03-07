package javase.unit3.task1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Classic logger without log types.
 *
 * All log messages could not contain {@link #messagesSeparator}.
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
     * @throws IllegalArgumentException if message contains {@link #messagesSeparator}.
     */
    public void log(String  message) throws IllegalArgumentException {
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
     * Returns the first log record where given string is a substring of the log message.
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
     * Returns the last log record where given string is a substring of the log message.
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
     * Returns all of the log records where given string is a substring of the log messages.
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

    /**
     * Prints the first log record where given string is a substring of the log message.
     *
     * @param stringToFind
     * @return if logger have find record with the given string or not.
     */
    public boolean printFirstIfExists(String stringToFind) {
        Objects.requireNonNull(stringToFind);

        String logRecord = findFirst(stringToFind);

        if (logRecord.isEmpty()) {
            return false;
        } else {
            System.out.println(logRecord);
            return true;
        }
    }

    /**
     * Prints the last log record where given string is a substring of the log message.
     *
     * @param stringToFind
     * @return if logger have find record with the given string or not.
     */
    public boolean printLastIfExists(String stringToFind) {
        Objects.requireNonNull(stringToFind);

        String logRecord = findLast(stringToFind);

        if (logRecord.isEmpty()) {
            return false;
        } else {
            System.out.println(logRecord);
            return true;
        }
    }


    /**
     * Prints all log records where given string is a substring of the log message.
     *
     * @param stringToFind
     * @return if logger have find any records with the given string or not.
     */
    public boolean printAllIfExists(String stringToFind) {
        Objects.requireNonNull(stringToFind);

        String logRecords = findAll(stringToFind);

        if (logRecords.isEmpty()) {
            return false;
        } else {
            System.out.println(logRecords);
            return true;
        }
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
