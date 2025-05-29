package Lr_7;

/**
 * 
 */
public class MultiThreadWorkView {

    /**
     * 
     */
    public void showHelloMessage() {
	System.out.println("Запуск симуляции многопопточной синхронизации:");
    }

    /**
     * 
     */
    public void showByByMessage() {
	System.out.println("Работа завершена");
    }

    /**
     * 
     * @param who
     * @param value
     */
    public void showReaded(String who, String value) {
	System.out.println("Считано: " + who + "\n" + value);
    }

    /**
     * 
     * @param who
     * @param value
     */
    public void showWrited(String who, String value) {
	System.out.println("Записано: " + who + "\n" + value);
    }
}
