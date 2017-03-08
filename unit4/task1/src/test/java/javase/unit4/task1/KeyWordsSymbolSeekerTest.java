package javase.unit4.task1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class KeyWordsSymbolSeekerTest extends KeyWordsSeekerTest {

    @Override
    public void setUp() throws IOException {
        super.setUp();

        seeker = new KeyWordsSymbolSeeker(testFileName);

        outputFileName = "SymbolSeekerOutput.txt";
    }

    @Override
    public void testGetKeyWordsIfWhereIsNoKeyWords() throws IOException {
        KeyWordsSeeker seeker = new KeyWordsSymbolSeeker(emptyFileName);
        Set<String> keywords = seeker.getKeyWords();

        assertEquals(new HashSet<String>(), keywords);
    }
}
