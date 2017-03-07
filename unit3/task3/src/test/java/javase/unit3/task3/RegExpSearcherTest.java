package javase.unit3.task3;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class RegExpSearcherTest {

    @Test(expected = NullPointerException.class)
    public void testMethodFromIfFileIsNull() throws IOException {
        RegExpSearcher.from(null, Charset.defaultCharset());
    }

    @Test(expected = NullPointerException.class)
    public void testMethodFromIfNullFile() throws IOException {
        RegExpSearcher.from(new File("test"), null);
    }

    @Test
    public void testMethodFromEmptyString() throws IOException {
        RegExpSearcher.from("");
    }

    @Test
    public void testRecogniseLinkWithDot() {
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("Рис.1").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("рис.1").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("Рис. 1").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("рис. 1").matches());
    }

    @Test
    public void testRecogniseLinkWithFullWord() {
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("рисунке 1").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("Рисунке 1").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("Рисунок 1").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("рисунок 1").matches());
    }

    @Test
    public void testRecogniseLinkWithDotAndSeveralNumbers() {
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("Рис.123").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("рис.123").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("Рис. 123").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("рис. 123").matches());
    }

    @Test
    public void testRecogniseLinkWithFullWordAndSeveralNumbers() {
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("Рисунке 123").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("рисунке 123").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("Рисунок 133").matches());
        assertTrue(RegExpSearcher.LINK_PATTERN.matcher("рисунок 133").matches());
    }

    @Test
    public void testIsLinksSequential() throws IOException {
        RegExpSearcher searcher1 = RegExpSearcher.from("А (Рис.1) (Рис.2) (Рис.2) (Рис.3).");
        assertTrue(searcher1.isLinksSequential());
        RegExpSearcher searcher2 = RegExpSearcher.from("А (Рис.1) (Рис.3) (Рис.2) (Рис.2).");
        assertFalse(searcher2.isLinksSequential());
    }

    @Test
    public void testHasLinks() throws IOException {
        RegExpSearcher searcher1 = RegExpSearcher.from("А (Рис.1) (Рис.2) (Рис.2) (Рис.3).");
        assertTrue(searcher1.hasLinks());
        RegExpSearcher searcher2 = RegExpSearcher.from("Аааа. Бббб. Ввввв. Гггг.");
        assertFalse(searcher2.hasLinks());
    }

    @Test
    public void testHasLinksIfSearchableObjectIsEmpty() throws IOException {
        RegExpSearcher searcher = RegExpSearcher.from("");
        assertFalse(searcher.hasLinks());
    }

    @Test
    public void testGetSentencesWithLinksIfThereIsNoLinks() throws IOException {
        RegExpSearcher searcher = RegExpSearcher.from("аааа. бббб. ввввв. гггг.");
        assertArrayEquals(new String[] {}, searcher.getSentencesWithLinks().toArray());
    }

    @Test
    public void testGetSentencesWithLinks() throws IOException {
        RegExpSearcher searcher = RegExpSearcher.from("Аaaaa (Рис.1) аааа. Бббб(Рис.2). Ввв ввввв. Г (Рис.3) гггг (Рис.4).");
        assertArrayEquals(new String[] {"Аaaaa (Рис.1) аааа.", "Бббб(Рис.2).", "Г (Рис.3) гггг (Рис.4)."}, searcher.getSentencesWithLinks().toArray());
    }

}