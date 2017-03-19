package javase.unit7.task2;

import java.io.IOException;

/**
 * Exception for not readable properties file.
 */
public class NotReadablePropertiesException extends IOException {

    public NotReadablePropertiesException(Throwable e) {
        super(e);
    }
}
