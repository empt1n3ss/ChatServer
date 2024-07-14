package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatLogger {
    private static final String LOG_FILE = "chat_log.txt";

    public static void logMessage(String message) {
        logToFile(message);
    }

    public static void logConnection(String clientName) {
        logToFile("Клиент " + clientName + " подключился");
    }

    public static void logDisconnection(String clientName) {
        logToFile("Клиент " + clientName + " отключился");
    }

    private static void logToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timeStampedMessage = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + message;
            writer.write(timeStampedMessage);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в лог-файл: " + e.getMessage());
        }
    }
}
