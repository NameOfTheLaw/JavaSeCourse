package javase.unit3.task3;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by andrey on 26.02.2017.
 */
public class RegExpSearcherTest {

    @Test
    public void testOfUsage() throws IOException {
        RegExpSearcher searher = RegExpSearcher.byFileNameAndEncoding("article.html", Charset.forName("windows-1251"));

        long sentencesCount = searher.getSentencesWithLinks().stream()
                .count();
        assertTrue(sentencesCount > 100);

        assertFalse(searher.isLinksSequential());
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
        RegExpSearcher searher1 = RegExpSearcher.from("(Рис.1) (Рис.2) (Рис.2) (Рис.3)");
        assertTrue(searher1.isLinksSequential());
        RegExpSearcher searher2 = RegExpSearcher.from("(Рис.1) (Рис.3) (Рис.2) (Рис.2)");
        assertFalse(searher2.isLinksSequential());
    }

    @Test
    public void testGetSentencesWithLinksIfThereIsNoLinks() throws IOException {
        RegExpSearcher searher = RegExpSearcher.from("аааа. бббб. ввввв. гггг.");
        assertArrayEquals(new String[] {}, searher.getSentencesWithLinks().toArray());
    }

    @Test
    public void testGetSentencesWithLinks() throws IOException {
        RegExpSearcher searher = RegExpSearcher.from("aaaaa (Рис.1) аааа. бббб(Рис.2). ввв ввввв. (Рис.3) гггг (Рис.4).");
        assertArrayEquals(new String[] {"aaaaa (Рис.1) аааа.", " бббб(Рис.2).", " (Рис.3) гггг (Рис.4)."}, searher.getSentencesWithLinks().toArray());
    }

}