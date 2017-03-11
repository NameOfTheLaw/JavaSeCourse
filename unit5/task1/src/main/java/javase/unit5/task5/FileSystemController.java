package javase.unit5.task5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileSystemController {

    private File currentDirectory;
    private static final String pathFormat = "%s" + FileSystemService.SEPARATOR + "%s";

    public FileSystemController(String folderPath) throws DirectoryNotFoundException, NotDirectoryException {
        Objects.requireNonNull(folderPath);

        File folder = new File(folderPath);

        checkDirectoryOrDie(folder);

        currentDirectory = folder;
    }

    public static FileSystemController fromCurrentDirectory() throws DirectoryNotFoundException, NotDirectoryException {
        return new FileSystemController(".");
    }

    public void move(String folderPath) throws NotDirectoryException, DirectoryNotFoundException {
        Objects.requireNonNull(folderPath);

        File destination = getFileFromRelativePath(folderPath);

        checkDirectoryOrDie(destination);

        currentDirectory = destination;
    }

    public List<File> list() {
        return Arrays.stream(currentDirectory.listFiles())
                .collect(Collectors.toList());
    }

    public File location() {
        return currentDirectory;
    }

    public void create(String filePath) throws IOException {
        FileSystemService.create(getFileFromRelativePath(filePath));
    }

    public void delete(String filePath) throws FileNotFoundException {
        FileSystemService.delete(getFileFromRelativePath(filePath));
    }

    public void echo(String filePath, String content) throws IOException {
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
