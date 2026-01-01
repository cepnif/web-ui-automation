package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads framework configuration values from the classpath resource:
 * {@code src/test/resources/config/framework.properties}.
 *
 * <p>System properties can override file values, e.g. {@code -Dbrowser=firefox}.</p>
 */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();
    private static boolean loaded = false;

    private ConfigReader() {
        // Utility class
    }

    /**
     * Loads {@code framework.properties} once and caches values in memory.
     *
     * @throws IllegalStateException if the properties file is not found on the classpath
     * @throws RuntimeException if reading the properties file fails
     */
    public static synchronized void load() {
        if (loaded) {
            return;
        }

        try (InputStream is = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/framework.properties")) {

            if (is == null) {
                throw new IllegalStateException("Cannot find config/framework.properties on classpath");
            }

            PROPS.load(is);
            loaded = true;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load framework.properties", e);
        }
    }

    /**
     * Gets a configuration value by key.
     *
     * <p>System property ({@code -Dkey=value}) takes precedence over file value.</p>
     *
     * @param key {@link String} the configuration key
     * @return {@link String} the resolved configuration value, or {@code null} if not present
     */
    public static String get(String key) {
        load();
        String sys = System.getProperty(key);
        return (sys != null && !sys.isBlank()) ? sys : PROPS.getProperty(key);
    }

    /**
     * Gets a configuration value as a boolean.
     *
     * @param key {@link String} the configuration key
     * @return {@code boolean} the parsed boolean value (defaults to {@code false} if missing)
     */
    public static boolean getBool(String key) {
        return Boolean.parseBoolean(get(key));
    }

    /**
     * Gets a configuration value as an integer.
     *
     * @param key {@link String} the configuration key
     * @return {@code int} the parsed integer value
     * @throws NumberFormatException if the value is not a valid integer
     */
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
