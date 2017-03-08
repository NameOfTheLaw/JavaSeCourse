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

    KeyWordsService keyWordsService = new KeyWordsService();
    BufferedInputStream inputStream;
    Map<String, Integer> keyWords;

    public KeyWordsByteSeeker(String fileName, Charset charset) throws IOException {
        inputStream = new BufferedInputStream(new FileInputStream(fileName));

        byte[] byteArray = new byte[inputStream.available()];
        inputStream.read(byteArray);

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
                String keyWordRecord = String.format("%s %d%n", keyWord, keyWords.get(keyWord));
                outputStream.write(keyWordRecord.getBytes());
            }
        }
    }

    private Map<String, Integer> findKeyWords(String text) {
        Map<String, Integer> keyWords = new HashMap<>();

        Matcher matcher = Pattern.compile("\\w+").matcher(text);

        while (matcher.find()) {
            String findedWord = matcher.group();

            if (keyWordsService.isKeyWord(findedWord)) {
                if (keyWords.containsKey(findedWord)) {
                    Integer newCount = keyWords.get(findedWord) + 1;
                    keyWords.put(findedWord, newCount);
                } else {
                    keyWords.put(findedWord, 1);
                }
            }
        }

        return keyWords;
    }
}
