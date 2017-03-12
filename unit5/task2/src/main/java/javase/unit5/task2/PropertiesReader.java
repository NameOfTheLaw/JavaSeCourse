package javase.unit5.task2;

import java.io.*;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesReader {

    private static Pattern propertyKeyValuePattern = Pattern.compile("([^=]+)=([^=]+)");

    private Properties properties;
    private String propertiesFileName;

    private PropertiesReader() {}

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
