package javase.unit5.task1;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileSystemControllerTest {

    FileSystemController controller;

    @Before
    public void setUp() throws DirectoryNotFoundException, NotDirectoryException {
        controller = new FileSystemController(".");
    }

    @Test
    public void testList() throws NotDirectoryException, DirectoryNotFoundException {
        controller.move("testFolder");
        List<File> filesAndDirs = controller.list();

        assertEquals(2, filesAndDirs.size());

        Set<String> expectedSet = Stream.of(new String[]{"testInnerFolder", "testFile"})
                .collect(Collectors.toSet());

        Set<String> resultedSet = filesAndDirs.stream()
                .map(File::getName)
                .collect(Collectors.toSet());

        assertEquals(expectedSet, resultedSet);
    }

    @Test
    public void testMove() throws NotDirectoryException, DirectoryNotFoundException {

        assertFileEndsWith(controller.location(), "task1");

        controller.move("testFolder");

        assertFileEndsWith(controller.location(), "task1\\testFolder");

    }

    @Test
    public void testMoveThrowFolder() throws NotDirectoryException, DirectoryNotFoundException {

        assertFileEndsWith(controller.location(), "task1");

        controller.move("testFolder\\testInnerFolder");

        assertFileEndsWith(controller.location(), "task1\\testFolder\\testInnerFolder");

    }

    @Test
    public void testMoveUpper() throws NotDirectoryException, DirectoryNotFoundException {

        assertFileEndsWith(controller.location(), "task1");

        controller.move("testFolder\\testInnerFolder");

        assertFileEndsWith(controller.location(), "task1\\testFolder\\testInnerFolder");

        controller.move("..\\..");

        assertFileEndsWith(controller.location(), "task1");

    }

    @Test(expected = NotDirectoryException.class)
    public void testMoveNotToDirectory() throws NotDirectoryException, DirectoryNotFoundException {

        assertFileEndsWith(controller.location(), "task1");

        controller.move("testFolder\\testFile");
    }

    @Test(expected = DirectoryNotFoundException.class)
    public void testMoveToNonExistingFolder() throws NotDirectoryException, DirectoryNotFoundException {

        assertFileEndsWith(controller.location(), "task1");

        controller.move("nonExistingFolder");
    }

    private void assertFileEndsWith(File file, String endSubstring) {
        try {
            assertTrue(file.getCanonicalPath().endsWith(endSubstring));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
