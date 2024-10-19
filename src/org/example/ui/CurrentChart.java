package org.example.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;


public class CurrentChart extends ChartFactory {

    public CurrentChart(List<DefaultCategoryDataset> datasets, String title) {
        //super();


        // Создаем первый набор данных для напряжения
        DefaultCategoryDataset voltageDataset = new DefaultCategoryDataset();
        voltageDataset.addValue(5, "Датчик 1", "1 сек");
        voltageDataset.addValue(7, "Датчик 1", "2 сек");
        voltageDataset.addValue(6, "Датчик 1", "3 сек");

        voltageDataset.addValue(4, "Датчик 2", "1 сек");
        voltageDataset.addValue(6, "Датчик 2", "2 сек");
        voltageDataset.addValue(7, "Датчик 2", "3 сек");

        // Создаем первый график (для напряжения)
        JFreeChart chart = ChartFactory.createLineChart(
                "Напряжение и Сила тока",   // Заголовок графика
                "Время",                    // Ось X
                "Напряжение (Вольты)",       // Ось Y1 (напряжение)
                voltageDataset
        );



        CategoryPlot plot = chart.getCategoryPlot();

        // Создаем второй набор данных для силы тока
        DefaultCategoryDataset currentDataset = new DefaultCategoryDataset();
        currentDataset.addValue(1, "Датчик 1", "1 сек");
        currentDataset.addValue(1.2, "Датчик 1", "2 сек");
        currentDataset.addValue(1.5, "Датчик 1", "3 сек");

        currentDataset.addValue(0.8, "Датчик 2", "1 сек");
        currentDataset.addValue(1.1, "Датчик 2", "2 сек");
        currentDataset.addValue(1.3, "Датчик 2", "3 сек");

        // Создаем вторую ось Y (для силы тока)
        NumberAxis secondAxis = new NumberAxis("Сила тока (Амперы)");
        plot.setRangeAxis(1, secondAxis);

        // Добавляем второй набор данных на второй оси Y
        plot.setDataset(1, currentDataset);
        plot.mapDatasetToRangeAxis(1, 1);  // Указываем, что данные 1-й группы идут на вторую ось (ось 1)

        // Настраиваем отображение второго набора данных (сила тока)
        plot.setRenderer(1, plot.getRenderer(0));  // Используем тот же рендерер для обеих осей
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        // Добавляем график в окно
        ChartPanel chartPanel = new ChartPanel(chart);
        //setContentPane(chartPanel);
    }
}
