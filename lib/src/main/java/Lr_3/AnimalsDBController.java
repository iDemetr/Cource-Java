package Lr_3;

import java.util.Scanner;

/**
 * 
 */
class AnimalsDBController {

    private AnimalsDB database;
    private AnimalsDBViewer view;
    private Scanner scanner;

    /**
     * 
     * @param database
     * @param view
     */
    public AnimalsDBController(AnimalsDB database, AnimalsDBViewer view) {
	this.database = database;
	this.view = view;
	this.scanner = new Scanner(System.in);
    }

    /**
     * 
     */
    public void run() {
	int choice;
	do {
	    view.showDBMenu();
	    System.out.print("Выберите действие: ");
	    choice = scanner.nextInt();
	    scanner.nextLine(); // Чистим буфер

	    switch (choice) {
	    case 1:
		addAnimal();
		view.displayAnimals(database.getAnimals());
		break;
	    case 2:
		deleteAnimal();
		view.displayAnimals(database.getAnimals());
		break;
	    case 3:
		changeAnimal();
		view.displayAnimals(database.getAnimals());
		break;
	    case 4:
		view.displayAnimals(database.getAnimals());
		break;
	    case 5:
		database.saveToFile();
		break;
	    case 6:
		database.loadFromFile();
		view.displayAnimals(database.getAnimals());
		break;
	    case 0:
		view.showMessage("Выход из программы.");
		break;
	    default:
		view.showMessage("Неверный выбор. Попробуйте снова.");
	    }
	} while (choice != 0);
	scanner.close();
    }

    /**
     * 
     */
    private void addAnimal() {

	System.out.print("Введите имя животного: ");
	var name = scanner.nextLine();

	System.out.print("Введите размер животного: ");
	var size = scanner.nextInt();
	scanner.nextLine(); // Чистим буфер

	System.out.print("Введите тип животного: ");
	var type = scanner.nextLine();

	var preferredPlantType = "";
	if (type.toLowerCase().equals("herbivore")) {
	    System.out.print("Введите предпочитаемый тип расстения: ");
	    preferredPlantType = scanner.nextLine();
	}

	database.addAnimal(AnimalsFactory.createAnimal(name, size, type, preferredPlantType));
	view.showMessage("Животное {name} добавлено.");
    }

    /**
     * 
     */
    private void deleteAnimal() {
	System.out.print("Введите ID животного для удаления: ");
	int deleteId = scanner.nextInt();
	database.deleteAnimal(deleteId);
	view.showMessage("Животное удалено.");
    }

    /**
     * 
     */
    private void changeAnimal() {
	System.out.print("Введите ID животного для изменения: ");

	int id = scanner.nextInt();
	scanner.nextLine(); // Чистим буфер

	System.out.print("Введите новое имя животного: ");
	var newName = scanner.nextLine();

	System.out.print("Введите новый размер животного: ");
	var newSize = scanner.nextInt();

	System.out.print("Введите новый тип животного: ");
	var newType = scanner.nextLine();

	database.changeAnimal(id, newName, newSize, newType);
	view.showMessage("Животное изменено.");
    }
}
