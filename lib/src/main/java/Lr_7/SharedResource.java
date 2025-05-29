package Lr_7;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Потенциальные проблемы и улучшения
 * 
 * Конкуренция между потоками:
 * Если у вас много потоков на чтение и запись, может возникнуть ситуация, когда потоки записи будут часто блокироваться,
 * если потоки чтения постоянно запрашивают данные. 
 * Это может привести к снижению производительности.
 * 
 * Чтение и запись:
 * В текущей реализации, если поток записи выполняется, потоки чтения могут заблокироваться, даже если данные не были изменены. 
 * Это может быть нежелательным, если вы хотите, чтобы несколько потоков могли читать одновременно, 
 * а запись происходила только по необходимости.
 * 
 * Улучшение с помощью ReadWriteLock:
 * Рассмотрите возможность использования ReadWriteLock, который позволяет нескольким потокам читать одновременно, 
 * но блокирует запись, пока есть активные потоки чтения. 
 * Это может значительно повысить производительность в сценариях с большим количеством операций чтения.
 */

public class SharedResource {
    private StringBuilder value = new StringBuilder();
    private boolean hasNewData = false;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    // private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public String read() throws InterruptedException {
	lock.lock();
	// rwLock.readLock().lock();
	try {
	    while (!hasNewData) {
		condition.await(); // Ожидание, если новых данных нет
	    }
	    hasNewData = false; // Сбрасываем флаг
	    return value.toString();
	} finally {
	    lock.unlock();
	    // rwLock.readLock().unlock();
	}
    }

    public void write(String newData) {
	lock.lock();
	// rwLock.writeLock().lock();
	try {
	    value.append(newData);
	    hasNewData = true; // Устанавливаем флаг
	    condition.signalAll(); // Уведомляем все ожидающие потоки
	} finally {
	    lock.unlock();
	    // rwLock.writeLock().unlock();
	}
    }
}
