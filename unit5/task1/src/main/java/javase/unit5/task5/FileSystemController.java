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

    public FileSystemController(String systemPath) throws DirectoryNotFoundException, NotDirectoryException {
        Objects.requireNonNull(systemPath);

        File destination = new File(systemPath);

        checkDirectoryOrDie(destination);
    }

    public void move(String folderPath) throws NotDirectoryException, DirectoryNotFoundException {
        Objects.requireNonNull(folderPath);

        File destination = new File(
                String.format(pathFormat,
                        currentDirectory.getAbsolutePath(),
                        folderPath
                )
        );

        checkDirectoryOrDie(destination);
    }

    public List<File> list() {
        return Arrays.stream(currentDirectory.list())
                .map(File::new)
                .collect(Collectors.toList());
    }

    public File location() {
        return currentDirectory;
    }

    private void checkDirectoryOrDie(File file) throws NotDirectoryException, DirectoryNotFoundException {
        if (file.exists()) {
            if (file.isDirectory()) {
                currentDirectory = file;
            } else {
                throw new NotDirectoryException(file.getName());
            }
        } else {
            throw new DirectoryNotFoundException();
        }
    }
}
