package Lr_4;

import java.lang.reflect.Field;
import java.util.ArrayList;

import sun.misc.Unsafe;

/**
 * Класс вспомогательных функций коллекции
 */
public class ArrayListUtils {

    private static final Unsafe unsafe;

    static {
	try {
	    Field field = Unsafe.class.getDeclaredField("theUnsafe");
	    field.setAccessible(true);
	    unsafe = (Unsafe) field.get(null);
	} catch (Exception e) {
	    throw new RuntimeException("Failed to get Unsafe instance", e);
	}
    }

    public static int getArrayListCapacity(ArrayList<?> list) {
	try {
	    Field field = ArrayList.class.getDeclaredField("elementData");

	    long offset = unsafe.objectFieldOffset(field);
	    Object[] elementData = (Object[]) unsafe.getObject(list, offset);

	    // field.setAccessible(true);
	    // Object[] elementData = (Object[]) field.get(list);

	    return elementData.length;
	} catch (Exception e) {
	    Logger.logError("Ошибка при получении емкости ArrayList: " + e.getMessage());
	    return -1;
	}
    }
}