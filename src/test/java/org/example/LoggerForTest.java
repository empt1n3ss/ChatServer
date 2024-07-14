package org.example;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerForTest {
    private static final String LOG_FILE = "test_log.txt";
    private static final Logger logger = Logger.getLogger(LoggerForTest.class.getName());
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации логгера: " + e.getMessage());
        }
    }

    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

    public static void logError(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    public static void closeLogger() {
        if (fileHandler != null) {
            fileHandler.close();
        }
    }
}
