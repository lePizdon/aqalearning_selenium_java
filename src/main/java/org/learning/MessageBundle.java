package org.learning;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MessageBundle {
    private static final Properties messages = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/messages.properties")) {
            messages.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load messages.properties");
        }
    }

    public static String get(String key) {
        return messages.getProperty(key);
    }

}
