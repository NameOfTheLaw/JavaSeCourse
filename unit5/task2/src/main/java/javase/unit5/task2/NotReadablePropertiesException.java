package javase.unit5.task2;

import java.io.IOException;

public class NotReadablePropertiesException extends IOException {
    public NotReadablePropertiesException(IOException e) {
        super(e);
    }
}
