package Lr_5;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.AbstractButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;

public class ThemeManager {

    public static final Color DARK_BACKGROUND = new Color(40, 40, 40);
    public static final Color DARK_TEXT = new Color(220, 220, 220);
    public static final Color DARK_ACCENT = new Color(45, 45, 45);
    public static final Color DARK_TEXT_ACTIVE = new Color(240, 240, 240);
    public static final Color DARK_TEXT_INACTIVE = new Color(150, 150, 150);

    public static void applyDarkTheme(Component component) {
	component.setBackground(DARK_BACKGROUND);
	component.setForeground(DARK_TEXT);

	if (component instanceof JPanel) {
	    ((JPanel) component).setOpaque(true); // Убедитесь, что JPanel не прозрачный
	}

	if (component instanceof JScrollPane) {
	    JScrollPane scrollPane = (JScrollPane) component;
	    scrollPane.getViewport().setBackground(DARK_BACKGROUND);
	    scrollPane.getViewport().setForeground(DARK_TEXT);
	    if (scrollPane.getHorizontalScrollBar() != null) {
		applyDarkTheme(scrollPane.getHorizontalScrollBar());
	    }
	    if (scrollPane.getVerticalScrollBar() != null) {
		applyDarkTheme(scrollPane.getVerticalScrollBar());
	    }
	    scrollPane.setBorder(null); // Убираем стандартную рамку
	}

	if (component instanceof JScrollBar) {
	    component.setBackground(DARK_ACCENT);
	    component.setForeground(DARK_TEXT); // Цвет стрелок
	}

	if (component instanceof AbstractButton) {
	    ((AbstractButton) component).setOpaque(true); // Убедитесь, что кнопка не прозрачная
	    ((AbstractButton) component).setBorderPainted(false); // Убираем рамку вокруг кнопки
	    component.setBackground(DARK_ACCENT); // Цвет фона кнопки
	}

	if (component instanceof JTextComponent) {
	    component.setBackground(DARK_ACCENT); // Цвет фона кнопки
	}

	if (component instanceof JTabbedPane) {
	    JTabbedPane tabbedPane = (JTabbedPane) component;
	    tabbedPane.setBackground(DARK_ACCENT); // Фон панели вкладок
	    tabbedPane.setForeground(DARK_TEXT); // Цвет текста вкладок

	    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
		Component tabComponent = tabbedPane.getComponentAt(i);
		applyDarkTheme(tabComponent); // Применяем тему к содержимому вкладок
	    }
	}

	if (component instanceof JMenuBar) {
	    JMenuBar menuBar = (JMenuBar) component;
	    menuBar.setBackground(DARK_ACCENT); // Фон меню бара
	    menuBar.setForeground(DARK_TEXT); // Цвет текста меню бара
	    for (int i = 0; i < menuBar.getMenuCount(); i++) {
		JMenu menu = menuBar.getMenu(i);
		applyDarkTheme(menu); // Применяем тему к каждому меню
	    }
	}

	if (component instanceof JMenu) {
	    JMenu menu = (JMenu) component;
	    menu.setBackground(DARK_ACCENT); // Фон меню
	    menu.setForeground(DARK_TEXT); // Цвет текста меню
	    for (int i = 0; i < menu.getItemCount(); i++) {
		JMenuItem menuItem = menu.getItem(i);
		if (menuItem != null) {
		    applyDarkTheme(menuItem); // Применяем тему к каждому пункту меню
		}
	    }
	}

	if (component instanceof JMenuItem) {
	    JMenuItem menuItem = (JMenuItem) component;
	    menuItem.setBackground(DARK_ACCENT); // Фон пункта меню
	    menuItem.setForeground(DARK_TEXT); // Цвет текста пункта меню
	}

	if (component instanceof Container) {
	    Container container = (Container) component;
	    for (Component child : container.getComponents()) {
		applyDarkTheme(child);
	    }
	}

	// Обработка ChartPanel
	if (component instanceof ChartPanel) {
	    ChartPanel chartPanel = (ChartPanel) component;
	    JFreeChart chart = chartPanel.getChart();
	    if (chart != null) {
		chart.setBackgroundPaint(DARK_BACKGROUND); // Фон графика
		chart.getTitle().setPaint(DARK_TEXT); // Цвет заголовка

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(DARK_BACKGROUND); // Фон области графика
		plot.setDomainGridlinePaint(DARK_TEXT); // Цвет линий сетки
		plot.setRangeGridlinePaint(DARK_TEXT);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelPaint(DARK_TEXT); // Цвет подписей оси X
		domainAxis.setLabelPaint(DARK_TEXT); // Цвет названия оси X

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setTickLabelPaint(DARK_TEXT); // Цвет подписей оси Y
		rangeAxis.setLabelPaint(DARK_TEXT); // Цвет названия оси Y

		rangeAxis = (NumberAxis) plot.getRangeAxis(1);
		rangeAxis.setTickLabelPaint(DARK_TEXT); // Цвет подписей оси Y
		rangeAxis.setLabelPaint(DARK_TEXT); // Цвет названия оси Y
	    }
	}
    }
}
