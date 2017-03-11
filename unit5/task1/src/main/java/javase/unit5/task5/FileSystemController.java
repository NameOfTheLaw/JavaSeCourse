package javase.unit5.task5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for managing with file system.
 *
 * Uses {@link FileSystemService} for handling with file system directly.
 */
public class FileSystemController {

    private File currentDirectory;
    private static final String pathFormat = "%s" + FileSystemService.SEPARATOR + "%s";

    /**
     * Constructor.
     *
     * @param folderPath relative path of the folder to start controller from.
     * @throws DirectoryNotFoundException if directory by an absolute path
     * (controller location + given path) isn't exist.
     * @throws NotDirectoryException if the file by the given path isn't a directory.
     */
    public FileSystemController(String folderPath) throws DirectoryNotFoundException, NotDirectoryException {
        Objects.requireNonNull(folderPath);

        File folder = new File(folderPath);

        checkDirectoryOrDie(folder);

        currentDirectory = folder;
    }

    /**
     * Returns a controller with current folder as location.
     *
     * @return
     * @throws DirectoryNotFoundException if directory by an absolute path
     * (controller location + given path) isn't exist.
     * @throws NotDirectoryException if the file by the given path isn't a directory.
     */
    public static FileSystemController fromCurrentDirectory() throws DirectoryNotFoundException, NotDirectoryException {
        return new FileSystemController(".");
    }

    /**
     * Change controller directory to the given.
     *
     * @param folderPath relative path of the folder to move controller to.
     * @throws DirectoryNotFoundException if directory by an absolute path
     * (controller location + given path) isn't exist.
     * @throws NotDirectoryException if the file by the given path isn't a directory.
     */
    public void move(String folderPath) throws DirectoryNotFoundException , NotDirectoryException {
        Objects.requireNonNull(folderPath);

        File destination = getFileFromRelativePath(folderPath);

        checkDirectoryOrDie(destination);

        currentDirectory = destination;
    }

    /**
     * Returns a list of files in the current folder.
     *
     * @return
     */
    public List<File> list() {
        return Arrays.stream(currentDirectory.listFiles())
                .collect(Collectors.toList());
    }

    /**
     * Returns a file of the current folder.
     *
     * @return
     */
    public File location() {
        return currentDirectory;
    }

    /**
     * Creates a file with the given path.
     *
     * @param filePath relative path of the file to create.
     * @throws IOException if can't create a file with a given path.
     * @throws FileAlreadyExistsException if a file with a given path is already exist.
     */
    public void create(String filePath) throws IOException, FileAlreadyExistsException {
        FileSystemService.create(getFileFromRelativePath(filePath));
    }

    /**
     * Deletes a file with the given path.
     *
     * @param filePath relative path of the file to delete.
     * @throws FileNotFoundException if file with a given path isn't exist.
     */
    public void delete(String filePath) throws FileNotFoundException {
        FileSystemService.delete(getFileFromRelativePath(filePath));
    }

    /**
     * Prints a given content into a file with a given path.
     *
     * @param filePath relative path of the file to echo into.
     * @param content a text to be echoed into file.
     * @throws IOException if can't write to a file with a given path.
     * @throws FileNotFoundException if file with a given path isn't exist.
     */
    public void echo(String filePath, String content) throws IOException, FileNotFoundException {
        FileSystemService.echo(getFileFromRelativePath(filePath), content);
    }

    private void checkDirectoryOrDie(File file) throws NotDirectoryException, DirectoryNotFoundException {
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new NotDirectoryException(file.getName());
            }
        } else {
            throw new DirectoryNotFoundException(file.getName());
        }
    }

    private File getFileFromRelativePath(String filePath) {
        return new File(
                String.format(pathFormat,
                        currentDirectory.getAbsolutePath(),
                        filePath
                )
        );
    }
}
