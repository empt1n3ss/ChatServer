package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadSettings {
    private static final String SETTINGS_FILE = "settings.txt";

    protected static int loadSettings() {
        Properties properties = new Properties();
        int port;
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            properties.load(input);
            port = Integer.parseInt(properties.getProperty("port"));
        } catch (IOException | NumberFormatException e) {
            System.out.println("Ошибка при загрузке настроек: " + e.getMessage());
            port = 12345;
        }
        return port;
    }
}
