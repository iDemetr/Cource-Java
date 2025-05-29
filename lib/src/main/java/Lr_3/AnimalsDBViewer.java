package Lr_3;

import java.util.List;

import Lr_2.Animals.AAnimal;

/**
 * 
 */
class AnimalsDBViewer {

    /**
     * 
     * @param animals
     */
    public void displayAnimals(List<AAnimal> animals) {
	System.out.println("Список животных:");
	for (var animal : animals) {
	    System.out.println(animal);
	}
    }

    /**
     * 
     */
    public void showDBMenu() {
	System.out.println("Главное меню:");
	System.out.println("1. Добавить животное");
	System.out.println("2. Удалить животное");
	System.out.println("3. Изменить животное");
	System.out.println("4. Показать всех животных");
	System.out.println("5. Сохранить данные в файл");
	System.out.println("6. Загрузить данные из файла");
	System.out.println("0. Выход");
    }

    /**
     * 
     * @param message
     */
    public void showMessage(String message) {
	System.out.println(message);
	Logger.log(message);
    }
}