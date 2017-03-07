package javase.unit4.task1;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by andrey on 08.03.2017.
 */
public class KeyWordsSymbolSeekerTest extends KeyWordsSeekerTest {

    @Override
    public void setUp() {
        super.setUp();

        outputFileName = "SymbolSeekerOutput.txt";
    }

    @Override
    public void testGetAllKeyWordsIfWhereIsNoKeyWords() {
        Set<String> keywords = new KeyWordsSymbolSeeker(emptyFileName);

        assertEquals(new HashSet<String>(), keywords);
    }
}
