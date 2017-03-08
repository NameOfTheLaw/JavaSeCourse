package javase.unit4.task1;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyWordsByteSeeker implements KeyWordsSeeker {

    private Map<String, Integer> keyWords;

    public KeyWordsByteSeeker(String fileName, Charset charset) throws IOException {
        byte[] byteArray;

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileName))) {
            byteArray = new byte[inputStream.available()];
            inputStream.read(byteArray);
        }

        keyWords = findKeyWords(new String(byteArray, charset));
    }

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

    @Override
    public Map<String, Integer> findKeyWords(String text) {
        return findKeyWords(text, new KeyWordsService());
    }
}
