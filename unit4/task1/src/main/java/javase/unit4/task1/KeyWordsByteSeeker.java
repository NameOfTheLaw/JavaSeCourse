package javase.unit4.task1;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of the {@link KeyWordsSeeker}.
 *
 * Uses only byte io streams ({@link Reader}/{@link Writer})
 * for working with files.
 */
public class KeyWordsByteSeeker implements KeyWordsSeeker {

    private Map<String, Integer> keyWords;

    /**
     * Constructor.
     *
     * @param fileName name of the file to find key words in.
     * @param charset charset of the input file.
     * @throws IOException if input file are somehow broken.
     */
    public KeyWordsByteSeeker(String fileName, Charset charset) throws IOException {
        byte[] byteArray;

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileName))) {
            byteArray = new byte[inputStream.available()];
            inputStream.read(byteArray);
        }

        keyWords = findKeyWords(new String(byteArray, charset));
    }

    /**
     * Constructor.
     *
     * @param fileName name of the file to find key words in.
     * @throws IOException if input file are somehow broken.
     */
    public KeyWordsByteSeeker (String fileName) throws IOException {
        this(fileName, Charset.defaultCharset());
    }

    @Override
    public Set<String> getKeyWords() {
        return keyWords.keySet();
    }

    @Override
    public Map<String, Integer> getKeyWordsWithCount() {
        return new HashMap<>(keyWords);
    }

    @Override
    public void printInto(String fileName) throws IOException {
        try (BufferedOutputStream outputStream =
                     new BufferedOutputStream(new FileOutputStream(fileName))) {
            for (String keyWord: keyWords.keySet()) {
                String keyWordRecord = String.format(OUTPUT_FORMAT, keyWord, keyWords.get(keyWord));
                outputStream.write(keyWordRecord.getBytes());
            }
        }
    }
}
