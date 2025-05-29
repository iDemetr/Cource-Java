package Lr_2;

import Lr_2.Animals.AAnimal;
import Lr_2.Animals.Herbivore;
import Lr_2.Animals.Predator;
import Lr_2.Plants.APlant;
import Lr_2.Plants.Grass;
import Lr_2.Plants.Tree;

public class Main {

    public static void main(String[] args) {
	// Создаем лес
	Forest forest = new Forest();
	initForest(forest);

	// Демонстрация поиска пропитания
	for (AAnimal animal : forest.getAnimals()) {
	    animal.findFood(forest);
	}

	// Вывод оставшихся растений и животных
	System.out.println("\nОставшиеся растения в лесу:");
	for (APlant plant : forest.getPlants()) {
	    System.out.println(plant.getName());
	}

	System.out.println("\nОставшиеся животные в лесу:");
	for (AAnimal animal : forest.getAnimals()) {
	    System.out.println(animal.getName());
	}
    }

    private static void initForest(Forest forest) {
	// Добавляем деревья
	forest.add(new Tree("Дуб"));
	forest.add(new Tree("Береза"));
	forest.add(new Tree("Ель"));
	forest.add(new Tree("Сосна"));
	forest.add(new Tree("Лиственница"));
	forest.add(new Tree("Клен"));

	// Добавляем растения
	forest.add(new Grass("Клевер"));
	forest.add(new Grass("Черника"));
	forest.add(new Grass("Папоротник"));

	// Добавляем травоядных животных
	forest.add(new Herbivore("Заяц", 1, "Grass"));
	forest.add(new Herbivore("Олень", 2, "Tree"));
	forest.add(new Herbivore("Косуля", 2, "Grass"));
	forest.add(new Herbivore("Бобер", 2, "Tree"));
	forest.add(new Herbivore("Заяц", 2, "Grass"));
	forest.add(new Herbivore("Олень", 5, "Tree"));

	// Добавляем хищников
	forest.add(new Predator("Лиса", 2));
	forest.add(new Predator("Волк", 3));
	forest.add(new Predator("Медведь", 3));
	forest.add(new Predator("Ястреб", 2));
	forest.add(new Predator("Волк", 7));
	forest.add(new Predator("Лиса", 4));
    }
}
