package Lr_4;

import java.util.Random;

import Lr_2.Forest;
import Lr_2.Animals.AAnimal;
import Lr_2.Animals.Herbivore;
import Lr_2.Animals.Predator;
import Lr_2.Plants.APlant;
import Lr_2.Plants.Grass;
import Lr_2.Plants.Tree;

/**
 * 
 */
public class ForestGenerator {

    private static final Random random = new Random();

    /**
     * Генерирует рандомный лес
     * 
     * @param id
     * @return
     */
    public static Forest generateForest(int id) {
	Forest forest = new Forest();
	// Добавляем случайное количество растений и животных
	for (int i = 0; i < random.nextInt(3) + 1; i++) {
	    forest.add(generrateRandomPlant(i));
	    forest.add(generrateRandomAnimal(i));
	}
	return forest;
    }

    /**
     * Генерирует рандомное растение
     * 
     * @param i
     * @return
     */
    static APlant generrateRandomPlant(int i) {
	int type = random.nextInt(2);

	switch (type) {
	case 0:
	    return new Grass("grass" + i);
	case 1:
	    return new Tree("tree" + i);
	default:
	    break;
	}
	return null;
    }

    /**
     * Генерирует рандомное животное
     * 
     * @param i
     * @return
     */
    static AAnimal generrateRandomAnimal(int i) {
	int type = random.nextInt(2);

	switch (type) {
	case 0:
	    return new Herbivore("herbivore" + i, random.nextInt(1, 10), "");
	case 1:
	    return new Predator("predator" + i, random.nextInt(1, 10));
	default:
	    break;
	}
	return null;
    }

}
