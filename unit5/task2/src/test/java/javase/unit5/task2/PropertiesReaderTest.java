package javase.unit5.task2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesReaderTest {

    private PropertiesReader reader;
    private String propertiesFile = "src\\main\\resources\\testProperties_en_EN.properties";

    @Before
    public void setUp() throws PropertiesNotFoundException, NotReadablePropertiesException {
        reader = PropertiesReader.of(propertiesFile);
    }

    @Test
    public void testCreate() throws PropertiesNotFoundException, NotReadablePropertiesException {
        reader = PropertiesReader.of(propertiesFile);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateOfNull() throws PropertiesNotFoundException, NotReadablePropertiesException {
        reader = PropertiesReader.of(null);
    }

    @Test(expected = PropertiesNotFoundException.class)
    public void testCreateOfNonExistingProperties() throws PropertiesNotFoundException, NotReadablePropertiesException {
        reader = PropertiesReader.of("nonexistingfilename.properties");
    }

    @Test
    public void testGet() throws PropertyNotFoundException {
        assertEquals("prop1content", reader.get("prop1"));
        assertEquals("prop2prop1content", reader.get("prop2.prop1"));
        assertEquals("prop2prop2content", reader.get("prop2.prop2"));
    }

    @Test(expected = NullPointerException.class)
    public void testGetOfNull() throws PropertyNotFoundException {
        reader.get(null);
    }

    @Test(expected = PropertyNotFoundException.class)
    public void testGetWithWrongKey() throws PropertyNotFoundException {
        assertEquals("prop1content", reader.get("nonexistingprop"));
    }

}
