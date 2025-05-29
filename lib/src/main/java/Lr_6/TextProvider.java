package Lr_6;

import Lr_6.Parsers.RandomQuotesExtractor;
import Lr_6.Parsers.RandomWordsExtractor;
import Lr_6.Parsers.TextGenerator;

/**
 * 
 */
public class TextProvider implements ITextProvider {

    String[] wordsLibrary;

    String pathWords = "russian.txt";
    String pathQuotes = "Quotes.txt";

    int cntWords;
    int lenghtWords;

    boolean isWordsMode = false;
    boolean isUserMode = false;
    boolean isQuotesMode = false;

    TextGenerator textGenerator;
    RandomQuotesExtractor randomQuotesExtractor;
    RandomWordsExtractor randomWordsExtractor;

    /**
     * 
     */
    public TextProvider() {
	textGenerator = new TextGenerator();
	randomQuotesExtractor = new RandomQuotesExtractor();
	randomWordsExtractor = new RandomWordsExtractor();

	cntWords = 0;
	apllyMode("default");
    }

    /**
     * 
     * @return
     */
    @Override
    public String generateText() {
	if (isWordsMode) {
	    return textGenerator.generateRandomText(wordsLibrary, cntWords);
	} else if (isQuotesMode) {
	    return randomQuotesExtractor.Extruct(pathQuotes);
	} else if (isUserMode) {
	    return "NotImplemented";
	} else {
	    return textGenerator.generateRandomText(cntWords, lenghtWords);
	}
    }

    /**
     * 
     * @param cnt
     */
    @Override
    public void setCntWords(int cnt) {
	cntWords = cnt;
    }

    /**
     * 
     */
    @Override
    public void setMode(String mode) {

	isWordsMode = false;
	isUserMode = false;
	isQuotesMode = false;

	var configs = getConfigMode(mode).split("-");
	for (var config : configs) {
	    apllyMode(config);
	}
    }

    /**
     * TODO: Вынести в отдельный файл-конфиг
     * 
     * @return
     */
    @Override
    public String[] getAvailableModes() {
	return new String[] { "Короткие комбинации русских букв (Легкий)", "Длинные комбинации русских букв (Средний)",
		"Короткие комбинации английских букв (Средний)", "Длинные комбинации английских букв (Сложный)",
		"Безумец (Сложный(Очень сложный))", "Рандомные слова (Легкий)", "Рандомные слова (Средний)",
		"Рандомные слова (Сложный)", "Цитаты", "Использование пользовательского файла" };
    }

    /**
     * TODO: Вынести в отдельный файл-конфиг
     * 
     * @param publicMode
     * @return
     */
    private static String getConfigMode(String publicMode) {
	switch (publicMode) {
	case "Короткие комбинации русских букв (Легкий)":
	    return "ru-short";
	case "Длинные комбинации русских букв (Средний)":
	    return "ru-long";
	case "Короткие комбинации английских букв (Средний)":
	    return "en-short";
	case "Длинные комбинации английских букв (Сложный)":
	    return "en-long";
	case "Безумец (Сложный(Очень сложный))":
	    return "naughty_fingers";
	case "Рандомные слова (Легкий)":
	    return "short-random";
	case "Рандомные слова (Средний)":
	    return "long-random";
	case "Рандомные слова (Сложный)":
	    return "longlong-random";
	case "Цитаты":
	    return "quotes";
	case "Использование пользовательского файла":
	    return "user";
	default:
	    throw new IllegalArgumentException(
		    "Неверно указан мод: " + publicMode + "\nПеречень возможных модов в getAvailableModes()");
	}
    }

    /**
     * 
     * @param mode
     */
    private void apllyMode(String mode) {
	switch (mode) {
	case "short":
	    lenghtWords = 5;
	    break;
	case "long":
	    lenghtWords = 10;
	    break;
	case "longlong":
	    lenghtWords = 100;
	    break;
	case "ru":
	    textGenerator.setCharsMode("ru");
	    break;
	case "en":
	    textGenerator.setCharsMode("en");
	    break;
	case "nums":
	    textGenerator.setCharsMode("nums");
	    break;
	case "naughty_fingers":
	    textGenerator.setCharsMode("ru");
	    textGenerator.addCharsMode("en");
	    textGenerator.addCharsMode("nums");
	    break;
	case "random":
	    apllyWordsMode();
	    break;
	case "quotes":
	    apllyQuotesMode();
	    break;
	case "user":
	    apllyUserMode();
	    break;
	default:
	    apllyMode("short");
	    apllyMode("ru");
	    break;
	}
    }

    /**
     * 
     */
    private void apllyWordsMode() {
	wordsLibrary = randomWordsExtractor.Extruct(pathWords, 1000, lenghtWords);
	isWordsMode = true;
    }

    /**
     * 
     */
    private void apllyUserMode() {
	isUserMode = true;
    }

    /**
     * 
     */
    private void apllyQuotesMode() {
	isQuotesMode = true;
    }

}
