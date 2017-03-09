package javase.unit4.task1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface for searching Java key words.
 */
public interface KeyWordsSeeker {

    String OUTPUT_FORMAT = "%s %d%n";

    /**
     * Returns a set of all key words.
     *
     * @return set of key words.
     */
    Set<String> getKeyWords();

    /**
     * Returns all key words with their occurrences count.
     *
     * @return map of key word - occurrences count.
     */
    Map<String,Integer> getKeyWordsWithCount();

    /**
     * Print all key words with their occurrences count into file with given fileName.
     *
     * @param fileName name of the output file.
     * @throws IOException if output file is somehow broken.
     */
    void printInto(String fileName) throws IOException;

    /**
     * Finds all key words in the given string argument.
     *
     * @param text string to search key words in.
     * @return map of key word - occurrences count.
     */
    default Map<String, Integer> findKeyWords(String text) {
        return findKeyWords(text, new KeyWordsService());
    }

    /**
     * Finds all key words in the given string argument using given KeyWordsService.
     *
     * @param text string to search key words in.
     * @param keyWordsService service for determining java key words.
     * @return map of key word - occurrences count.
     */
    default Map<String, Integer> findKeyWords(String text, KeyWordsService keyWordsService) {
        Map<String, Integer> keyWords = new HashMap<>();

        Matcher matcher = Pattern.compile("\\w+").matcher(text);

        while (matcher.find()) {
            String foundWord = matcher.group();

            if (keyWordsService.isKeyWord(foundWord)) {
                if (keyWords.containsKey(foundWord)) {
                    Integer newCount = keyWords.get(foundWord) + 1;
                    keyWords.put(foundWord, newCount);
                } else {
                    keyWords.put(foundWord, 1);
                }
            }
        }

        return keyWords;
    }
}
