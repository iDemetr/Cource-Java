package Lr_6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import Lr_5.ThemeManager;
import Lr_6.UI.DynamicTextLabel;
import Lr_6.UI.RadioButtonsGroup;

/**
 * 
 */
public class TypingTrainer extends JFrame {

    private JTextArea inputTextArea;

    private DynamicTextLabel dynamicLabelInput;
    private DynamicTextLabel dynamicLabelHistory;

    private JLabel CPMLabel;
    private JLabel WPMLabel;
    private JLabel accuracyLabel;

    private JButton resetButton;
    private JButton startButton;
    private JButton stopButton;
    private RadioButtonsGroup wordCountGroup;

    private JPanel centerPanel;
    private JPanel controlPanel;
    private JPanel statsPanel;
    private JPanel panel;

    private JComboBox<String> difficultyComboBox;

    private Timer timer;
    private ITextProvider textProvider;

    private List<String> currentText;

    private int correctChars = 0; // Кол-во правильно введенных символов
    private int totalwordsCount = 0; // Количество введенных слов
    private int totalChars = 0; // Общее кол-во введенных символов

    private int iteratorChars = 0; // итератор символов из текущей генерации (курсор)
    private int iteratorWords = 0; // итератор слов из текущей генерации (курсор)

    private long startTime = 0; // Время начала ввода

    private boolean isInvalidLastChar = false; // Флаг, что был введен кооректный символ

    /**
     * 
     */
    public TypingTrainer() {
	initUI();
	initComponents();
	initLayout();
	initEvents();

	wordCountGroup.setActive(10);
	difficultyComboBox.setSelectedIndex(0);

	// Применяем темную тему
	ThemeManager.applyDarkTheme(this);
    }

    /**
     * 
     */
    private void initUI() {
	setTitle("Тренажер слепой печати");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(800, 600);
	getContentPane().setLayout(new BorderLayout());

	this.setMinimumSize(new Dimension(400, 300));

	setLocationRelativeTo(null);
	setVisible(true);
    }

    /**
     * 
     */
    private void initComponents() {
	timer = new Timer(500, e -> updateTextHandler());
	timer.setRepeats(false);

	textProvider = new TextProvider();
    }

    /**
     * 
     */
    private void initLayout() {

	// 1. Панель управления
	controlPanel = new JPanel();
	resetButton = new JButton("Reset");
	startButton = new JButton("Start");
	stopButton = new JButton("Stop");
	controlPanel.add(resetButton);
	controlPanel.add(startButton);
	controlPanel.add(stopButton);
	getContentPane().add(controlPanel, BorderLayout.NORTH);

	difficultyComboBox = new JComboBox<>(textProvider.getAvailableModes());
	controlPanel.add(difficultyComboBox);

	// Группа радиокнопок для выбора количества слов
	wordCountGroup = new RadioButtonsGroup();
	wordCountGroup.addButton(controlPanel, "10", 10, ThemeManager.DARK_TEXT_ACTIVE,
		ThemeManager.DARK_TEXT_INACTIVE);
	wordCountGroup.addButton(controlPanel, "25", 25, ThemeManager.DARK_TEXT_ACTIVE,
		ThemeManager.DARK_TEXT_INACTIVE);
	wordCountGroup.addButton(controlPanel, "50", 50, ThemeManager.DARK_TEXT_ACTIVE,
		ThemeManager.DARK_TEXT_INACTIVE);
	wordCountGroup.addButton(controlPanel, "75", 75, ThemeManager.DARK_TEXT_ACTIVE,
		ThemeManager.DARK_TEXT_INACTIVE);
	wordCountGroup.addButton(controlPanel, "100", 100, ThemeManager.DARK_TEXT_ACTIVE,
		ThemeManager.DARK_TEXT_INACTIVE);

	add(controlPanel, BorderLayout.NORTH);

	// 2. Центральная панель с текстом (CENTER)
	var gbl_centerPanel = new GridBagLayout();
	gbl_centerPanel.rowWeights = new double[] { 0.0, 1.0 };
	gbl_centerPanel.columnWeights = new double[] { 1.0 };
	centerPanel = new JPanel(gbl_centerPanel);
	getContentPane().add(centerPanel, BorderLayout.CENTER);

	// Input labels
	dynamicLabelInput = new DynamicTextLabel(7, 2, new FlowLayout(FlowLayout.CENTER));
	var gbc = new GridBagConstraints();
	gbc.insets = new Insets(0, 50, 0, 0);
	gbc.weighty = 0.6;
	gbc.anchor = GridBagConstraints.SOUTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	centerPanel.add(dynamicLabelInput, gbc);

	panel = new JPanel();
	var gbc_panel = new GridBagConstraints();
	gbc_panel.weighty = 0.4;
	gbc_panel.anchor = GridBagConstraints.NORTH;
	gbc_panel.insets = new Insets(0, 0, 0, 0);
	gbc_panel.fill = GridBagConstraints.HORIZONTAL;
	gbc_panel.gridx = 0;
	gbc_panel.gridy = 1;
	centerPanel.add(panel, gbc_panel);
	panel.setLayout(new GridLayout(0, 2, 0, 0));

	// HistoryLabels
	dynamicLabelHistory = new DynamicTextLabel(7, 7, new FlowLayout(FlowLayout.RIGHT));
	panel.add(dynamicLabelHistory);
	dynamicLabelHistory.setAlignmentX(Component.RIGHT_ALIGNMENT);
	dynamicLabelHistory.setAlignmentY(Component.TOP_ALIGNMENT);
	FlowLayout flowLayout = (FlowLayout) dynamicLabelHistory.getLayout();
	flowLayout.setHgap(0);
	flowLayout.setVgap(0);

	// InputTextArea
	inputTextArea = new JTextArea();

	// Применяем DocumentFilter для запрета удаления символов
	// ((AbstractDocument) inputTextArea.getDocument()).setDocumentFilter(new
	// NonEditableDocumentFilter());

	// Запрещаем удаление текста
	inputTextArea.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "none");
	inputTextArea.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "none");
	inputTextArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "none");

	inputTextArea.setOpaque(false);
	panel.add(inputTextArea);
	inputTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
	inputTextArea.setAlignmentY(Component.TOP_ALIGNMENT);
	inputTextArea.setForeground(Color.WHITE);
	inputTextArea.setFont(new Font("Arial", Font.BOLD, 24));

	inputTextArea.setFocusable(true); // Передаем фокус на текстовое поле
	inputTextArea.requestFocusInWindow();

	// Изменяем цвет курсора
	var caret = inputTextArea.getCaret();
	inputTextArea.setCaretColor(Color.RED);
	caret.setVisible(true);

	// 3. Панель статистики
	statsPanel = new JPanel(new FlowLayout());

	CPMLabel = new JLabel("Скорость: 0 CPM");
	accuracyLabel = new JLabel("Точность: 0%");
	WPMLabel = new JLabel("Скорость: 0 WPM");

	statsPanel.add(CPMLabel);
	statsPanel.add(accuracyLabel);
	statsPanel.add(WPMLabel);
	getContentPane().add(statsPanel, BorderLayout.SOUTH);
    }

    /**
     * 
     */
    private void initEvents() {
	// TODO: Добавить обработчики событий, таймеры и логику работы программы

	// Обработчики событий
	startButton.addActionListener(e -> startTraining());
	stopButton.addActionListener(e -> stopTraining());
	resetButton.addActionListener(e -> resetTraining());

	wordCountGroup.addListener_SelectedIdChangedEvent(
		e -> textProvider.setCntWords(Integer.parseInt(e.getActionCommand())));

	// Обработчик ввода текста
	inputTextArea.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent e) {
		var enteredChar = e.getKeyChar();
		// var code = e.getKeyCode(); || code == 0 || code == KeyEvent.VK_DELETE || code
		// == KeyEvent.VK_BACK_SPACE
		isInvalidLastChar = !isValidInput(enteredChar);
		if (isInvalidLastChar) {
		    e.consume(); // Отменяем ввод недопустимого символа
		}
		totalChars++;

		// if (!isInvalidLastChar) {
		processInput(enteredChar);
		// }
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
		// Не используется
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		// if (isRunning) {
		// char enteredChar = e.getKeyChar();
		// if (enteredChar != KeyEvent.CHAR_UNDEFINED) { // Обработка обычных символов
		// processInput(enteredChar);
		// }
		// }
	    }
	});

	difficultyComboBox.addActionListener(e -> updateDifficulty((String) difficultyComboBox.getSelectedItem()));
    }

    /**
     * 
     */
    private void startTraining() {
	startTime = System.currentTimeMillis();

	// Сбрасываем счетчики
	correctChars = 0;
	totalChars = 0;
	totalwordsCount = 0;

	iteratorChars = 0;
	iteratorWords = 0;

	inputTextArea.setText("");
	inputTextArea.setEnabled(true);
	inputTextArea.requestFocusInWindow();

	updateTextHandler(); // Генерируем новый текст при старте
    }

    /**
     * 
     */
    private void stopTraining() {
	inputTextArea.setEnabled(false);
	updateStats();
    }

    /**
     * 
     */
    private void resetTraining() {
	stopTraining();
	dynamicLabelHistory.resetText();
	startTraining();
    }

    /**
     * 
     * @param enteredChar
     */
    private void processInput(char enteredChar) {
	if (!isInvalidLastChar) {
	    iteratorChars++;

	    // Проверяем, является ли введенный символ пробелом
	    if (enteredChar == ' ') {

		inputTextArea.setEnabled(false);
		inputTextArea.setText("");
		dynamicLabelHistory.pushText(currentText.get(iteratorWords));

		correctChars += iteratorChars;
		iteratorChars = 0;
		dynamicLabelInput.next();

		iteratorWords++;
		totalwordsCount++; // Если пробел - увеличиваем счетчик слов

		inputTextArea.setEnabled(true);
	    }
	}

	updateStats();

	// Если весь текст введен правильно, генерируем новый
	if (iteratorWords == currentText.size()) {
	    updateText();
	}

	inputTextArea.requestFocusInWindow(); // Возвращаем фокус
    }

    /**
     * 
     */
    private void updateText() {
	inputTextArea.setEnabled(false);
	dynamicLabelInput.fadeOut(300);
	timer.start();
    }

    /**
     * 
     */
    private void updateTextHandler() {
	currentText = Arrays.asList(textProvider.generateText().split(" "));
	dynamicLabelInput.setText(currentText);

	dynamicLabelInput.fadeIn(300);

	iteratorWords = 0;

	inputTextArea.requestFocusInWindow();
	centerPanel.repaint(); // Обновляем панель

	inputTextArea.setEnabled(true);
    }

    /**
     * 
     */
    private void updateStats() {

	var _correctChars = iteratorChars + correctChars;

	long elapsedTime = System.currentTimeMillis() - startTime;
	double minutes = (double) elapsedTime / (60 * 1000);
	double cpm = (minutes > 0) ? _correctChars / minutes : 0; // Символов в минуту
	double accuracy = (totalChars > 0) ? (double) _correctChars / totalChars * 100 : 0;
	double wpm = totalwordsCount / minutes; // Слова в минуту

	CPMLabel.setText(String.format("Speed: %.0f CPM", cpm));
	WPMLabel.setText(String.format("Speed: %.0f WPM", wpm));
	accuracyLabel.setText(String.format("Accuracy: %.0f%%", accuracy));
    }

    /*
     * 
     * @param enteredChar
     */
    private boolean isValidInput(char enteredChar) {
	var currWord = currentText.get(iteratorWords);
	return iteratorChars == currWord.length() && enteredChar == ' ' // Или введен конец слова
		|| iteratorChars < currWord.length() && enteredChar == currWord.charAt(iteratorChars); // Или символ
												       // совпадает
    }

    /**
     * 
     * @param mode
     */
    private void updateDifficulty(String mode) {
	textProvider.setMode(mode);
    }
}