package Lr_3;

/**
 * 
 */
public class AuthenticationController {

    private Settings settings;

    /**
     * 
     * @return
     */
    public boolean authenticate() {
	// Чтение настроек
	settings = new Settings("config.properties");

	Logger.loadSettings(settings.isDebugMode());
	// Логирование старта программы
	Logger.log("Настройки загружены. Пользователь: " + settings.getUsername());

	// Аутентификация
	Authentication auth = new Authentication(settings.getUsername(), settings.getPassword());
	if (!auth.authenticate()) {
	    System.out.println("Ошибка аутентификации. Программа завершена.");
	    Logger.log("Ошибка аутентификации. Программа завершена.");
	    return false;
	}

	// Приветствие
	System.out.println("Добро пожаловать, " + settings.getUsername() + "!");
	Logger.log("Авторизация пройдена.");

	return true;
    }

    /**
     * 
     * @return
     */
    public Settings GetSettings() {
	return settings;
    }

}
