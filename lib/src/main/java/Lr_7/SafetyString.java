package Lr_7;

public class SafetyString {

    private StringBuilder value;
    private final int delay; // Задержка в миллисекундах

    // private final Semaphore semaphore = new Semaphore(1);

    private boolean isWriting = false; // Флаг, указывающий, что происходит запись

    // private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    // private Object objlock = 1;

    private int countWrites = 0;

    /**
     * 
     * @param delay
     */
    public SafetyString(int delay) {
	this.value = new StringBuilder();
	this.delay = delay;
    }

    /**
     * 
     * @return
     */
    public synchronized String read() {
	try {
	    // Если идёт запись или записей небыло - ждать
	    while (isWriting || countWrites < 1) {
		wait();
	    }
	    sleep(delay);
	    return value.toString();

	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	    return null;
	} finally {
	    countWrites--;
	    notifyAll(); // Уведомляем все ожидающие потоки
	}
    }

    /**
     * 
     * @param newValue
     */
    public synchronized void write(String newValue) {
	try {
	    while (countWrites > 0) {
		wait(); // Ожидание, если происходит запись или есть читатели
	    }

	    isWriting = true; // Устанавливаем флаг записи
	    sleep(delay);
	    value.append(newValue);
	    isWriting = false; // Сбрасываем флаг записи

	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	} finally {
	    countWrites++;
	    notifyAll(); // Уведомляем все ожидающие потоки
	}
    }

    /*
     * public String read() throws InterruptedException { return queue.take(); //
     * Ожидание, пока не появится элемент }
     * 
     * public void write(String newData) throws InterruptedException {
     * value.append(newData); queue.put(value.toString()); // Ожидание, если очередь
     * полна }
     */

    /**
     * 
     * @param delay
     */
    private void sleep(int delay) {
	try {
	    Thread.sleep(delay); // Задержка для симуляции долгого процесса
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
	}
    }
}
