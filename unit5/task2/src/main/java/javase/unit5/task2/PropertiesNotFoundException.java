package javase.unit5.task2;

public class PropertiesNotFoundException extends Exception {

    private final String propertiesFileName;

    public PropertiesNotFoundException(String propertiesFileName) {
        this.propertiesFileName = propertiesFileName;
    }

    @Override
    public String getMessage() {
        return String.format(
                "Properties file %s was not found.",
                propertiesFileName);
    }
}
