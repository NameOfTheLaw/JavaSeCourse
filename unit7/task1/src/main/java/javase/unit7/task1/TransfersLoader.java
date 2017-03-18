package javase.unit7.task1;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public abstract class TransfersLoader {

    public TransfersLoader(Path path) throws FileNotFoundException, NotDirectoryException {
        Objects.requireNonNull(path);

        validatePath(path);
    }

    public abstract List<Transfer> get();

    protected void validatePath(Path path) throws FileNotFoundException, NotDirectoryException {
        if (!path.toFile().exists()) {
            throw new FileNotFoundException(
                    String.format("File %s was not found.", path.getFileName()));
        }

        if (path.toFile().isDirectory()) {
            throw new NotDirectoryException(
                    String.format("%s is a directory.", path.getFileName()));
        }
    }
}
