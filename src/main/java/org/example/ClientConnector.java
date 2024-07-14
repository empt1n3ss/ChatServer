package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnector {
    private static final String HOST = "localhost";
    private static boolean disconnected = false;

    public static void connectToServer() {
        int port = LoadSettings.loadSettings();
        try (Socket socket = new Socket(HOST, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Соединение с сервером установлено");
            System.out.print("Введите ваше имя: ");
            String clientName = stdIn.readLine();
            out.println(clientName);

            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("\r" + serverMessage);
                        System.out.print("Введите сообщение: ");
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при чтении сообщения с сервера: " + e.getMessage());
                } finally {
                    disconnected = true;
                }
            }).start();

            String userInput;
            System.out.print("Введите сообщение: ");
            while ((userInput = stdIn.readLine()) != null) {
                if (disconnected) {
                    break;
                }
                out.println(userInput);
                if ("/exit".equalsIgnoreCase(userInput)) {
                    System.out.println("Отключение");
                    break;
                }
                System.out.print("Введите сообщение: ");
            }

        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост: " + HOST);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода при подключении к серверу");
        }
    }

    public static boolean isDisconnected() {
        return disconnected;
    }
}
