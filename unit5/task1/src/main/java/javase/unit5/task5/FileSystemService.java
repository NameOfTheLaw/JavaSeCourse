package javase.unit5.task5;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

public class FileSystemService {

    public final static String SEPARATOR = File.separator;

    public void create(File file) throws IOException {
        Objects.requireNonNull(file);

        if (file.exists()) {
            throw new FileAlreadyExistsException(file.getName());
        } else {
            file.createNewFile();
        }
    }

    public void delete(File file) throws FileNotFoundException {
        Objects.requireNonNull(file);

        if (file.exists()) {
            file.delete();
        } else {
            throw new FileNotFoundException(file.getName());
        }
    }

    public void echo(File file, String content) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(content);

        if (!file.exists()) {
            throw new FileNotFoundException();
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            }
        }
    }
}
