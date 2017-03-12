package javase.unit5.task2;

public class PropertyNotFoundException extends Exception {

    private final String properties;
    private final String propertyKey;

    public PropertyNotFoundException(String propertyKey, String properties) {
        this.propertyKey = propertyKey;
        this.properties = properties;
    }

    @Override
    public String getMessage() {
        return String.format(
                "Property was not found by key %s in properties %s.",
                propertyKey,
                properties);
    }
}
