package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BroadcastMessageTest {

    private ClientHandler sender;
    private ClientHandler receiver1;
    private ClientHandler receiver2;

    @Before
    public void setUp() {
        LoggerForTest.logInfo("Начало тестирования BroadcastMessage");
        sender = Mockito.mock(ClientHandler.class);
        receiver1 = Mockito.mock(ClientHandler.class);
        receiver2 = Mockito.mock(ClientHandler.class);
    }

    @After
    public void tearDown() {
        LoggerForTest.logInfo("Завершение тестирования BroadcastMessage");
    }

    @Test
    public void testBroadcastMessage() {
        try {
            LoggerForTest.logInfo("Начало теста testBroadcastMessage");
            List<ClientHandler> clients = new ArrayList<>();
            clients.add(sender);
            clients.add(receiver1);
            clients.add(receiver2);

            BroadcastMessage.clients = clients;

            String message = "Test message";
            BroadcastMessage.broadcastMessage(message, sender);

            verify(receiver1, times(1)).sendMessage(message);
            verify(receiver2, times(1)).sendMessage(message);
            verify(sender, never()).sendMessage(message);

            LoggerForTest.logInfo("Тест testBroadcastMessage завершен успешно");
        } catch (AssertionError e) {
            LoggerForTest.logError("Ошибка в тесте testBroadcastMessage: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LoggerForTest.logError("Неожиданная ошибка в тесте testBroadcastMessage", e);
            throw e;
        }
    }
}
