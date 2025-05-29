package Lr_4;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 
 */
public class Logger {
    private static final String LOG_FILE = "LR_4.log";
    private static final String SUMMARY_FILE = "LR_4_Summary.log";

    private static int errorCount = 0;

    private static String Reset = "\033[0m";
    private static String Red = "\033[31m";
    private static String Green = "\033[32m";
    private static String Yellow = "\033[33m";
    private static String Blue = "\033[34m";

    static {
	try (FileWriter fw = new FileWriter(LOG_FILE, false)) {
	} catch (IOException e) {
	    logError("Ошибка очистки лога: " + e.getMessage());
	}
	try (FileWriter fw = new FileWriter(SUMMARY_FILE, false)) {
	    fw.write("---operator,countItem,totalTime,medianTime---\n");
	} catch (IOException e) {
	    logError("Ошибка очистки лога: " + e.getMessage());
	}
    }

    /**
     * Основной логгер событий и сообщений Выводит сообщения в консоль с учетом
     * цвета + в стандартный логовый файл
     * 
     * @param message
     * @param color
     */
    public static void log(String message, String color) {
	try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
	    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

	    fw.write(timestamp + " - " + message + "\n");
	    printlnExtention(timestamp + " - " + message, color);

	} catch (IOException e) {
	    logError("Ошибка при записи в лог: " + e.getMessage());
	}
    }

    public static void logError(String message) {
	try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
	    fw.write(Red + message + Reset + "\n");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	errorCount++;
    }

    /**
     * Дефолный логгер об операции
     * 
     * @param collectionType
     * @param operation
     * @param id
     * @param time
     */
    public static void logOperation(String collectionType, String operation, int id, long time) {
	log(collectionType + ": " + operation + ", ID = " + id + ", " + time + " ns", "");
    }

    /**
     * Дефолтный логгер результатов одного теста, по заданию
     * 
     * @param collectionType
     * @param totalTimes
     * @param totalCounts
     */
    public static void logSummary(String collectionType, Map<String, Long> totalTimes,
	    Map<String, Integer> totalCounts) {
	log("------------------------------------------------------------------------------", "blue");
	log(collectionType + " Summary:", "blue");
	log("------------------------------------------------------------------------------", "blue");

	for (String operation : totalTimes.keySet()) {
	    long totalTime = totalTimes.get(operation);
	    int totalCount = totalCounts.get(operation);
	    long medianTime = totalCount == 0 ? 0 : totalTime / totalCount;
	    log(operation + "\tTotalCount = " + totalCount, "blue");
	    log("\t\tTotalTime = " + totalTime + " ns", "blue");
	    log("\t\tMedianTime = " + medianTime + " ns", "blue");
	}
	log("------------------------------------------------------------------------------", "blue");
    }

    /**
     * Вспомогательный логгер результатов тестирования в табличном формате,
     * необходимый для следующего задания
     * 
     * @param collectionType
     * @param size
     * @param totalTimes
     * @param totalCounts
     */
    public static void logSummary(String collectionType, int size, Map<String, Long> totalTimes,
	    Map<String, Integer> totalCounts) {
	try (FileWriter fw = new FileWriter(SUMMARY_FILE, true)) {
	    fw.write("[" + collectionType + ":" + size + "]\n");

	    for (String operation : totalTimes.keySet()) {
		long totalTime = totalTimes.get(operation);
		int totalCount = totalCounts.get(operation);
		long medianTime = totalCount == 0 ? 0 : totalTime / totalCount;

		fw.write(operation + "," + totalCount + "," + totalTime + "," + medianTime + "\n");
	    }
	} catch (IOException e) {
	    logError("Ошибка при записи в лог: " + e.getMessage());
	}
    }

    /**
     * Печатает цветной текст в консоль
     * 
     * @param message
     * @param color
     */
    public static void printlnExtention(String message, String color) {
	switch (color.toLowerCase()) {

	case "red":
	    System.out.println(Red + message + Reset);
	    break;
	case "blue":
	    System.out.println(Blue + message + Reset);
	    break;
	case "green":
	    System.out.println(Green + message + Reset);
	    break;
	case "yellow":
	    System.out.println(Yellow + message + Reset);
	    break;
	default:
	    System.out.println(message);
	    break;
	}
    }

    /**
     * 
     * @return
     */
    public static int getErrorCount() {
	return errorCount;
    }
}
