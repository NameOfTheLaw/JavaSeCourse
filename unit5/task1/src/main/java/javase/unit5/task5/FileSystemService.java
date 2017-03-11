package javase.unit5.task5;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

/**
 * File system service for handling low level operations.
 */
public class FileSystemService {

    /**
     * Separator symbol for paths in concrete OS.
     */
    public static final String SEPARATOR = File.separator;

    /**
     * Creates a file.
     *
     * @param file file to be created.
     * @throws IOException if couldn't create a file.
     * @throws FileAlreadyExistsException if file is already exists in the file system.
     */
    public static void create(File file) throws IOException, FileAlreadyExistsException {
        Objects.requireNonNull(file);

        if (file.exists()) {
            throw new FileAlreadyExistsException(file.getName());
        } else {
            file.createNewFile();
        }
    }

    /**
     * Deletes a file.
     *
     * @param file file to be deleted.
     * @throws FileNotFoundException if file isn't exists in the file system.
     */
    public static void delete(File file) throws FileNotFoundException {
        Objects.requireNonNull(file);

        if (file.exists()) {
            file.delete();
        } else {
            throw new FileNotFoundException(file.getName());
        }
    }

    /**
     * Prints content into file.
     *
     * @param file file to print into.
     * @param content content to print in a file.
     * @throws IOException if couldn't write into file.
     * @throws FileNotFoundException if file isn't exists in the file system.
     */
    public static void echo(File file, String content) throws IOException, FileNotFoundException {
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
