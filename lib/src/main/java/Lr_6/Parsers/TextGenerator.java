package Lr_6.Parsers;

import java.util.Random;

/**
 * 
 */
public class TextGenerator {

    static String charactersNumbers = "0123456789";
    static String charactersEn = "qwertyuiopasdfghjklzxcvbnm";
    static String charactersRu = "йцукенгшщзхъфывапролджэячсмитьбю";

    private String selectedCharacters;

    Random random = new Random();

    /**
     * 
     */
    public TextGenerator() {
    }

    /**
     * Генерирует N радномных слов рандомной длиной
     * 
     * @param cntWords    Колличество слов
     * @param lenghtWords Масимальная длина слов
     * @return
     */
    public String generateRandomText(int cntWords, int lenghtWords) {

	StringBuilder sb = new StringBuilder(cntWords);
	for (int i = 0; i < cntWords; i++) {
	    sb.append(generateRandomWord(lenghtWords) + " ");
	}

	return sb.toString().trim();
    }

    /**
     * Генерирует случайных текст из набора слов
     * 
     * @param array  Массив возмодных слов
     * @param length Длина предложения
     * @return
     */
    public String generateRandomText(String[] array, int length) {

	StringBuilder sb = new StringBuilder(length);
	int i = 0;
	while (i++ < length) {
	    sb.append(array[random.nextInt(array.length)] + " ");
	}
	return sb.toString().trim();
    }

    /**
     * 
     * @return
     */
    private String generateRandomWord(int lenghtWords) {
	var length = random.nextInt(1, lenghtWords);
	StringBuilder sb = new StringBuilder(length);
	for (int i = 0; i < length; i++) {
	    sb.append(selectedCharacters.charAt(random.nextInt(selectedCharacters.length())));
	}
	return sb.toString();
    }

    /**
     * 
     * @param type ru, en, nums
     * @return
     */
    public String getCharsMod(String type) {
	switch (type) {
	case "ru":
	    return charactersRu;
	case "en":
	    return charactersEn;
	case "nums":
	    return charactersNumbers;
	default:
	    break;
	}
	return "";
    }

    /**
     * 
     * @param type ru, en, nums
     */
    public void setCharsMode(String type) {
	selectedCharacters = getCharsMod(type);
    }

    /**
     * 
     * @param type ru, en, nums
     */
    public void addCharsMode(String type) {
	selectedCharacters += getCharsMod(type);
    }
}
