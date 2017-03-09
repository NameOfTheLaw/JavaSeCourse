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
        if (!inputFile.exists()) {
            throw new FileNotFoundException(String.format("Input file \"%s\" isn't exist.", inputFileName));
        }

        this.inputCharset = inputFileCharset;
    }

    public UTF16Translator(String inputFileName) throws FileNotFoundException {
        this(inputFileName, defaultInputCharset);
    }

    public void translateTo(String outputFileName) throws IOException {
        if (!inputFile.exists()) {
            throw new FileNotFoundException(String.format("Input file \"%s\" was removed.", inputFile.getName()));
        }

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
                String outputLine = new String(inputLine.getBytes(outputCharset), outputCharset);
                if (reader.ready()) {
                    writer.write(String.format("%s%n", outputLine));
                } else {
                    writer.write(String.format("%s", outputLine));
                }

            }
        }
    }
}
