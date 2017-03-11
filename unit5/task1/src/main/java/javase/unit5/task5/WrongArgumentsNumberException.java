package javase.unit5.task5;

public class WrongArgumentsNumberException extends RuntimeException {

    private static final String format = "Expected %d arguments, found %d.";
    private final int expectedNumber;
    private final int foundNumber;

    public WrongArgumentsNumberException(int expectedNumber, int foundNumber) {
        this.expectedNumber = expectedNumber;
        this.foundNumber = expectedNumber;
    }

    @Override
    public String getMessage() {
        return String.format(format, expectedNumber, foundNumber);
    }
}
