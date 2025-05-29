package Lr_3;

import Lr_2.Animals.AAnimal;
import Lr_2.Animals.Herbivore;
import Lr_2.Animals.Predator;

/**
 * 
 */
class AnimalsFactory {

    /**
     * 
     * @param str
     * @return
     */
    public static AAnimal fromString(String str) {
	String[] parts = str.split(",");

	var name = parts[1];
	var size = Integer.parseInt(parts[2]);
	var type = parts[3];
	var additionalData = parts.length == 5 ? parts[4] : "";

	return createAnimal(name, size, type, additionalData);
    }

    /**
     * 
     * @param name
     * @param size
     * @param type
     * @param additionalData
     * @return
     */
    public static AAnimal createAnimal(String name, int size, String type, Object additionalData) {

	type = type.toLowerCase();

	switch (type) {
	case "herbivore":
	    return new Herbivore(name, size, (String) additionalData);
	case "predator":
	    return new Predator(name, size);
	default:
	    throw new IllegalArgumentException("Unknown animal type: " + type);
	}
    }
}