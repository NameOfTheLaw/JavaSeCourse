package javase.unit4.task1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface KeyWordsSeeker {
    Set<String> getKeyWords();

    Map<String,Integer> getKeyWordsWithCount();

    void printInto(String fileName) throws IOException;
}
