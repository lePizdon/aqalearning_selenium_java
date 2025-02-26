package org.learning;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfProperties {
    private static final Properties properties;
    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/conf.properties")){
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
