package javase.unit3.task3;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by andrey on 26.02.2017.
 */
public class RegExpSearherTest {

    @Test
    public void testOfUsage() {
        RegExpSearher searher = RegExpSearher.byFileName("article.html");
        assertFalse(searher.isLinksSequential());
        Arrays.stream(searher.getSentencesWithLinks())
                .forEach(System.out::println);
    }

    @Test
    public void testRecogniseLinkWithDot() {
        assertTrue(RegExpSearher.getPattern().mathcher("gfdgd Рис.1 ffdgdf").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("(Рис.1)").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("(рис.1)").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("Рис.1").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("рис.1").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("(Рис. 1)").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("(рис. 1)").matches());
    }

    @Test
    public void testRecogniseLinkWithFullWord() {
        assertTrue(RegExpSearher.getPattern().mathcher("Рисунке 1 ").matches());
        assertTrue(RegExpSearher.getPattern().mathcher(" рисунке 1 ").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("рисунок 1").matches());
    }

    @Test
    public void testRecogniseLinkWithDotAndFullWord() {
        assertTrue(RegExpSearher.getPattern().mathcher("рисунке 1.").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("рисунок 1.").matches());
        assertTrue(RegExpSearher.getPattern().mathcher(" Рисунке 1. ").matches());
    }

    @Test
    public void testRecogniseLinkWithDotAndSeveralNumbers() {
        assertTrue(RegExpSearher.getPattern().mathcher("gfdgd Рис.123 ffdgdf").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("(Рис.12)").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("(рис.122)").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("Рис.12").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("рис.133").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("(Рис. 13)").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("(рис. 133)").matches());
    }

    @Test
    public void testRecogniseLinkWithFullWordAndSeveralNumbers() {
        assertTrue(RegExpSearher.getPattern().mathcher("Рисунке 133 ").matches());
        assertTrue(RegExpSearher.getPattern().mathcher(" рисунке 12 ").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("рисунок 133").matches());
    }

    @Test
    public void testRecogniseLinkWithDotAndFullWord() {
        assertTrue(RegExpSearher.getPattern().mathcher("рисунке 12.").matches());
        assertTrue(RegExpSearher.getPattern().mathcher("рисунок 31.").matches());
        assertTrue(RegExpSearher.getPattern().mathcher(" Рисунке 144. ").matches());
    }

    @Test
    public void testIsLinksSequential() {
        RegExpSearher searher1 = RegExpSearher.from("(Рис.1) (Рис.2) (Рис.2) (Рис.3)");
        assertTrue(searher1.isLinksSequential());
        RegExpSearher searher2 = RegExpSearher.from("(Рис.1) (Рис.3) (Рис.2) (Рис.2)");
        assertFalse(searher2.isLinksSequential());
    }

    @Test
    public void testGetSentencesWithLinksIfThereIsNoLinks() {
        RegExpSearher searher = RegExpSearher.from("аааа. бббб. ввввв. гггг.");
        assertArrayEquals(new String[] {}, searher.getSentencesWithLinks());
    }

    @Test
    public void testGetSentencesWithLinks() {
        RegExpSearher searher = RegExpSearher.from("(Рис.1) аааа. бббб(Рис.2). ввввв. гггг(Рис.2).");
        assertArrayEquals(new String[] {"aaaa", "бббб", "гггг"}, searher.getSentencesWithLinks());

    }

    @Test
    public void testGetSentencesWithLinksIfThereIsNoLinks() {
        RegExpSearher searher = RegExpSearher.from("аааа. бббб. ввввв. гггг.");
        assertArrayEquals(new String[] {}, searher.getSentencesWithLinks());
    }
}