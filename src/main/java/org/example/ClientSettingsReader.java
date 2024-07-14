package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientSettingsReader {
    private static final String SETTINGS_FILE = "settings.txt";

    public static void readServerSettings() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            properties.load(input);
            String port = properties.getProperty("port");
            System.out.println("Настройки сервера:");
            System.out.println("Порт: " + port);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке настроек: " + e.getMessage());
        }
    }
}
