package javase.unit3.task1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andrey on 25.02.2017.
 */
public class CrazyLoggerTest {

    private CrazyLogger logger;

    @Before
    public void before() {
        logger = new CrazyLogger();
    }

    @Test
    public void testLog() {
        logger.log("first message");
        logger.log("second message");
    }

    @Test(expected = NullPointerException.class)
    public void testLogIfNull() {
        logger.log(null);
    }

    @Test
    public void testLogIfEmpty() {
        logger.log("");
        String resultLog = logger.getLog();
        assertTrue(resultLog.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLogIfArgHasSeparator() {
        logger.log("option1; option2");
    }

    @Test
    public void testFindFirstIfWhereIsNoSubstringInLog() {
        String searchResult = logger.findFirst("abba");
        assertTrue(searchResult.isEmpty());
    }

    @Test
    public void testFindFirst() {
        logger.log("abba1");
        logger.log("abba2");
        String searchResult = logger.findFirst("abba");
        assertEquals(searchResult.indexOf("abba"), searchResult.lastIndexOf("abba"));
    }

    @Test
    public void testFindLastIfWhereIsNoSubstringInLog() {
        String searchResult = logger.findLast("abba");
        assertTrue(searchResult.isEmpty());
    }

    @Test
    public void testFindLast() {
        logger.log("abba1");
        logger.log("abba2");
        String searchResult = logger.findLast("abba");
        assertEquals(searchResult.indexOf("abba"), searchResult.lastIndexOf("abba"));
    }

    @Test
    public void testFindAllIfWhereIsNoSubstringInLog() {
        String searchResult = logger.findAll("abba");
        assertTrue(searchResult.isEmpty());
    }

    @Test
    public void testFindAll() {
        logger.log("abba1");
        logger.log("abba2");
        String searchResult = logger.findAll("abba");
        assertNotEquals(searchResult.indexOf("abba"), searchResult.lastIndexOf("abba"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindFirstIfArgHasSeparator() {
        logger.findFirst("option1; option2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindLastIfArgHasSeparator() {
        logger.findLast("option1; option2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAllIfArgHasSeparator() {
        logger.findAll("option1; option2");
    }

    @Test(expected = NullPointerException.class)
    public void testFindFirstIfArgIsNull() {
        logger.findFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void testFindLastIfArgIsNull() {
        logger.findLast(null);
    }

    @Test(expected = NullPointerException.class)
    public void testFindAllIfArgIsNull() {
        logger.findAll(null);
    }

    @Test
    public void testPrintFirstReturns() {
        logger.log("mes1");
        assertTrue(logger.printFirstIfExists("mes1"));
        assertFalse(logger.printFirstIfExists("mes2"));
    }

    @Test
    public void testPrintLastReturns() {
        logger.log("mes1");
        assertTrue(logger.printLastIfExists("mes1"));
        assertFalse(logger.printLastIfExists("mes2"));
    }

    @Test
    public void testPrintAllReturns() {
        logger.log("mes1");
        assertTrue(logger.printAllIfExists("mes1"));
        assertFalse(logger.printAllIfExists("mes2"));
    }


}