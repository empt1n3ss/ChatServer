package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class LoadSettingsTest {
    private static final String SETTINGS_FILE = "settings_test.txt";
    private static final Path SETTINGS_PATH = Paths.get(SETTINGS_FILE);
    private static final int DEFAULT_PORT = 12345;

    @Before
    public void setUp() throws IOException {
        createSettingsFile("port=54321");
        LoggerForTest.logInfo("Настройка перед тестом завершена");
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(SETTINGS_PATH);
        LoggerForTest.logInfo("Очистка после теста завершена");
    }

    @Test
    public void testLoadSettings() {
        try {
            int port = LoadSettings.loadSettings();
            assertEquals(12345, port);
            LoggerForTest.logInfo("Тест testLoadSettings завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testLoadSettings: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testLoadSettings", e);
            throw e;
        }
    }

    @Test
    public void testLoadSettingsFileNotFound() throws IOException {
        try {
            Files.deleteIfExists(SETTINGS_PATH);
            int port = LoadSettings.loadSettings();
            assertEquals(DEFAULT_PORT, port);
            LoggerForTest.logInfo("Тест testLoadSettingsFileNotFound завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testLoadSettingsFileNotFound: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testLoadSettingsFileNotFound", e);
            throw e;
        }
    }

    @Test
    public void testLoadSettingsInvalidFormat() throws IOException {
        try {
            createSettingsFile("port=invalid");
            int port = LoadSettings.loadSettings();
            assertEquals(DEFAULT_PORT, port);
            LoggerForTest.logInfo("Тест testLoadSettingsInvalidFormat завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testLoadSettingsInvalidFormat: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testLoadSettingsInvalidFormat", e);
            throw e;
        }
    }

    private void createSettingsFile(String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(SETTINGS_PATH)) {
            writer.write(content);
        }
    }
}
