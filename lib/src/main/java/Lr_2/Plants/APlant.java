package Lr_2.Plants;

/**
 * Абстрактный класс, представляющий растение.
 */
public abstract class APlant {
    private String name;

    /**
     * Конструктор растения.
     *
     * @param name название растения.
     */
    public APlant(String name) {
	this.name = name;
    }

    /**
     * Возвращает название растения.
     *
     * @return название растения.
     */
    public String getName() {
	return name;
    }
}