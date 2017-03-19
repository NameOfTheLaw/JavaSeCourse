package javase.unit7.task2;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ParallelPropertiesReaderTest {

    private ParallelPropertiesReader reader;
    private Path propertiesPath = Paths.get("testData\\testProperties_en_EN.properties");

    @Before
    public void setUp() throws PropertiesNotFoundException, NotReadablePropertiesException {
        reader = ParallelPropertiesReader.of(propertiesPath);
    }

    @Test
    public void testCreate() throws PropertiesNotFoundException, NotReadablePropertiesException {
        reader = ParallelPropertiesReader.of(propertiesPath);
    }

    @Test(expected = PropertiesNotFoundException.class)
    public void testCreateOfNonExistingProperties() throws PropertiesNotFoundException, NotReadablePropertiesException {
        reader = ParallelPropertiesReader.of(Paths.get("nonexistingfilename.properties"));
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
        assertEquals("propcontent", reader.get("nonexistingprop"));
    }

}
