package javase.unit7.task1;

/**
 * Exception for bad format of input transfer.
 */
public class BadTransferFormatException extends RuntimeException {
    public BadTransferFormatException(String s) {
        super(s);
    }
}
