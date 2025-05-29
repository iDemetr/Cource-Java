package Lr_1;

/**
 * Класс Controller управляет взаимодействием между моделью и представлением.
 * Обрабатывает ввод данных и вызывает соответствующие методы для вычислений.
 */
public class Controller {

    private Model Model;
    private View View;

    /**
     * Конструктор контроллера, который инициализирует модель и представление.
     *
     * @param model модель для вычисления квадратного корня.
     * @param view  представление для отображения результатов.
     */
    public Controller(Model model, View view) {
	this.Model = model;
	this.View = view;
    }

    /**
     * Метод для выполнения эксперимента с заранее подготовленными данными.
     *
     */
    public void runExperiment() {
	double[] numbers = { 1, 2, 3, 10, 20, 30, 100, 200, 300 };
	double[] precisions = { 0.01, 0.001, 0.0001, 0.00001, 0.000001 };

	for (double number : numbers) {
	    for (double precision : precisions) {

		var newtonResult = Model.CalcSqrtNewton(number, precision);
		var javaResult = Model.CalcSqrt(number);

		View.displayResults(number, precision, newtonResult.getLeft(), javaResult.getLeft(),
			newtonResult.getRight(), javaResult.getRight());
	    }
	}
    }
}
