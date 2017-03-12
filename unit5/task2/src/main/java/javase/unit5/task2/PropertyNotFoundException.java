package javase.unit5.task2;

/**
 * Exception for not found property in the properties file.
 */
public class PropertyNotFoundException extends Exception {

    private final String properties;
    private final String propertyKey;

    /**
     * Constructor.
     *
     * @param propertyKey property.
     * @param propertiesFileName properties file name.
     */
    public PropertyNotFoundException(String propertyKey, String propertiesFileName) {
        this.propertyKey = propertyKey;
        this.properties = propertiesFileName;
    }

    @Override
    public String getMessage() {
        return String.format(
                "Property was not found by key %s in properties %s.",
                propertyKey,
                properties);
    }
}
