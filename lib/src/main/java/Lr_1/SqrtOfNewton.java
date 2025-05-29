package Lr_1;

/**
 * Класс SqrtOfNewton представляет модель для вычисления квадратного корня
 * числа.
 */
public class SqrtOfNewton {

    public static double Calc(double value, double eps /* =0.001 */) {

	if (value < 0) {
	    throw new IllegalArgumentException("Число должно быть неотрицательным");
	}
	if (value == 0) {
	    return 0;
	}

	double x = value; // Начальное приблежение, влияющее на вероятность схождения.
	while (Math.abs(x * x - value) > eps) {
	    x = (x + value / x) * .5;
	}
	return x;
    }
}
