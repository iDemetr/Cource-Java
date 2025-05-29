package Lr_2.Animals;

import Lr_2.Forest;

/**
 * Абстрактный класс, представляющий животное.
 */
public abstract class AAnimal {
    private String name;
    private int size;
    protected Boolean isDied;

    /**
     * Конструктор животного.
     *
     * @param name название животного.
     * @param size размер животного.
     */
    public AAnimal(String name, int size) {
	this.name = name;
	this.size = size;
	isDied = false;
    }

    /**
     * Возвращает название животного.
     *
     * @return название животного.
     */
    public String getName() {
	return name;
    }

    /**
     * Возвращает размер животного.
     *
     * @return размер животного.
     */
    public int getSize() {
	return size;
    }

    /**
     * Возвращает состояние животного.
     *
     * @return состояние животного.
     */
    public boolean getState() {
	return isDied;
    }

    @Override
    public String toString() {
	return this.hashCode() + "," + name + "," + size + "," + this.getClass().getSimpleName();
    }

    /**
     * Абстрактный метод для поиска и поедания пропитания.
     *
     * @param forest лес, в котором животное ищет пропитание.
     */
    public abstract void findFood(Forest forest);
}
