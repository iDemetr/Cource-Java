package Lr_1;

import java.util.Scanner;

/**
 * Класс View отвечает за отображение результатов вычислений. Обеспечивает вывод
 * информации в консоль.
 */
public class View {

    private Scanner scanner;

    /**
     * Конструктор представления, который инициализирует сканер, для чтения
     * консольного потока
     */
    public View() {
	scanner = new Scanner(System.in);
    }

    /**
     * Получение числа от пользователя
     * 
     * @return Распаршенное значение
     */
    public double getNumber() {
	System.out.print("Введите число: ");
	while (true) {
	    String input = scanner.nextLine();
	    if (new Model().isValidInput(input)) {
		return Double.parseDouble(input);
	    } else {
		System.out.print("Неверный ввод. Введите неотрицательное число: ");
	    }
	}
    }

    /**
     * Получение точности от пользователя
     * 
     * @return Распаршенное значение точности
     */
    public double getPrecision() {
	System.out.print("Введите точность (например, 0.01 для 1%): ");
	while (true) {
	    String input = scanner.nextLine();
	    if (new Model().isValidInput(input)) {
		return Double.parseDouble(input);
	    } else {
		System.out.print("Неверный ввод. Введите неотрицательное число: ");
	    }
	}
    }

    /**
     * Метод для отображения результатов вычисления квадратного корня с
     * использованием метода Ньютона и встроенной функции Java Math.sqrt. Результаты
     * выводятся в виде таблицы, включая время выполнения.
     *
     * @param number       Число, для которого вычисляется квадратный корень.
     * @param precision    Точность вычисления (разница между итерациями метода
     *                     Ньютона).
     * @param newtonResult Результат вычисления методом Ньютона.
     * @param javaResult   Результат вычисления с использованием Math.sqrt.
     * @param newtonTime   Время выполнения метода Ньютона в наносекундах.
     * @param javaTime     Время выполнения Math.sqrt в наносекундах.
     */
    public void displayResults(double number, double precision, double newtonResult, double javaResult, long newtonTime,
	    long javaTime) {
	// Заголовок таблицы
	System.out.println("+----------------+----------------+----------------+----------------+");
	System.out.println("| Число          | Точность       | Метод Ньютона  | Java Math.sqrt |");
	System.out.println("+----------------+----------------+----------------+----------------+");

	// Форматирование данных для таблицы
	String row = String.format("| %-14.2f | %-14.6f | %-14.6f | %-14.6f |", number, precision, newtonResult,
		javaResult);
	System.out.println(row);

	// Время выполнения
	System.out.println("+----------------+----------------+----------------+----------------+");
	System.out.printf("| Время (нс)     |                | %-14d | %-14d |", newtonTime, javaTime);
	System.out.println();
	System.out.println("+----------------+----------------+----------------+----------------+");
	System.out.println(); // Пустая строка для разделения результатов
    }
}
