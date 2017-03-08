package javase.unit4.task3;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class UTFTranslatorTest {

    @Test
    public void testTranslate() throws IOException {
        UTFTranslator translator = new UTFTranslator("utf-8.txt", "utf-16.txt");

        File inputFile = new File("utf-8.txt");
        File outputFile = new File("utf-16.txt");
        outputFile.delete();

        translator.translate();

        assertFileEquals(inputFile, outputFile);

    }

    @Test
    public void testTranslateEmptyFile() throws IOException {
        UTFTranslator translator = new UTFTranslator("empty_utf-8.txt", "empty_utf-16.txt");

        File inputFile = new File("empty_utf-8.txt");
        File outputFile = new File("empty_utf-16.txt");
        outputFile.delete();

        translator.translate();

        assertFileEquals(inputFile, outputFile);

    }

    @Test
    public void testTranslateWithCharsets() throws IOException {
        UTFTranslator translator = new UTFTranslator(
                "utf-8.txt", Charset.forName("utf-8"),
                "utf-16.txt", Charset.forName("utf-16"));

        File inputFile = new File("utf-8.txt");
        File outputFile = new File("utf-16.txt");
        outputFile.delete();

        translator.translate();

        assertFileEquals(inputFile, outputFile);

    }

    private void assertFileEquals(File inputFile, File outputFile) throws IOException {
        try (BufferedReader readerFromInputFile = new BufferedReader(new FileReader(inputFile));
             BufferedReader readerFromOutputFile = new BufferedReader(new FileReader(outputFile))) {

            String inputLine, outputLine;
            while ((inputLine = readerFromInputFile.readLine()) != null
                    & (outputLine = readerFromOutputFile.readLine()) != null) {
                assertEquals(inputLine, new String(outputLine.getBytes("UTF-16")));
            }

            assertNull(inputLine);
            assertNull(outputLine);
        }
    }
}
