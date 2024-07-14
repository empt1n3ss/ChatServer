package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertTrue;

public class ClientConnectorTest {
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private final PrintStream originalSystemErr = System.err;

    private final ByteArrayInputStream simulatedInput = new ByteArrayInputStream("TestUser\n/exit\n".getBytes());
    private final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
    private final ByteArrayOutputStream capturedError = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setIn(simulatedInput);
        System.setOut(new PrintStream(capturedOutput));
        System.setErr(new PrintStream(capturedError));
    }

    @After
    public void restoreStreams() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        System.setErr(originalSystemErr);
    }

    @Before
    public void setUpLogger() {
        LoggerForTest.logInfo("Начало тестирования ClientConnector");
    }

    @After
    public void tearDownLogger() {
        LoggerForTest.logInfo("Завершение тестирования ClientConnector");
    }

    @Test
    public void testConnectToServer() {
        try {
            ClientConnector.connectToServer();

            String capturedOutputString = capturedOutput.toString();

            if (!capturedOutputString.contains("Соединение с сервером установлено")) {
                throw new AssertionError("Ошибка ввода-вывода при подключении к серверу");
            }

            assertTrue(capturedOutputString.contains("Введите ваше имя: "));
            assertTrue(capturedOutputString.contains("Введите сообщение: "));
            assertTrue(capturedOutputString.contains("Отключение"));

            String capturedErrorString = capturedError.toString();
            assertTrue("Неожиданная ошибка в потоке ошибок: " + capturedErrorString, capturedErrorString.isEmpty());

            LoggerForTest.logInfo("Тест testConnectToServer завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testConnectToServer: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testConnectToServer", e);
            throw e;
        }
    }
}
