package javase.unit4.task1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyWordsSymbolSeeker implements KeyWordsSeeker {

    private Map<String, Integer> keyWords;

    public KeyWordsSymbolSeeker(String fileName) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            keyWords = new HashMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                keyWords.putAll(findKeyWords(line));
            }
        }
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String keyWord: keyWords.keySet()) {
                String keyWordRecord = String.format(OUTPUT_FORMAT, keyWord, keyWords.get(keyWord));
                writer.write(keyWordRecord);
            }
        }
    }

    @Override
    public Map<String, Integer> findKeyWords(String text) {
        return findKeyWords(text, new KeyWordsService());
    }
}
