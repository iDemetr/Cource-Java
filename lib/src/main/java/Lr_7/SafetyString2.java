package Lr_7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SafetyString2 {

    private final StringBuilder value;
    private final int delay; // Задержка в миллисекундах

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private final CountDownLatch latch = new CountDownLatch(1);

    public SafetyString2(int delay) {
	this.value = new StringBuilder();
	this.delay = delay;
    }

    public String read() {
	try {
	    latch.await(); // Ожидание завершения записи
	    readLock.lock();
	    sleep(delay);
	    return value.toString();
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	    return null;
	} finally {
	    readLock.unlock();
	}
    }

    public void write(String newValue) {
	writeLock.lock();
	try {
	    sleep(delay);
	    value.append(newValue);
	    latch.countDown(); // Уменьшаем счётчик после записи
	} finally {
	    writeLock.unlock();
	}
    }

    private void sleep(int delay) {
	try {
	    Thread.sleep(delay); // Задержка для симуляции долгого процесса
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
	}
    }
}
