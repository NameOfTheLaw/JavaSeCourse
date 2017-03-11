package javase.unit5.task5;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class JavaFileSystemServiceTest {

    JavaFileSystemService service;

    @Before
    public void setUp() {
        service = new JavaFileSystemService(".");

    }

    @Test
    public void testCreate() {
        File file = new File("file.txt");

        deleteIfExists(file);

        assertFalse(file.exists());
        service.create(file);
        assertTrue(file.exists());
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void testCreateIfFileExists() {
        File file = new File("file.txt");

        deleteIfExists(file);

        assertFalse(file.exists());
        service.create(file);
        assertTrue(file.exists());
        service.create(file);
    }

    @Test
    public void testDelete() {
        File file = new File("file.txt");

        deleteIfExists(file);

        assertFalse(file.exists());
        service.create(file);
        assertTrue(file.exists());
        service.delete(file);
        assertFalse(file.exists());
    }

    @Test(expected = FileNotFoundException.class)
    public void testDeleteIfNonExists() {
        File file = new File("file.txt");

        deleteIfExists(file);

        assertFalse(file.exists());
        service.delete(file);
    }

    @Test
    public void testEcho() {
        File file = new File("file.txt");
        String content = "File content.";

        deleteIfExists(file);

        service.create(file);
        service.echo(file, content);

        assertFileEndsWithContent(content, file);
    }

    @Test
    public void testEchoInNewFile() {
        File file = new File("file.txt");
        String content = "File content.";

        deleteIfExists(file);

        service.create(file);
        service.echo(file, content);

        assertFileContent(content, file);
    }

    @Test(expected = FileNotFoundException.class)
    public void testEchoInNonExistingFile() {
        File file = new File("file.txt");
        String content = "File content.";

        deleteIfExists(file);

        service.echo(file, content);
    }

    @Test
    public void testList() {
        service.move("testFolder");
        List<File> filesAndDirs = service.list();

        assertEquals(2, filesAndDirs.size());

        Set<String> expectedSet = Stream.of(new String[] {"testInnerFolder", "testFile"})
                .collect(Collectors.toSet());

        Set<String> resultedSet = filesAndDirs.stream()
                .map(File::getName)
                .collect(Collectors.toSet());

        assertEquals(expectedSet, resultedSet);
    }

    @Test
    public void testMove() {

        assertTrue(service.location().endsWith("task1"));

        service.move("testFolder");

        assertTrue(service.location().endsWith("task1\\testFolder"));

    }

    @Test
    public void testMoveThrowFolder() {

        assertTrue(service.location().endsWith("task1"));

        service.move("testFolder\\testInnerFolder");

        assertTrue(service.location().endsWith("task1\\testFolder\\testInnerFolder"));

    }

    @Test
    public void testMoveUpper() {

        assertTrue(service.location().endsWith("task1"));

        service.move("testFolder\\testInnerFolder");

        assertTrue(service.location().endsWith("task1\\testFolder\\testInnerFolder"));

        service.move("..\\..");

        assertTrue(service.location().endsWith("task1"));

    }

    @Test(expected = NotDirectoryException.class)
    public void testMoveToFile() {

        assertTrue(service.location().endsWith("task1"));

        service.move("testFolder\\testFile");
    }

    @Test(expected = DirectoryNotFoundException.class)
    public void testMoveToNonExistingFolder() {

        assertTrue(service.location().endsWith("task1"));

        service.move("nonExistingFolder");
    }

    private void deleteIfExists(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    private void assertFileContent(String expectedContent, File file) {
        StringBuilder stringBuilder = getString(file);
        assertEquals(expectedContent, stringBuilder.toString());
    }

    private void assertFileEndsWithContent(String expectedContent, File file) {
        StringBuilder stringBuilder = getString(file);
        assertTrue(stringBuilder.toString().endsWith(expectedContent));
    }

    private StringBuilder getString(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.next());
            }
        } catch (Exception e) {
            throw new AssertionError();
        }
        return stringBuilder;
    }
}
