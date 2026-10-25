package uk.gov.dwp.uc.pairtest.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {

    private static final Properties properties = new Properties();

    static {
        // Automatically looks in src/main/resources
        try (InputStream input =
                     AppConfig.class.getClassLoader().getResourceAsStream(
                             "application.properties")) {
            if (input == null) {
                System.err.println("Warning: application.properties not found in " +
                        "classpath. Using DEFAULT configuration values.");
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Error loading application.properties: " +
                    e.getMessage() + ". Using DEFAULT configuration values.");
        }
    }

    public static final int MAX_TICKETS = getInt("ticket.max.allowed",
            25);
    public static final int ADULT_PRICE = getInt("ticket.price.adult",
            25);
    public static final int CHILD_PRICE = getInt("ticket.price.child",
            15);
    public static final int INFANT_PRICE = getInt("ticket.price.infant",
            0);

    private AppConfig () {}

    public static int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("Warning: Invalid integer value for " +
                    "configuration key '" + key + "'. Falling back to default: " + defaultValue);
            return defaultValue;
        }
    }
}
