package Lr_3;

import java.util.Scanner;

/**
 * 
 */
public class Authentication {
    private String username;
    private String password;

    /**
     * 
     * @param username
     * @param password
     */
    public Authentication(String username, String password) {
	this.username = username;
	this.password = password;
    }

    /**
     * 
     * @return
     */
    public boolean authenticate() {
	var scanner = new Scanner(System.in);
	System.out.print("Введите логин: ");
	var inputUsername = scanner.nextLine();
	System.out.print("Введите пароль: ");
	var inputPassword = scanner.nextLine();

	// scanner.close(); // Приводит к последующей ошибке при взаимодействии с новым
	// потоком чтения

	return inputUsername.equals(username) && inputPassword.equals(password);
    }
}
