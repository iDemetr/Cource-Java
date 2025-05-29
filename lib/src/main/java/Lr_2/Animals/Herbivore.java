package Lr_2.Animals;

import java.util.List;

import Lr_2.Forest;
import Lr_2.Plants.APlant;
import Lr_2.Plants.Grass;
import Lr_2.Plants.Tree;

/**
 * Класс, представляющий травоядное животное.
 */
public class Herbivore extends AAnimal {
    private String preferredPlantType;

    /**
     * Конструктор травоядного животного.
     *
     * @param name               название животного.
     * @param size               размер животного.
     * @param preferredPlantType предпочитаемый тип растения.
     */
    public Herbivore(String name, int size, String preferredPlantType) {
	super(name, size);
	this.preferredPlantType = preferredPlantType;
    }

    @Override
    public String toString() {
	return super.toString() + (preferredPlantType != "" ? "," + preferredPlantType : "");
    }

    @Override
    public void findFood(Forest forest) {
	List<APlant> plants = forest.getPlants();
	for (APlant plant : plants) {
	    if (plant instanceof Grass && preferredPlantType.equals("Grass")) {
		System.out.println(getName() + " съел " + plant.getName() + ".");
		plants.remove(plant); // Уничтожаем растение
		return;
	    } else if (plant instanceof Tree && preferredPlantType.equals("Tree")) {
		System.out.println(getName() + " съел " + plant.getName() + ".");
		plants.remove(plant); // Уничтожаем растение
		return;
	    }
	}

	System.out.println(getName() + " не нашел(шла) подходящей еды и умер(ла).");
	this.isDied = true;
	forest.remove(this);
    }
}
