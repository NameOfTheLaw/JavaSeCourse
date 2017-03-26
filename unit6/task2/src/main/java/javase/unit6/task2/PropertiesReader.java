package javase.unit6.task2;

import java.io.*;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reader for properties files.
 */
public class PropertiesReader {

    private static Pattern propertyKeyValuePattern = Pattern.compile("([^=]+)=([^=]+)");

    private Properties properties;
    private String propertiesFileName;

    private PropertiesReader() {}

    /**
     * Returns a PropertiesReader of the file.
     *
     * @param propertiesPath path to the properties file.
     * @return PropertiesReader of the file.
     * @throws PropertiesNotFoundException if properties file isn't exists.
     * @throws NotReadablePropertiesException if properties file isn't readable;
     */
    public static PropertiesReader of(String propertiesPath) throws PropertiesNotFoundException, NotReadablePropertiesException {
        Objects.requireNonNull(propertiesPath);

        PropertiesReader propertiesReader = new PropertiesReader();

        File propertiesFile = getPropertiesFileOrDie(propertiesPath);

        propertiesReader.propertiesFileName = propertiesFile.getName();

        try {
            propertiesReader.properties = readPropertiesFromFile(propertiesFile);
        } catch (IOException e) {
            throw new NotReadablePropertiesException(e);
        }

        return propertiesReader;
    }

    /**
     * Returns a property value by the key.
     *
     * @param propertyKey key for property.
     * @return a property value.
     * @throws PropertyNotFoundException if where is no such a key in the property file.
     */
    public String get(String propertyKey) throws PropertyNotFoundException {
        Objects.requireNonNull(propertyKey);

        if (!properties.containsKey(propertyKey)) {
            throw new PropertyNotFoundException(propertyKey, propertiesFileName);
        }

        return properties.getProperty(propertyKey);
    }

    private static Properties readPropertiesFromFile(File propertiesFile) throws IOException {
        Properties properties = new Properties();

        try (BufferedReader reader = new BufferedReader(new FileReader(propertiesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = propertyKeyValuePattern.matcher(line);

                if (matcher.matches()) {
                    String key = matcher.group(1).trim();
                    String value = matcher.group(2).trim();
                    if (!key.isEmpty() && !value.isEmpty()) {
                        properties.put(key, value);
                    }
                }
            }
        }

        return properties;
    }

    private static File getPropertiesFileOrDie(String propertiesPath) throws PropertiesNotFoundException {
        File propertiesFile = new File(propertiesPath);

        if (!propertiesFile.exists()) {
            throw new PropertiesNotFoundException(propertiesFile.getName());
        }

        return propertiesFile;
    }
}
