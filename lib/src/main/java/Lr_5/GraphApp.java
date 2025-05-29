package Lr_5;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 
 */
public class GraphApp extends JFrame {
    private List<OperationStats> statsList;

    /**
     * 
     * @param statsList
     */
    public GraphApp(List<OperationStats> statsList) {
	this.statsList = statsList;
	initUI();
    }

    /**
     * 
     */
    private void initUI() {
	setTitle("Графики производительности коллекций");
	setSize(800, 600);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	// Создаем панель с вкладками
	var tabbedPane = new JTabbedPane();

	// Создаем панели для графиков ArrayList и HashMap
	tabbedPane.addTab("ArrayList", createCollectionPanel(statsList, "ArrayList"));
	tabbedPane.addTab("HashMap", createCollectionPanel(statsList, "HashMap"));

	// Добавляем вкладки на фрейм
	setContentPane(tabbedPane);
	// add(tabbedPane);

	ThemeManager.applyDarkTheme(this);
    }

    /**
     * 
     * @param data
     * @param collectionType
     * @return
     */
    private JPanel createCollectionPanel(List<OperationStats> data, String collectionType) {
	var panel = new JPanel(new GridLayout(1, 2)); // 2 столбца для add и remove

	// Фильтрация по типу коллекции
	var databyType = data.stream().filter(x -> x.getCollectionType().equals(collectionType))
		.collect(Collectors.toList());

	// Группировка данных по типу операции
	// var dataGroup =
	// databyType.stream().collect(Collectors.groupingBy(OperationStats::getOperationType));

	// Формирование графиков по каждой из операций
	// dataGroup.forEach((operationType, StatsCollection) -> {
	// panel.add(createChartPanel(StatsCollection, collectionType, operationType));
	// });

	panel.add(createChartPanel(databyType, collectionType));

	return panel;
    }

    /**
     * 
     * @param data
     * @param collectionType
     * @param operationType
     * @return
     */
    private ChartPanel createChartPanel(List<OperationStats> data, String collectionType) {
	// String operationType <<<<<<<<<<<< INPUT

	var totalTimeDataset = new DefaultCategoryDataset();
	var medianTimeDataset = new DefaultCategoryDataset();

	// for (var stats : data) {
	// totalTimeDataset.addValue(stats.getTotalTime(), "TT",
	// String.valueOf(stats.getSize()));
	// medianTimeDataset.addValue(stats.getMedianTime(), "MT",
	// String.valueOf(stats.getSize()));
	// }

	for (var stats : data) {
	    totalTimeDataset.addValue(stats.getTotalTime(), "TotalTime " + stats.getOperationType(),
		    String.valueOf(stats.getSize()));
	    medianTimeDataset.addValue(stats.getMedianTime(), "MedianTime " + stats.getOperationType(),
		    String.valueOf(stats.getSize()));
	}

	var chart = createChart(collectionType, totalTimeDataset);
	configurePlot(chart, medianTimeDataset);

	return new ChartPanel(chart);
    }

    /**
     * Создаие графика на основе набора данных общего времени
     * 
     * @param collectionType
     * @param totalTimeDataset
     * @return
     */
    private JFreeChart createChart(String collectionType, DefaultCategoryDataset Dataset) {

	// String operationType, <<<<<<<<<<< INPUT

	return ChartFactory.createLineChart("Производительность " + collectionType, // collectionType + " - " +
										    // operationType, // Заголовок
		"Количество элементов", // Ось X
		"Total Time (ns)", // Ось Y (основная)
		Dataset, // Данные для общего времени
		PlotOrientation.VERTICAL, // Ориентация
		true, // Легенда
		true, // Подсказки
		false // URL
	);
    }

    /**
     * 
     * @param chart
     * @param medianTimeDataset
     */
    private void configurePlot(JFreeChart chart, DefaultCategoryDataset dataset) {

	var plot = chart.getCategoryPlot();

	// Создаем вторую ось Y
	NumberAxis medianAxis = new NumberAxis("Median Time (ns)");
	plot.setRangeAxis(1, medianAxis);
	plot.setRangeAxisLocation(1, org.jfree.chart.axis.AxisLocation.BOTTOM_OR_RIGHT); // Размещаем ось справа

	// Добавляем вторую ось на график
	plot.setDataset(1, dataset);
	plot.mapDatasetToRangeAxis(1, 1); // Связываем с дополнительной осью

	setStyle(plot);
    }

    /**
     * Настраивает внешний вид графика
     * 
     * @param plot
     */
    private void setStyle(CategoryPlot plot) {

	float hue1 = 30f / 360f;
	float saturation = 0.9f;
	float brightness = 0.9f;
	Color orangeColor = Color.getHSBColor(hue1, saturation, brightness);

	float hue2 = 0f / 360f;
	Color redColor = Color.getHSBColor(hue2, saturation, brightness);

	float hue3 = 210f / 360f;
	Color blueColor = Color.getHSBColor(hue3, saturation, brightness);

	float hue4 = 120f / 360f;
	Color greenColor = Color.getHSBColor(hue4, saturation, brightness);

	// Создаем рендерер для medianTime
	var medianTimeRenderer = new LineAndShapeRenderer();
	medianTimeRenderer.setSeriesPaint(0, redColor);
	medianTimeRenderer.setSeriesShapesVisible(0, true);
	medianTimeRenderer.setSeriesPaint(1, orangeColor);
	medianTimeRenderer.setSeriesShapesVisible(1, true);

	// Устанавливаем второй рендерер для второй серии
	plot.setRenderer(1, medianTimeRenderer);

	// Настраиваем рендерер для totalTime
	var totalTimeRenderer = (LineAndShapeRenderer) plot.getRenderer();
	totalTimeRenderer.setSeriesPaint(0, blueColor);
	totalTimeRenderer.setSeriesShapesVisible(0, true);
	totalTimeRenderer.setSeriesPaint(1, greenColor);
	totalTimeRenderer.setSeriesShapesVisible(1, true);
    }
}
