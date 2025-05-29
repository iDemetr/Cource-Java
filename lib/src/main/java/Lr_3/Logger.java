package Lr_3;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 */
public class Logger {
    private static final String LOG_FILE = "LR_3.log";
    private static boolean isDebugMode = true;

    /**
     * 
     * @param isDebugMode
     */
    public static void loadSettings(boolean isDebugMode) {
	Logger.isDebugMode = isDebugMode;
    }

    /**
     * 
     * @param message
     */
    public static void log(String message) {
	if (!isDebugMode) {
	    return;
	}

	try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
	    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    writer.write(timestamp + " - " + message + "\n");
	    writer.close();
	} catch (IOException e) {
	    System.err.println("Ошибка при записи в лог: " + e.getMessage());
	}
    }
}
