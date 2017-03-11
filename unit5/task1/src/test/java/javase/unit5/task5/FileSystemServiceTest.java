package javase.unit5.task5;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

import static org.junit.Assert.*;

public class FileSystemServiceTest {

    FileSystemService service;
    private File file;

    @Before
    public void setUp() {
        file = new File("testFile.txt");
        service = new FileSystemService();
    }

    @After
    public void tearDown() throws Exception {
        deleteIfExists(file);
    }

    @Test
    public void testCreate() throws IOException {

        deleteIfExists(file);

        assertFalse(file.exists());
        service.create(file);
        assertTrue(file.exists());
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void testCreateIfFileExists() throws IOException {

        deleteIfExists(file);

        assertFalse(file.exists());
        service.create(file);
        assertTrue(file.exists());
        service.create(file);
    }

    @Test
    public void testDelete() throws IOException {

        deleteIfExists(file);

        assertFalse(file.exists());
        service.create(file);
        assertTrue(file.exists());
        service.delete(file);
        assertFalse(file.exists());
    }

    @Test(expected = FileNotFoundException.class)
    public void testDeleteIfNonExists() throws FileNotFoundException {

        deleteIfExists(file);

        assertFalse(file.exists());
        service.delete(file);
    }

    @Test
    public void testEcho() throws IOException {
        String content = "File content.";

        deleteIfExists(file);

        service.create(file);
        service.echo(file, content);

        assertFileEndsWithContent(content, file);
    }

    @Test
    public void testEchoInNewFile() throws IOException {
        String content = "File content.";

        deleteIfExists(file);

        service.create(file);
        service.echo(file, content);

        assertFileContent(content, file);
    }

    @Test(expected = FileNotFoundException.class)
    public void testEchoInNonExistingFile() throws IOException {
        String content = "File content.";

        deleteIfExists(file);

        service.echo(file, content);
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
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                if (reader.ready()) stringBuilder.append("\n");
            }
        } catch (Exception e) {
            throw new AssertionError();
        }
        return stringBuilder;
    }


}
