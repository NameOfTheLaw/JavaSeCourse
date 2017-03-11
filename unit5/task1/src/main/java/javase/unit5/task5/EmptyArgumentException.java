package javase.unit5.task5;

/**
 * Exception for empty arguments in user commands.
 */
public class EmptyArgumentException extends RuntimeException {
    private final int index;

    /**
     * Constructor.
     *
     * @param i argument index.
     */
    public EmptyArgumentException(int i) {
        this.index = i;
    }

    @Override
    public String getMessage() {
        return String.format("Arguments can't be empty (contains only spaces). Argument %s is empty.", index);
    }
}
