package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class ClientSettingsReaderTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        LoggerForTest.logInfo("Начало тестирования ClientSettingsReader");
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        LoggerForTest.logInfo("Завершение тестирования ClientSettingsReader");
    }

    @Test
    public void testReadServerSettings() {
        try {
            ClientSettingsReader.readServerSettings();

            String output = outContent.toString().trim();
            assertTrue(output.contains("Настройки сервера:"));
            assertTrue(output.matches("(?s).*Порт: 12345"));

            LoggerForTest.logInfo("Тест testReadServerSettings завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testReadServerSettings: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testReadServerSettings", e);
            throw e;
        }
    }

    @Test
    public void testReadServerSettings_FileNotFound() {
        try {
            java.io.File originalFile = new java.io.File("settings.txt");
            java.io.File renamedFile = new java.io.File("settings_backup.txt");
            originalFile.renameTo(renamedFile);

            ClientSettingsReader.readServerSettings();

            String output = outContent.toString().trim();
            assertTrue(output.contains("Ошибка при загрузке настроек:"));

            LoggerForTest.logInfo("Тест testReadServerSettings_FileNotFound завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testReadServerSettings_FileNotFound: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testReadServerSettings_FileNotFound", e);
            throw e;
        } finally {
            java.io.File renamedFile = new java.io.File("settings_backup.txt");
            java.io.File originalFile = new java.io.File("settings.txt");
            renamedFile.renameTo(originalFile);
        }
    }
}
