package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.example.BroadcastMessage.broadcastMessage;
import static org.example.ChatLogger.*;
import static org.example.RemoveClient.removeClient;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private String clientName;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void run() {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            this.out = out;

            clientName = in.readLine();
            out.println("Добро пожаловать, " + clientName + "!");
            out.println("Для выхода из чата введите /exit");
            logConnection(clientName);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("[" + java.time.LocalDate.now() + "]" + "Получено сообщение от " + clientName + ": " + inputLine);
                logMessage(clientName + " отправил сообщение: " + inputLine);
                broadcastMessage(clientName + ": " + inputLine, this);
                if ("/exit".equalsIgnoreCase(inputLine)) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при общении с клиентом: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[" + java.time.LocalDate.now() + "]" + clientName + " отключился.");
            logDisconnection(clientName);
            removeClient(this);
        }
    }
}
