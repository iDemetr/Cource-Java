package Lr_3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 */
public class Settings {
    private String username;
    private String password;
    private String userGroup;
    private boolean debugMode;
    private boolean autoTestMode;

    /**
     * 
     * @param configFilePath
     */
    public Settings(String configFilePath) {
	Properties properties = new Properties();
	try (FileInputStream fis = new FileInputStream(configFilePath)) {
	    properties.load(fis);
	    this.username = properties.getProperty("username");
	    this.password = properties.getProperty("password");
	    this.userGroup = properties.getProperty("userGroup");
	    this.debugMode = Boolean.parseBoolean(properties.getProperty("debugMode"));
	    this.autoTestMode = Boolean.parseBoolean(properties.getProperty("autoTestMode"));
	} catch (IOException e) {
	    System.err.println("Ошибка при чтении файла настроек: " + e.getMessage());
	    Logger.log("Ошибка при чтении файла настроек: " + e.getMessage());
	}
    }

    /**
     * 
     * @return
     */
    public String getUsername() {
	return username;
    }

    /**
     * 
     * @return
     */
    public String getPassword() {
	return password;
    }

    /**
     * 
     * @return
     */
    public String getUserGroup() {
	return userGroup;
    }

    /**
     * 
     * @return
     */
    public boolean isDebugMode() {
	return debugMode;
    }

    /**
     * 
     * @return
     */
    public boolean isAutoTestMode() {
	return autoTestMode;
    }
}
