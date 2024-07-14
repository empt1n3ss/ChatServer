package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static org.example.BroadcastMessage.clients;
import static org.example.LoadSettings.loadSettings;
import static org.example.ServerCommands.handleServerCommands;
import static org.example.ServerCommands.printAvailableCommands;

public class Server {
    protected static final int port = loadSettings();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер запущен и ждет подключения на порт " + port);
            printAvailableCommands();

            Thread commandThread = new Thread(() -> {
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                String command;
                try {
                    while ((command = consoleReader.readLine()) != null) {
                        handleServerCommands(command);
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при чтении команды: " + e.getMessage());
                }
            });
            commandThread.start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
