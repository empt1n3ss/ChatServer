package org.example;

import static org.example.ChatLogger.logMessage;

public class ServerCommands {
    static void handleServerCommands(String command) {
        if (command.startsWith("/send ")) {
            String message = command.substring(6);
            BroadcastMessage.broadcastMessage("Сервер: " + message, null);
            System.out.println("Вы отправили сообщение: " + message);
            logMessage("Сервер: " + message);

        } else if ("/exit".equalsIgnoreCase(command)) {
            System.out.println("Остановка сервера...");
            BroadcastMessage.broadcastMessage("Остановка сервера...", null);
            System.exit(0);
        } else {
            System.out.println("Неизвестная команда: " + command);
            printAvailableCommands();
        }
    }
    static void printAvailableCommands() {
        System.out.println("Доступные команды:\n" +
                "/send <message> - Отправить сообщение всем клиентам\n" +
                "/exit - Остановить сервер");
    }
}
