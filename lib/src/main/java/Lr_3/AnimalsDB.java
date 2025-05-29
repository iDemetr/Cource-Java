package Lr_3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Lr_2.Animals.AAnimal;

/**
 * 
 */
class AnimalsDB {
    private List<AAnimal> animals;
    private static final String FILE_NAME = "animals_db.txt";

    /**
     * 
     */
    public AnimalsDB() {
	animals = new ArrayList<>();
	loadFromFile();
    }

    /**
     * 
     */
    public void loadFromFile() {

	var file = new File(FILE_NAME);
	if (!file.exists()) {
	    try {
		file.createNewFile();
	    } catch (IOException e) {
		System.out.println("Ошибка создания файла БД: " + e.getMessage());
	    }
	}

	try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
	    String line;
	    while ((line = br.readLine()) != null) {
		animals.add(AnimalsFactory.fromString(line));
	    }
	    System.out.println("Данные успешно загружены из файла.");
	} catch (IOException e) {
	    System.out.println("Ошибка при загрузке данных: " + e.getMessage());
	}
    }

    /**
     * 
     */
    public void saveToFile() {
	try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
	    for (var animal : animals) {
		bw.write(animal.toString());
		bw.newLine();
	    }
	    System.out.println("Данные успешно сохранены в файл.");
	} catch (IOException e) {
	    System.out.println("Ошибка при сохранении данных: " + e.getMessage());
	}
    }

    /**
     * 
     * @param animal
     */
    public void addAnimal(AAnimal animal) {
	animals.add(animal);
    }

    /**
     * 
     * @param id
     */
    public void deleteAnimal(int id) {
	animals.removeIf(animal -> animal.hashCode() == id);
    }

    /**
     * 
     * @return
     */
    public List<AAnimal> getAnimals() {
	return animals;
    }

    /**
     * 
     * @param id
     * @param newName
     * @param size
     * @param newType
     */
    public void changeAnimal(int id, String newName, int size, String newType) {
	for (var animal : animals) {
	    if (animal.hashCode() == id) {
		// Изменяем данные
		// В данном случае, создаем новый объект
		deleteAnimal(id);
		addAnimal(AnimalsFactory.createAnimal(newName, size, newType, ""));
		return;
	    }
	}
    }
}
