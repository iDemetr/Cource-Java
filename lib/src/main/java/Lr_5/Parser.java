package Lr_5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<OperationStats> parseFile(String filePath) {
	List<OperationStats> statsList = new ArrayList<>();
	String currentCollectionType = null;
	int currentSize = 0;

	try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

	    // Пропускаем строку с заголовком
	    // "---operator,countItem,totalTime,medianTime---"
	    reader.readLine();

	    String line;
	    while ((line = reader.readLine()) != null) {
		line = line.trim();

		// Пропуск пустых строк
		if (line.isEmpty()) {
		    continue;
		}

		// Обработка строки с типом коллекции и размером
		if (line.startsWith("[")) {
		    String[] parts = line.replace("[", "").replace("]", "").split(":");
		    currentCollectionType = parts[0];
		    currentSize = Integer.parseInt(parts[1]);
		} else {
		    // Обработка строки с данными об операции
		    String[] parts = line.split(",");
		    String operationType = parts[0];
		    int countItem = Integer.parseInt(parts[1]);
		    long totalTime = Long.parseLong(parts[2]);
		    long medianTime = Long.parseLong(parts[3]);

		    // Создание объекта OperationStats и добавление в список
		    OperationStats stats = new OperationStats(currentCollectionType, currentSize, operationType,
			    countItem, totalTime, medianTime);
		    statsList.add(stats);
		}
	    }
	} catch (IOException e) {
	    System.err.println("Ошибка при чтении файла: " + e.getMessage());
	}

	return statsList;
    }
}