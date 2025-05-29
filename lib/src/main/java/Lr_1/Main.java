package Lr_1;

/**
 * Основной класс приложения, который инициализирует модель, представление и
 * контроллер, а также запускает процесс вычисления квадратного корня.
 */
public class Main {

    /**
     * Основной метод приложения.
     *
     * @param args аргументы командной строки (no used).
     */
    public static void main(String[] args) {

	System.out.println("Work Lr_1 started!");

	// Инициализация модели, представления и контроллера
	var model = new Model();
	var view = new View();
	var controller = new Controller(model, view);

	// Проведение эксперимента
	controller.runExperiment();

	System.out.println("Work Lr_1 complited!");
    }

}
