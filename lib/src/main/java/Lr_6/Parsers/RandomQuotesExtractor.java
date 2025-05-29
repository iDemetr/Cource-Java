package Lr_6.Parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 */
public class RandomQuotesExtractor {
    /**
     * 
     * @param filePath
     * @return
     */
    public String Extruct(String filePath) {
	try {
	    // Шаг 1: Читаем все цитаты из файла в список
	    List<String> quotes = new ArrayList<>();
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
		String line;
		while ((line = reader.readLine()) != null) {
		    quotes.add(line.trim()); // Добавляем каждую цитату в список
		}
	    }

	    // Шаг 2: Генерируем случайный индекс для выбора цитаты
	    if (!quotes.isEmpty()) {
		Random random = new Random();
		int randomIndex = random.nextInt(quotes.size());
		return quotes.get(randomIndex); // Получаем случайную цитату
	    } else {
		System.out.println("Файл с цитатами пуст.");
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
