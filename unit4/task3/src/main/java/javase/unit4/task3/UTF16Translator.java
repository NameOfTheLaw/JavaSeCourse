package javase.unit4.task3;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;

/**
 * Translator for file from any charset to UTF-16.
 *
 * Default charset for input file is {@link #defaultInputCharset}.
 * Created by andrey on 09.03.2017.
 */
public class UTF16Translator {

    private final static Charset defaultInputCharset = Charset.forName("utf-8");
    private final static Charset outputCharset = Charset.forName("utf-16");
    private Charset inputCharset;
    private File inputFile;

    /**
     * Constructor.
     *
     * @param inputFileName name of the input file to translate from.
     * @param inputFileCharset charset for input file.
     * @throws FileNotFoundException
     */
    public UTF16Translator(String inputFileName, Charset inputFileCharset) throws FileNotFoundException {
        this.inputFile = new File(inputFileName);
        checkInputFileExistsOrDie("Input file \"%s\" isn't exist.");

        this.inputCharset = inputFileCharset;
    }

    /**
     * Constructor.
     *
     * Uses {@link #defaultInputCharset}.
     * @param inputFileName name of the input file to translate from.
     * @throws FileNotFoundException
     */
    public UTF16Translator(String inputFileName) throws FileNotFoundException {
        this(inputFileName, defaultInputCharset);
    }

    /**
     * Translate data from input file to output file.
     *
     * Encoding of the output file will be UTF-16.
     * @param outputFileName name of the output file to translate to.
     * @throws IOException if input or output files are somehow broken.
     */
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
