package javase.unit4.task1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class for maintaining with Java key words.
 *
 * Created by andrey on 08.03.2017.
 */
public class KeyWordsService {

    private Set<String> keyWordsSet;
    private static final String[] keyWordsArray = new String[] {
        "abstract", "continue", "for", "new", "switch",
        "assert", "default", "goto", "package", "synchronized",
        "boolean", "do", "if", "private", "this",
        "break", "double", "implements", "protected", "throw",
        "byte", "else", "import", "public", "throws",
        "case", "enum", "instanceof", "return", "transient",
        "catch", "extends", "int", "short", "try",
        "char", "final", "interface", "static", "void",
        "class", "finally", "long", "strictfp", "volatile",
        "const", "float", "native", "super", "while"
    };

    public KeyWordsService() {
        keyWordsSet = new HashSet<>(Arrays.asList(keyWordsArray));
    }

    /**
     * Checks if the given word is key word in Java or not.
     *
     * @param word
     * @return
     */
    public boolean isKeyWord(String word) {
        Objects.requireNonNull(word);

        return keyWordsSet.contains(word);
    }
}
