package javase.unit5.task2;

import org.junit.Before;
import org.junit.Test;

public class PropertiesReaderTest {

    private PropertiesReader reader;
    private String propertiesFile = "testProperties.properties";

    @Before
    public void setUp() {
        reader = PropertiesReader.of(propertiesFile);
    }

    @Test
    public void testCreate() {
        reader = PropertiesReader.of(propertiesFile);

        testGetMessage();
    }

    @Test(expected = PropertiesNotFoundException.class)
    public void testCreateOfNonExistingProperties() {
        reader = PropertiesReader.of("nonexistingfilename.properties");
    }

    @Test
    public void testGetMessage() {
        assertEquals("prop1content", reader.get("prop1"));
        assertEquals("prop2prop1content", reader.get("prop2.prop1"));
        assertEquals("prop2prop2content", reader.get("prop2.prop2"));
    }

    @Test(expected = PropertyNotFoundException.class)
    public void testGetMessageWithWrongKey() {
        assertEquals("prop1content", reader.get("nonexistingprop"));
    }

    @Test(expected = PropertyNotFoundException.class)
    public void testGetMessageWithWrongKey() {
        assertEquals("prop1content", reader.get("nonexistingprop"));
    }
}
