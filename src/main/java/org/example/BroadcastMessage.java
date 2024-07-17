package org.example;

import java.util.ArrayList;
import java.util.List;

public class BroadcastMessage {
    protected static List<ClientHandler> clients = new ArrayList<>();

    public static synchronized void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}
