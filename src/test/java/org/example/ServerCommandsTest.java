package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerCommandsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        LoggerForTest.logInfo("Начало тестирования ServerCommands");
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        LoggerForTest.logInfo("Завершение тестирования ServerCommands");
    }

    @Test
    public void testHandleServerCommands_Send() {
        try {
            String command = "/send Test message";
            ServerCommands.handleServerCommands(command);

            String output = outContent.toString().trim();
            String expectedOutput = "Вы отправили сообщение: Test message";
            assertEquals(expectedOutput, output);

            LoggerForTest.logInfo("Тест testHandleServerCommands_Send завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testHandleServerCommands_Send: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testHandleServerCommands_Send", e);
            throw e;
        }
    }

    @Test
    public void testHandleServerCommands_Unknown() {
        try {
            String command = "/unknown";
            ServerCommands.handleServerCommands(command);

            String output = outContent.toString().trim();
            assertTrue(output.contains("Неизвестная команда"));

            LoggerForTest.logInfo("Тест testHandleServerCommands_Unknown завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testHandleServerCommands_Unknown: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testHandleServerCommands_Unknown", e);
            throw e;
        }
    }

    @Test
    public void testPrintAvailableCommands() {
        try {
            ServerCommands.printAvailableCommands();

            String output = outContent.toString().trim();
            String expectedOutput = "Доступные команды:\n" +
                    "/send <message> - Отправить сообщение всем клиентам\n" +
                    "/exit - Остановить сервер";
            assertEquals(expectedOutput, output);

            LoggerForTest.logInfo("Тест testPrintAvailableCommands завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testPrintAvailableCommands: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testPrintAvailableCommands", e);
            throw e;
        }
    }
}
