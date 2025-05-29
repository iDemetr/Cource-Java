package Lr_2.Animals;

import java.util.List;

import Lr_2.Forest;

/**
 * Класс, представляющий хищное животное.
 */
public class Predator extends AAnimal {

    /**
     * Конструктор хищного животного.
     *
     * @param name название животного.
     * @param size размер животного.
     */
    public Predator(String name, int size) {
	super(name, size);
    }

    @Override
    public void findFood(Forest forest) {
	List<AAnimal> animals = forest.getAnimals();
	for (AAnimal animal : animals) {
	    if (animal.getSize() < this.getSize() && !animal.equals(this)) {
		System.out.println(getName() + " съел " + animal.getName() + ".");
		animals.remove(animal); // Уничтожаем животное
		return;
	    }
	}

	System.out.println(getName() + " не нашел(шла) подходящей еды и умер(ла).");
	this.isDied = true;
	forest.remove(this);
    }
}
