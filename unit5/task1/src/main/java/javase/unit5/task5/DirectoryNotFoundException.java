package javase.unit5.task5;

import java.io.FileNotFoundException;

/**
 * Exception for not found directory.
 */
public class DirectoryNotFoundException extends FileNotFoundException {

    private final String directoryName;

    DirectoryNotFoundException(String directoryName) {
        this.directoryName = directoryName;
    }

    @Override
    public String getMessage() {
        return directoryName;
    }
}
