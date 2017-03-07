package javase.unit3.task2;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import static javase.unit3.task2.QuestionAndAnswer.*;
import static org.junit.Assert.*;

public class ResourcesServiceTest {

    ResourcesService resourcesService;

    @Before
    public void before() {
        resourcesService = new ResourcesService(
                new ResourceBundleTesting(Locale.forLanguageTag("en")));
    }

    @Test
    public void testCreatingFromResourceBundle() {
        ResourcesService resourcesService = new ResourcesService(
                new ResourceBundleTesting(Locale.ENGLISH));

        assertEquals("a1", resourcesService.getAnswer(1));
        assertEquals("q1", resourcesService.getQuestion(1));

        resourcesService = new ResourcesService(
                new ResourceBundleTesting(Locale.forLanguageTag("ru")));

        assertEquals("отв1", resourcesService.getAnswer(1));
        assertEquals("воп1", resourcesService.getQuestion(1));
    }

    @Test
    public void testGetQuestion() {
        assertEquals("q1", resourcesService.getQuestion(1));
        assertEquals("q2", resourcesService.getQuestion(2));
        assertEquals("q3", resourcesService.getQuestion(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetQuestionOutOfBoundLowBorder() {
        resourcesService.getQuestion(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetQuestionOutOfBoundHighBorder() {
        resourcesService.getQuestion(4);
    }

    @Test
    public void testGetAnswer() {
        assertEquals("a1", resourcesService.getAnswer(1));
        assertEquals("a2", resourcesService.getAnswer(2));
        assertEquals("a3", resourcesService.getAnswer(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAnswerOutOfBoundLowBorder() {
        resourcesService.getAnswer(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAnswerOutOfBoundHighBorder() {
        resourcesService.getAnswer(4);
    }

    @Test
    public void testGetAllQuestions() {
        List<String> allQuestions = resourcesService.getAllQuestions();

        assertArrayEquals(new String[] {"q1", "q2", "q3"}, allQuestions.stream().toArray());
    }

    @Test
    public void testGetAllQuestionsIfWhereIsNoQuestions() {
        ResourcesService resourcesService = new ResourcesService(new ResourceBundleTesting(Locale.FRANCE));
        List<String> allQuestions = resourcesService.getAllQuestions();

        assertArrayEquals(new String[] {}, allQuestions.stream().toArray());
    }

    @Test(timeout = 2000L)
    public void testPrintAllQuestions() throws IOException, NoSuchFieldException, IllegalAccessException {
        ResourcesService resourcesService = new ResourcesService(new ResourceBundleTesting(Locale.ENGLISH));

        PipedInputStream inputStream = new PipedInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        PipedOutputStream outputStream = new PipedOutputStream(inputStream);
        PrintStream printWriter = new PrintStream(outputStream);

        resourcesService.printAllQuestions(printWriter);

        String[] expectedMessages = new String[] {"q1", "q2", "q3"};
        int i = 0;

        String outputMessage = bufferedReader.readLine(); //ignore first output
        while (bufferedReader.ready() && (outputMessage = bufferedReader.readLine()) != null) {
            Field formatField = ResourcesService.class.getDeclaredField("questionInListFormat");
            formatField.setAccessible(true);
            String outputFormat = (String) formatField.get(null);

            assertEquals(
                String.format(
                    outputFormat,
                    i + 1,
                    expectedMessages[i++]),
                outputMessage
            );
        }
    }

    @Test(timeout = 2000L)
    public void testPrintAllQuestionsIfWhereIsNoQuestions() throws IOException {
        ResourcesService resourcesService = new ResourcesService(new ResourceBundleTesting(Locale.FRANCE));

        PipedInputStream pipedInputStream = new PipedInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pipedInputStream));

        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        PrintStream printWriter = new PrintStream(pipedOutputStream);

        resourcesService.printAllQuestions(printWriter);

        String outputMessage = bufferedReader.readLine(); //ignore first output
        assertNull(bufferedReader.ready() ? bufferedReader.readLine() : null);
    }

    private class ResourceBundleTesting extends ResourceBundle {

        Map<String, String> properties = new HashMap<>();

        public ResourceBundleTesting(Locale locale) {
            if (locale.equals(Locale.ENGLISH)) {
                properties.put(formatQuestionKey(1), "q1");
                properties.put(formatAnswerKey(1), "a1");
                properties.put(formatQuestionKey(2), "q2");
                properties.put(formatAnswerKey(2), "a2");
                properties.put(formatQuestionKey(3), "q3");
                properties.put(formatAnswerKey(3), "a3");
            } else if (locale.equals(Locale.forLanguageTag("ru"))) {
                properties.put(formatQuestionKey(1), "воп1");
                properties.put(formatAnswerKey(1), "отв1");
                properties.put(formatQuestionKey(2), "воп2");
                properties.put(formatAnswerKey(2), "отв2");
                properties.put(formatQuestionKey(3), "воп3");
                properties.put(formatAnswerKey(3), "отв3");
            }
            properties.put("error.input", "");
            properties.put("error.outofindex", "");
            properties.put("choose.locale", "");
            properties.put("questions.intro", "");
        }

        @Override
        protected Object handleGetObject(String s) {
            return properties.get(s);
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.enumeration(properties.keySet());
        }
    }
}