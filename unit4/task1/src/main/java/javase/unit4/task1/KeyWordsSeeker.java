package javase.unit4.task1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface KeyWordsSeeker {

    String OUTPUT_FORMAT = "%s %d%n";

    Set<String> getKeyWords();

    Map<String,Integer> getKeyWordsWithCount();

    void printInto(String fileName) throws IOException;

    Map<String, Integer> findKeyWords(String text);

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
