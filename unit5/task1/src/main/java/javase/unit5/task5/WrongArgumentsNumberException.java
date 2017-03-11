package javase.unit5.task5;

public class WrongArgumentsNumberException extends RuntimeException {

    private final int expectedNumber;
    private final int foundNumber;

    public WrongArgumentsNumberException(int expectedNumber, int foundNumber) {
        this.expectedNumber = expectedNumber;
        this.foundNumber = foundNumber;
    }

    @Override
    public String getMessage() {
        return String.format("Expected %d arguments, found %d.", expectedNumber, foundNumber);
    }
}
