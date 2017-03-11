package javase.unit5.task5;

import java.io.File;
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

    public void move(String folderPath) throws NotDirectoryException, DirectoryNotFoundException {
        Objects.requireNonNull(folderPath);

        File destination = getDestination(folderPath);

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

    private void checkDirectoryOrDie(File file) throws NotDirectoryException, DirectoryNotFoundException {
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new NotDirectoryException(file.getName());
            }
        } else {
            throw new DirectoryNotFoundException();
        }
    }

    private File getDestination(String filePath) {
        return new File(
                String.format(pathFormat,
                        currentDirectory.getAbsolutePath(),
                        filePath
                )
        );
    }
}
