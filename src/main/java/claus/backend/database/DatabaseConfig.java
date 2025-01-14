package claus.backend.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                System.exit(1);
            }

            // Load the properties file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String getDbUrl() {
        return properties.getProperty("db.url");
    }

    static String getDbUsername() {
        return properties.getProperty("db.username");
    }

    static String getDbPassword() {
        return properties.getProperty("db.password");
    }
}