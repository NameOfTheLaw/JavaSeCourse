package javase.unit5.task5;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by andrey on 11.03.2017.
 */
public class FileSystemControllerTest {

    FileSystemController controller;

    @Before
    public void setUp() {
        controller = new FileSystemController(".");
    }

    @Test
    public void testList() {
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
    public void testMove() {

        assertTrue(controller.location().endsWith("task1"));

        controller.move("testFolder");

        assertTrue(controller.location().endsWith("task1\\testFolder"));

    }

    @Test
    public void testMoveThrowFolder() {

        assertTrue(controller.location().endsWith("task1"));

        controller.move("testFolder\\testInnerFolder");

        assertTrue(controller.location().endsWith("task1\\testFolder\\testInnerFolder"));

    }

    @Test
    public void testMoveUpper() {

        assertTrue(controller.location().endsWith("task1"));

        controller.move("testFolder\\testInnerFolder");

        assertTrue(controller.location().endsWith("task1\\testFolder\\testInnerFolder"));

        controller.move("..\\..");

        assertTrue(controller.location().endsWith("task1"));

    }

    @Test(expected = NotDirectoryException.class)
    public void testMoveToFile() {

        assertTrue(controller.location().endsWith("task1"));

        controller.move("testFolder\\testFile");
    }

    @Test(expected = DirectoryNotFoundException.class)
    public void testMoveToNonExistingFolder() {

        assertTrue(controller.location().endsWith("task1"));

        controller.move("nonExistingFolder");
    }
}
