package Lr_4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import Lr_2.Forest;

/**
 * 
 */
public class Test {

    private static Random random = new Random();

    /**
     * 
     * @param size
     */
    public static void testArrayList(int size) {
	ArrayList<Forest> list = new ArrayList<>();
	Map<String, Long> totalTimes = new HashMap<>();
	Map<String, Integer> totalCounts = new HashMap<>();

	var pullTime = PullArrayList(size, list);
	totalTimes.put("add", pullTime);
	totalCounts.put("add", size);

	var res = FlushArrayList(size, list);
	totalTimes.put("remove", res.getLeft());
	totalCounts.put("remove", res.getRight());

	// Логирование итогов
	Logger.logSummary("ArrayList", totalTimes, totalCounts);
	Logger.logSummary("ArrayList", size, totalTimes, totalCounts);
    }

    /**
     * 
     * @param size
     */
    public static void testHashMap(int size) {
	HashMap<Integer, Forest> map = new HashMap<>();
	Map<String, Long> totalTimes = new HashMap<>();
	Map<String, Integer> totalCounts = new HashMap<>();

	var pullTimes = PullHashMap(size, map);
	totalTimes.put("put", pullTimes);
	totalCounts.put("put", size);

	var res = FlushHashMap(size, map);
	totalTimes.put("remove", res.getLeft());
	totalCounts.put("remove", res.getRight());

	// Логирование итогов
	Logger.logSummary("HashMap", totalTimes, totalCounts);
	Logger.logSummary("HashMap", size, totalTimes, totalCounts);
    }

    /**
     * 
     * @param size
     * @param list
     * @return Затраченное время на добавление элементов в список в нс
     */
    public static Long PullArrayList(int size, ArrayList<Forest> list) {
	// Переменные для отслеживания изменения времени добавления
	long lastAddTime = 0, totalTimes = 0;
	int lastCapacity = 0;

	// Добавление элементов
	// long startTime = System.nanoTime();
	for (int i = 0; i < size; i++) {
	    var forest = ForestGenerator.generateForest(i);

	    long operationStart = System.nanoTime();
	    list.add(forest);
	    long operationTime = System.nanoTime() - operationStart;

	    if (i < 50 || i >= size - 50) {
		Logger.logOperation("ArrayList", "add", i, operationTime);
		if (i == 50) {
		    Logger.log("\t---\t", "");
		}
	    }

	    // Проверка изменения емкости
	    int currentCapacity = ArrayListUtils.getArrayListCapacity(list);
	    if (currentCapacity != lastCapacity) {
		Logger.log("ArrayList capacity increased from " + lastCapacity + " to " + currentCapacity + " \t in "
			+ (operationTime - lastAddTime) + " ns", "green");
		lastCapacity = currentCapacity;
	    }

	    totalTimes += operationTime;
	    lastAddTime = operationTime;
	}
	return totalTimes;
    }

    /**
     * 
     * @param size
     * @param list
     * @return
     */
    public static Pair<Long, Integer> FlushArrayList(int size, ArrayList<Forest> list) {

	Long totalTimes = 0L;
	int totalCounts = 0;

	// Удаление 10% элементов
	int removeCount = (int) (size * 0.1);
	for (int i = 0; i < removeCount; i++) {
	    try {
		int index = random.nextInt(list.size());

		long operationStart = System.nanoTime();
		list.remove(index);
		long operationTime = System.nanoTime() - operationStart;

		if (i < 50 || i >= size - 50) {
		    Logger.logOperation("ArrayList", "remove", index, operationTime);
		    if (i == 50) {
			Logger.log("\t---\t", "");
		    }
		}

		totalTimes += operationTime;
		totalCounts++;

	    } catch (Exception e) {
		Logger.log("Error: " + e.getMessage(), "red");
	    }
	}
	return Pair.of(totalTimes, totalCounts);
    }

    /**
     * 
     * @param size
     * @param map
     * @return Затраченное время на добавление элементов в словарь в нс
     */
    public static Long PullHashMap(int size, HashMap<Integer, Forest> map) {
	Long totalTimes = 0L;

	// Добавление элементов
	for (int i = 0; i < size; i++) {
	    var forest = ForestGenerator.generateForest(i);

	    long operationStart = System.nanoTime();
	    map.put(i, forest);
	    long operationTime = System.nanoTime() - operationStart;

	    if (i < 50 || i >= size - 50) {
		Logger.logOperation("HashMap", "put", i, operationTime);
		if (i == 50) {
		    Logger.log("\t---\t", "");
		}
	    }
	    totalTimes += operationTime;
	}

	return totalTimes;
    }

    /**
     * 
     * @param size
     * @param map
     * @return
     */
    public static Pair<Long, Integer> FlushHashMap(int size, HashMap<Integer, Forest> map) {

	Long totalTimes = 0L;
	int totalCounts = 0;

	// Удаление 10% элементов
	int removeCount = (int) (size * 0.1);
	for (int i = 0; i < removeCount; i++) {
	    try {
		int key = random.nextInt(size) + 1;

		long operationStart = System.nanoTime();
		map.remove(key);
		long operationTime = System.nanoTime() - operationStart;

		if (i < 50 || i >= size - 50) {
		    Logger.logOperation("HashMap", "remove", key, operationTime);
		    if (i == 50) {
			Logger.log("\t---\t", "");
		    }
		}

		totalTimes += operationTime;
		totalCounts++;

	    } catch (Exception e) {
		Logger.log("Error: " + e.getMessage(), "red");
	    }
	}

	return Pair.of(totalTimes, totalCounts);
    }

}
