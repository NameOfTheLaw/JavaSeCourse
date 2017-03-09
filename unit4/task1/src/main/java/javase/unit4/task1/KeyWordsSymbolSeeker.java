package javase.unit4.task1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the {@link KeyWordsSeeker}.
 *
 * Uses only symbols io streams ({@link InputStream}/{@link OutputStream})
 * for working with files.
 */
public class KeyWordsSymbolSeeker implements KeyWordsSeeker {

    private Map<String, Integer> keyWords;

    /**
     * Constructor.
     *
     * @param fileName name of the file to find key words in.
     * @throws IOException if input file are somehow broken.
     */
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
}
