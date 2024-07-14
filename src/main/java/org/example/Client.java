package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    public static void main(String[] args) {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String choice = "";

        while (!choice.equals("3") && !ClientConnector.isDisconnected()) {
            System.out.println("Выберите действие:");
            System.out.println("1. Прочитать настройки сервера из файла");
            System.out.println("2. Подключиться к серверу");
            System.out.println("3. Выйти из программы");
            System.out.print("Введите ваш выбор (1, 2 или 3): ");

            try {
                choice = stdIn.readLine();

                switch (choice) {
                    case "1":
                        ClientSettingsReader.readServerSettings();
                        break;
                    case "2":
                        ClientConnector.connectToServer();
                        break;
                    case "3":
                        System.out.println("Выход из программы.");
                        break;
                    default:
                        System.out.println("Неверный выбор.");
                }

            } catch (IOException e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
            }
        }
    }
}
