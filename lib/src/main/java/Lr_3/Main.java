package Lr_3;

/**
 * 
 */
public class Main {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

	// Логирование старта программы
	Logger.log("Старт программы.");
	System.out.println("WorkDir: " + System.getProperty("user.dir"));

	var AuthoToken = new AuthenticationController();

	if (AuthoToken.authenticate()) {

	    var settings = AuthoToken.GetSettings(); // Выполнение автотестов, если включен режим
	    if (settings.isAutoTestMode()) {
		runAutoTests();
	    }

	    var database = new AnimalsDB();
	    var view = new AnimalsDBViewer();
	    var controller = new AnimalsDBController(database, view);

	    controller.run();
	}

	Logger.log("Завершение программы.");
    }

    /**
     * 
     */
    private static void runAutoTests() {
	//
	Logger.log("Запуск автотестов...");

	// TODO: Здесь будет логика автотестов

	//
	Logger.log("Результаты автотестов: все тесты пройдены.");
    }

}
