package javase.unit5.task5;

/**
 * Exception for wrong arguments number in user commands.
 */
public class WrongArgumentsNumberException extends RuntimeException {

    private final int expectedNumber;
    private final int foundNumber;

    /**
     * Constructor.
     *
     * @param expectedNumber expected arguments number.
     * @param foundNumber found arguments number.
     */
    public WrongArgumentsNumberException(int expectedNumber, int foundNumber) {
        this.expectedNumber = expectedNumber;
        this.foundNumber = foundNumber;
    }

    @Override
    public String getMessage() {
        return String.format("Expected %d arguments, found %d.", expectedNumber, foundNumber);
    }
}
