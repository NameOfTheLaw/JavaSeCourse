package javase.unit5.task2;

/**
 * Exception for properties file not found.
 */
public class PropertiesNotFoundException extends Exception {

    private final String propertiesFileName;

    /**
     * Constructor.
     *
     * @param propertiesFileName file name of the non existing properties file.
     */
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
