package javase.unit4.task3;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;

/**
 * Created by andrey on 09.03.2017.
 */
public class UTF16Translator {

    private final static Charset defaultInputCharset = Charset.forName("utf-8");
    private final static Charset outputCharset = Charset.forName("utf-16");
    private Charset inputCharset;
    private File inputFile;

    public UTF16Translator(String inputFileName, Charset inputFileCharset) throws FileNotFoundException {
        this.inputFile = new File(inputFileName);
        checkInputFileExistsOrDie("Input file \"%s\" isn't exist.");

        this.inputCharset = inputFileCharset;
    }

    public UTF16Translator(String inputFileName) throws FileNotFoundException {
        this(inputFileName, defaultInputCharset);
    }

    public void translateTo(String outputFileName) throws IOException {
        checkInputFileExistsOrDie("Input file \"%s\" was removed.");

        File outputFile = new File(outputFileName);
        if (!outputFile.createNewFile()) {
            throw new FileAlreadyExistsException(String.format("Output file \"%s\" already exists.", outputFileName));
        }

        try(BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outputFile), outputCharset));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputFile), inputCharset))) {

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                if (reader.ready()) {
                    writer.write(String.format("%s%n", inputLine));
                } else {
                    writer.write(String.format("%s", inputLine));
                }

            }
        }
    }

    private void checkInputFileExistsOrDie(String messageFormat) throws FileNotFoundException {
        if (!inputFile.exists()) {
            throw new FileNotFoundException(String.format(messageFormat, inputFile.getName()));
        }
    }
}
