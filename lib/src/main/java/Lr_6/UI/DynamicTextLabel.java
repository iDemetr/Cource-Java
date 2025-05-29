package Lr_6.UI;

import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * 
 */
public class DynamicTextLabel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 414859496578898960L;
    /**
     * Колличейство выводимых слов
     */
    int Size;
    /**
     * Номер "Центрального" лейбла вводимого пользователем
     */
    int originPostLabel;

    /**
     * Коллекция лейблов
     */
    List<FadingJLabel> labelList;
    /**
     * Массив слов для вывода
     */
    List<String> textCollection;
    /**
     * Относительыный сдвиг в массиве взодных слов для вывода
     */
    int iterator;

    /**
     * Время анимации затухания по умолчанию
     */
    int defaultTimeFade = 50;

    /**
     * 
     * @param manager
     */
    public DynamicTextLabel(LayoutManager manager) {
	this(7, 3, manager);
    }

    /**
     * 
     * @param size
     * @param originPosLabel
     * @param manager
     */
    public DynamicTextLabel(int size, int originPosLabel, LayoutManager manager) {
	super(manager);

	this.Size = size;
	this.originPostLabel = originPosLabel;
	iterator -= originPosLabel;

	textCollection = new ArrayList<>();
	labelList = new ArrayList<>();

	for (var i = 0; i < Size; i++) {
	    var item = new FadingJLabel("");
	    item.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Отступ справа

	    textCollection.add("");
	    labelList.add(item);

	    add(item);
	}

	this.setVisible(true);
    }

    public int getViewSize() {
	return labelList.size() < textCollection.size() ? labelList.size() : textCollection.size();
    }

    /**
     * Стандартный режим рааботы
     * 
     * @param textCollection
     */
    public void setText(List<String> text) {

	iterator = -originPostLabel;
	this.textCollection = text;

	update();
	iterator++;
    }

    /**
     * Режим работы "очередь"
     * 
     * @param textCollection
     */
    public void pushText(String text) {

	fadeOut(defaultTimeFade);

	for (var i = 0; i < Size - 1; i++) {
	    this.textCollection.set(i, this.textCollection.get(i + 1));
	}
	this.textCollection.set(Size - 1, text);

	iterator = 0;
	update();

	fadeIn(defaultTimeFade);
    }

    /**
     * Режим работы "очередь"
     * 
     * @param textCollection
     */
    public void resetText() {

	fadeOut(defaultTimeFade);

	for (var i = 0; i < Size; i++) {
	    this.textCollection.set(i, "");
	}

	iterator = 0;
	update();

	fadeIn(defaultTimeFade);
    }

    /**
     * 
     */
    public void next() {
	fadeOut(defaultTimeFade);

	update();
	iterator++;

	fadeIn(defaultTimeFade);
    }

    /**
     * 
     * @param duration
     */
    public void fadeIn(int duration) {
	for (var component : labelList) {
	    component.fadeIn(duration);
	}
    }

    /**
     * 
     * @param duration
     */
    public void fadeOut(int duration) {
	for (var component : labelList) {
	    component.fadeOut(duration);
	}
    }

    /**
     * Обновляет видимость и текст лейблов
     */
    private void update() {
	var i = 0;
	for (var component : labelList) {
	    var curr = iterator + i;
	    if (textCollection.size() > curr && curr > -1) {
		component.setText(textCollection.get(curr));
		update_label(component, i);
	    } else {
		component.setText("");
	    }
	    i++;
	}
    }

    /**
     * Обновляет прозрачность лейблов в звисимости от центральной позиции
     * 
     * @param component
     * @param i
     */
    private void update_label(FadingJLabel component, int i) {

	if (i < originPostLabel) {
	    component.setAlpha(calculateOpacityValue(i, 0.05, 1, originPostLabel, getViewSize()));
	} else if (i > originPostLabel) {
	    component.setAlpha(calculateOpacityValue(i, 0.15, 1, originPostLabel, getViewSize()));
	} else {
	    component.setAlpha(1.0f);
	}
    }

    /**
     * 
     * @param currentIndex
     * @param minValue
     * @param maxValue
     * @param originValue
     * @param originCell
     * @param size
     * @return
     */
    public static double calculateOpacityValue(int currentIndex, double minValue, double originValue, int originCell,
	    int size) {
	if (currentIndex == size) {
	    return originValue; // Возвращаем значение максимума для ячейки с максимумом
	}

	// if (originCell > size) {
	// originCell = size;
	// }

	// Интерполяция слева от максимума
	if (currentIndex < originCell) {
	    return minValue + (originValue - minValue) * currentIndex / originCell;
	}

	// Интерполяция справа от максимума
	return originValue - (originValue - minValue) * Math.abs(originCell - currentIndex) / (size - originCell - 1);
    }
}
