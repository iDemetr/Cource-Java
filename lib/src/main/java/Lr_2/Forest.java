package Lr_2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import Lr_2.Animals.AAnimal;
import Lr_2.Plants.APlant;

/**
 * Класс, представляющий лес, содержащий растения и животных.
 */
public class Forest {
    private CopyOnWriteArrayList<APlant> plants = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<AAnimal> animals = new CopyOnWriteArrayList<>();

    /**
     * Добавляет растение в лес.
     *
     * @param plant растение для добавления.
     */
    public void add(APlant plant) {
	plants.add(plant);
    }

    /**
     * Добавляет животное в лес.
     *
     * @param animal животное для добавления.
     */
    public void add(AAnimal animal) {
	animals.add(animal);
    }

    /**
     * Удаляет растение из леса. thread safe
     *
     * @param plant растение для удаления.
     */
    public void remove(APlant plant) {
	plants.remove(plant);
    }

    /**
     * Удаляет животное из леса. thread safe
     *
     * @param animal животное для удаления.
     */
    public void remove(AAnimal animal) {
	animals.remove(animal);
    }

    /**
     * Возвращает список растений в лесу. multithread not safe
     * 
     * @return список растений.
     */
    public List<APlant> getPlants() {
	return plants;
    }

    /**
     * Возвращает список животных в лесу. multithread not safe
     * 
     * @return список животных.
     */
    public List<AAnimal> getAnimals() {
	return animals;
    }
}
