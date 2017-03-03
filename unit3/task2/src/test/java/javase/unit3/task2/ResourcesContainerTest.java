package javase.unit3.task2;

import org.junit.Test;

import java.util.*;

import static javase.unit3.task2.QuestionAndAnswer.*;

public class ResourcesContainerTest {

    @Test
    public void testOfUsage() {

        ResourcesContainer resourcesContainer = new ResourcesContainer(
                new ResourceBundleTesting(Locale.forLanguageTag("ru")));

        String question = resourcesContainer.getQuestion(1);
        String answer = resourcesContainer.getAnswer(1);
        List<String> questionsList = resourcesContainer.getAllQuestions();
        resourcesContainer.printAll();

        resourcesContainer = new ResourcesContainer(
                new ResourceBundleTesting(Locale.ENGLISH));

        question = resourcesContainer.getQuestion(2);
        answer = resourcesContainer.getAnswer(2);
        questionsList = resourcesContainer.getAllQuestions();
        resourcesContainer.printAll(System.out);
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
            } else {
                throw new UnsupportedOperationException("This test class has only En and Ru locales implementations");
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