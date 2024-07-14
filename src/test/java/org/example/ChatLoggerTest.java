package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class ChatLoggerTest {
    private static final String LOG_FILE = "chat_log.txt";
    private static final Path LOG_PATH = Paths.get(LOG_FILE);

    private final PrintStream originalSystemOut = System.out;
    private final PrintStream originalSystemErr = System.err;

    private final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
    private final ByteArrayOutputStream capturedError = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(capturedOutput));
        System.setErr(new PrintStream(capturedError));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalSystemOut);
        System.setErr(originalSystemErr);
    }

    @After
    public void cleanUp() throws IOException {
        Files.deleteIfExists(LOG_PATH);
    }

    @Before
    public void setUpLogger() {
        LoggerForTest.logInfo("Начало тестирования ChatLogger");
    }

    @After
    public void tearDownLogger() {
        LoggerForTest.logInfo("Завершение тестирования ChatLogger");
        LoggerForTest.closeLogger();
    }

    @Test
    public void testLogMessage() throws IOException {
        String message = "Тестовое сообщение для лога";
        try {
            ChatLogger.logMessage(message);
            waitForFileWrite();
            assertTrue("Файл лога не найден", Files.exists(LOG_PATH));
            String logContent = Files.readString(LOG_PATH);
            assertTrue("Ожидаемое сообщение не найдено в логе", logContent.contains(message));
            LoggerForTest.logInfo("Тест testLogMessage завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testLogMessage: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testLogMessage", e);
            throw e;
        }
    }

    @Test
    public void testLogConnection() throws IOException {
        String clientName = "TestClient";
        try {
            ChatLogger.logConnection("Клиент " + clientName + " подключился");
            waitForFileWrite();
            assertTrue("Файл лога не найден", Files.exists(LOG_PATH));
            String logContent = Files.readString(LOG_PATH);
            assertTrue("Ожидаемое сообщение не найдено в логе", logContent.contains("Клиент " + clientName + " подключился"));
            LoggerForTest.logInfo("Тест testLogConnection завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testLogConnection: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testLogConnection", e);
            throw e;
        }
    }

    @Test
    public void testLogDisconnection() throws IOException {
        String clientName = "TestClient";
        try {
            ChatLogger.logDisconnection("Клиент " + clientName + " отключился");
            waitForFileWrite();
            assertTrue("Файл лога не найден", Files.exists(LOG_PATH));
            String logContent = Files.readString(LOG_PATH);
            assertTrue("Ожидаемое сообщение не найдено в логе", logContent.contains("Клиент " + clientName + " отключился"));
            LoggerForTest.logInfo("Тест testLogDisconnection завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testLogDisconnection: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testLogDisconnection", e);
            throw e;
        }
    }

    private void waitForFileWrite() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
