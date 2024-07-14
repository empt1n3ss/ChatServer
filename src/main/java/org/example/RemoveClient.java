package org.example;

import static org.example.BroadcastMessage.clients;

public class RemoveClient {
    public static void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
