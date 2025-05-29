package Lr_1;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Класс Model содержит методы для вычисления квадратного корня с использованием
 * метода Ньютона и встроенной функции Java Math.sqrt.
 */
public class Model {

    /**
     * Метод для вычисления квадратного корня с использованием метода Ньютона.
     *
     * @param value     значение.
     * @param precision точность вычисления.
     * @return вычисленный квадратный корень в паре с затраченным временем в
     *         наносекундах
     */
    public Pair<Double, Long> CalcSqrtNewton(double value, double eps) {
	var start = System.nanoTime();
	var res = SqrtOfNewton.Calc(value, eps);
	var end = System.nanoTime();

	return Pair.of(res, end - start);
    }

    /**
     * Метод для вычисления квадратного корня с использованием встроенной функции
     * Java Math.sqrt.
     *
     * @param value значение.
     * @return вычисленный квадратный корень в паре с затраченным временем в
     *         наносекундах
     */
    public Pair<Double, Long> CalcSqrt(double value) {
	var start = System.nanoTime();
	var res = Math.sqrt(value);
	var end = System.nanoTime();

	return Pair.of(res, end - start);
    }

    /**
     * Конструктор модели, который инициализирует число.
     *
     * @param input строка, для проверки double значения
     * @return true, если введенное значение double
     */
    public boolean isValidInput(String input) {
	try {
	    double number = Double.parseDouble(input);
	    return number >= 0;
	} catch (NumberFormatException e) {
	    return false;
	}
    }

}
