package javase.unit4.task3;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class UTF16TranslatorTest {

    @Test
    public void testTranslate() throws IOException {
        UTF16Translator translator = new UTF16Translator("utf-8.txt");

        File inputFile = new File("utf-8.txt");
        File outputFile = new File("utf-16.txt");
        outputFile.delete();

        translator.translateTo("utf-16.txt");

        assertFileEquals(inputFile, outputFile);

    }

    @Test
    public void testTranslateEmptyFile() throws IOException {
        UTF16Translator translator = new UTF16Translator("empty_utf-8.txt");

        File inputFile = new File("empty_utf-8.txt");
        File outputFile = new File("empty_utf-16.txt");
        outputFile.delete();

        translator.translateTo("empty_utf-16.txt");

        assertFileEquals(inputFile, outputFile);

    }

    @Test
    public void testTranslateWithCharset() throws IOException {
        UTF16Translator translator = new UTF16Translator("windows-1251.txt", Charset.forName("windows-1251"));

        File inputFile = new File("windows-1251.txt");
        File outputFile = new File("utf-16.txt");
        outputFile.delete();

        translator.translateTo("utf-16.txt");

        assertFileEquals(inputFile, Charset.forName("windows-1251"), outputFile);

    }

    private void assertFileEquals(File inputFile, Charset inputCharset, File outputFile) throws IOException {
        try (BufferedReader readerFromInputFile = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputFile), inputCharset));
             BufferedReader readerFromOutputFile = new BufferedReader(
                    new InputStreamReader(new FileInputStream(outputFile), "utf-16"))) {

            String inputLine, outputLine;
            while ((inputLine = readerFromInputFile.readLine()) != null
                    & (outputLine = readerFromOutputFile.readLine()) != null) {
                assertEquals(inputLine, outputLine);
            }

            assertNull(inputLine);
            assertNull(outputLine);
        }
    }

    private void assertFileEquals(File inputFile, File outputFile) throws IOException {
        assertFileEquals(inputFile, Charset.forName("utf-8"), outputFile);
    }
}
