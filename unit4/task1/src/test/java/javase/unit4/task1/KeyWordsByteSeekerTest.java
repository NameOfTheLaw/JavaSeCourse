package javase.unit4.task1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by andrey on 08.03.2017.
 */
public class KeyWordsByteSeekerTest extends KeyWordsSeekerTest {

    @Override
    public void setUp() throws IOException {
        super.setUp();

        seeker = new KeyWordsByteSeeker(testFileName);

        outputFileName = "ByteSeekerOutput.txt";
    }

    public void testGetKeyWordsIfWhereIsNoKeyWords() throws IOException {
        KeyWordsSeeker seeker = new KeyWordsByteSeeker(emptyFileName);
        Set<String> keywords = seeker.getKeyWords();

        assertEquals(new HashSet<String>(), keywords);
    }
}
