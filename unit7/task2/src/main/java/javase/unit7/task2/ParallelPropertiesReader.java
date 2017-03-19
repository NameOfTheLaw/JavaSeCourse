package javase.unit7.task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reader for properties files.
 */
public class ParallelPropertiesReader {

    private static Pattern propertyKeyValuePattern = Pattern.compile("([^=]+)=([^=]+)");
    private static PropertiesContainer container = new PropertiesContainer();

    private Properties properties;
    private Path propertiesPath;

    private ParallelPropertiesReader() {
    }

    /**
     * Returns a PropertiesReader of the file.
     *
     * @param propertiesPath path to the properties file.
     * @return PropertiesReader of the file.
     * @throws PropertiesNotFoundException    if properties file isn't exists.
     * @throws NotReadablePropertiesException if properties file isn't readable;
     */
    public static ParallelPropertiesReader of(String propertiesPath) throws PropertiesNotFoundException, IOException {
        Objects.requireNonNull(propertiesPath);

        return of(Paths.get(propertiesPath));
    }

    /**
     * Returns a PropertiesReader of the file.
     *
     * @param path path of the properties file.
     * @return PropertiesReader of the file path.
     * @throws PropertiesNotFoundException    if properties file isn't exists.
     * @throws NotReadablePropertiesException if properties file isn't readable;
     */
    public static ParallelPropertiesReader of(Path path) throws PropertiesNotFoundException, IOException {
        Objects.requireNonNull(path);

        validatePath(path);

        ParallelPropertiesReader parallelPropertiesReader = new ParallelPropertiesReader();
        parallelPropertiesReader.propertiesPath = path;

        Properties properties;
        boolean isLoaded = false;
        synchronized (container) {
            if (container.isLoading(path)) {
                properties = container.getLoadingProperties(path);
                try {
                    synchronized (properties) {
                        properties.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isLoaded = true;
            } else if (container.isLoaded(path)) {
                properties = container.getProperties(path);
                isLoaded = true;
            } else {
                properties = new Properties();
                container.addLoading(path, properties);
            }
        }

        if (!isLoaded) {
            loadProperties(path, properties);
            container.setLoaded(path, properties);
            synchronized (properties) {
                properties.notifyAll();
            }
        }

        parallelPropertiesReader.properties = properties;

        return parallelPropertiesReader;
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
            throw new PropertyNotFoundException(propertyKey, propertiesPath.toString());
        }

        return properties.getProperty(propertyKey);
    }

    private static void loadProperties(Path path, Properties properties) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
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
    }

    private static void validatePath(Path path) throws PropertiesNotFoundException {
        if (!path.toFile().exists()) {
            throw new PropertiesNotFoundException(path.getFileName().toString());
        }
    }

    private static File getPropertiesFile(String propertiesPath) throws PropertiesNotFoundException {
        File propertiesFile = new File(propertiesPath);

        if (!propertiesFile.exists()) {
            throw new PropertiesNotFoundException(propertiesFile.getName());
        }

        return propertiesFile;
    }

    private static class PropertiesContainer {
        private Map<Path, Properties> loadedProperties = new HashMap<>();
        private Map<Path, Properties> loadingProperties = new HashMap<>();

        public boolean isLoaded(Path path) {
            return loadedProperties.containsKey(path);
        }

        public synchronized boolean isLoading(Path path) {
            return loadingProperties.containsKey(path);
        }

        public Properties getProperties(Path path) {
            return loadedProperties.get(path);
        }

        public Properties getLoadingProperties(Path path) {
            return loadingProperties.get(path);
        }

        public void setLoaded(Path path, Properties properties) {
            if (isLoading(path)) {
                loadingProperties.remove(path);
                loadedProperties.put(path, properties);
            } else {
                throw new ConcurrentModificationException(path.getFileName().toString());
            }
        }

        public void addLoading(Path path, Properties properties) {
            if (isLoading(path)) {
                throw new ConcurrentModificationException(path.getFileName().toString());
            } else {
                loadingProperties.put(path, properties);
            }
        }
    }
}
