package Lr_6.Parsers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 
 */
public class RandomWordsExtractor {
    /**
     * 
     * @param filePath
     * @param numberOfWords
     * @param maxLength
     * @return
     */
    public String[] Extruct(String filePath, int numberOfWords, int maxLength) {
	try {

	    // Шаг 1: Получаем количество строк в файле
	    List<Long> linePositions = new ArrayList<>();
	    // List<String> validLines = new ArrayList<>(); // Список для строк, которые
	    // соответствуют критериям

	    try (BufferedReader reader = new BufferedReader(
		    new InputStreamReader(new FileInputStream(filePath), Charset.forName("windows-1251")))) {
		String line;
		long position = 0;
		while ((line = reader.readLine()) != null) {
		    // Проверяем длину строки
		    if (line.length() <= maxLength) {
			// Добавляем строку в список, если она подходит
			// validLines.add(line);
			linePositions.add(position); // Сохраняем позицию
		    }

		    // Учитываем длину строки и символы новой строки
		    position += line.length() + 1;
		    // .getBytes(Charset.forName("windows-1251")).length
		    // System.lineSeparator().getBytes(Charset.forName("windows-1251")).length;
		}
	    }

	    // Шаг 2: Генерируем случайные индексы
	    Set<Integer> randomIndexes = new HashSet<>();
	    Random random = new Random();
	    while (randomIndexes.size() < numberOfWords && randomIndexes.size() < linePositions.size()) {
		randomIndexes.add(random.nextInt(linePositions.size()));
	    }

	    String[] words = new String[numberOfWords];
	    int i = 0;

	    // Шаг 3: Читаем случайные строки из файла
	    try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r")) {
		long fileLength = randomAccessFile.length();
		System.out.println("Размер файла: " + fileLength + " байт");

		for (int index : randomIndexes) {
		    var pos = linePositions.get(index);
		    // Пример безопасного использования seek
		    if (pos >= 0 && pos < fileLength) {
			randomAccessFile.seek(pos);

			var v = randomAccessFile.readLine();
			var content = new String(v.getBytes("ISO-8859-1"), "Windows-1251");

			System.out.println("Переместили указатель на позицию: " + pos);
			System.out.println("Содержимое: " + v + " | " + content);

			words[i++] = content;
		    } else {
			System.out.println("Ошибка: Позиция выходит за пределы файла.");
		    }
		    // randomAccessFile.seek(pos);
		    // words[i++] = randomAccessFile.readLine();
		}
	    }

	    return words;

	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
    }
}
