package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientHandlerTest {

    private ClientHandler clientHandler;
    private Socket mockSocket;
    private ByteArrayOutputStream outContent;
    private InputStream mockInputStream;
    private OutputStream mockOutputStream;
    private PrintStream originalOut;

    @Before
    public void setUp() throws IOException {
        LoggerForTest.logInfo("Начало тестирования ClientHandler");

        mockSocket = mock(Socket.class);
        outContent = new ByteArrayOutputStream();
        mockInputStream = new ByteArrayInputStream("TestClient\nTest message\n/exit".getBytes());
        mockOutputStream = new ByteArrayOutputStream();
        originalOut = System.out;

        when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);
        when(mockSocket.getInputStream()).thenReturn(mockInputStream);

        System.setOut(new PrintStream(outContent));
        clientHandler = new ClientHandler(mockSocket);
    }

    @After
    public void tearDown() throws IOException {
        System.setOut(originalOut);
        outContent.close();
        mockInputStream.close();
        mockOutputStream.close();

        LoggerForTest.logInfo("Завершение тестирования ClientHandler");
    }

    @Test
    public void testClientHandler_MessageReceived() throws InterruptedException {
        try {
            clientHandler.start();
            clientHandler.join();

            String expectedMessage = "[" + LocalDate.now() + "]" + "Получено сообщение от TestClient: Test message";
            String actualOutput = outContent.toString();
            assertTrue(actualOutput.contains(expectedMessage));

            LoggerForTest.logInfo("Тест testClientHandler_MessageReceived завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testClientHandler_MessageReceived: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testClientHandler_MessageReceived", e);
            throw e;
        }
    }

    @Test
    public void testClientHandler_ExitCommand() throws InterruptedException {
        try {
            clientHandler.start();
            clientHandler.join();

            String expectedLog = "[" + LocalDate.now() + "]TestClient отключился.";
            String actualOutput = outContent.toString();
            assertTrue(actualOutput.contains(expectedLog));

            LoggerForTest.logInfo("Тест testClientHandler_ExitCommand завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testClientHandler_ExitCommand: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testClientHandler_ExitCommand", e);
            throw e;
        }
    }

    @Test
    public void testClientHandler_SendMessage() throws InterruptedException {
        try {
            clientHandler.start();
            clientHandler.join();
            clientHandler.sendMessage("Test reply");

            String expectedOutput = "Test reply\n";
            String actualOutput = mockOutputStream.toString();

            String[] messages = actualOutput.split("\n");
            String lastMessage = messages[messages.length - 1];

            assertEquals(expectedOutput.trim(), lastMessage.trim());

            LoggerForTest.logInfo("Тест testClientHandler_SendMessage завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testClientHandler_SendMessage: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testClientHandler_SendMessage", e);
            throw e;
        }
    }
}
