package javase.unit5.task5;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

public class FileSystemService {

    public static final String SEPARATOR = File.separator;

    public static void create(File file) throws IOException {
        Objects.requireNonNull(file);

        if (file.exists()) {
            throw new FileAlreadyExistsException(file.getName());
        } else {
            file.createNewFile();
        }
    }

    public static void delete(File file) throws FileNotFoundException {
        Objects.requireNonNull(file);

        if (file.exists()) {
            file.delete();
        } else {
            throw new FileNotFoundException(file.getName());
        }
    }

    public static void echo(File file, String content) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(content);

        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            }
        }
    }
}
