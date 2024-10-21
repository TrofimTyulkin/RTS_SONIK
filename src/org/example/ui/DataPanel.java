package org.example.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DataPanel extends JPanel {
    DefaultTableModel model;//Модель таблицы
    ArrayList<Double> capacityBattery;

    ArrayList<Double> produce;

    public DataPanel(ArrayList<Double> capacityBattery, ArrayList<Double> produce) {
        JTable table = new JTable(model);
        this.produce = produce;
        this.capacityBattery = capacityBattery;

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //setLayout(new GridLayout(10,4));
//        for (int i = 1; i<=10; i++){
//            for (int j = 1; j<=4; j++) {
//                add(new JLabel(i + " " + j));
//                System.out.println(i + " " + j);
//            }
//        }
        DefaultCategoryDataset voltageDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset capacityDataset = new DefaultCategoryDataset();

        for (int i = 0; i < capacityBattery.size(); i++) {
            capacityDataset.addValue(capacityBattery.get(i), "Емкость батареи", "1 сек");
        }

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
        currentDataset.addValue(1.5, "Датчик 1", "4 сек");

        currentDataset.addValue(0.8, "Датчик 2", "1 сек");
        currentDataset.addValue(1.1, "Датчик 2", "2 сек");
        currentDataset.addValue(1.3, "Датчик 2", "4 сек");



        // Создаем вторую ось Y (для силы тока)
        NumberAxis secondAxis = new NumberAxis("Сила тока (Амперы)");
        plot.setRangeAxis(1, secondAxis);

        // Добавляем второй набор данных на второй оси Y
        plot.setDataset(1, currentDataset);
        plot.mapDatasetToRangeAxis(1, 1);  // Указываем, что данные 1-й группы идут на вторую ось (ось 1)

        // Настраиваем отображение второго набора данных (сила тока)
        plot.setRenderer(1, plot.getRenderer(0));  // Используем тот же рендерер для обеих осей
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
        //setSize(1350,600);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(800, 500));
        add(panel);

        Timer timer = new Timer(500, e -> {
            //setCapacityBattery();
        });
        timer.start();


        //add(new DrawGraph(a));
    }
    public void setCapacityBattery(ArrayList<Double> capacityBattery) {
        this.capacityBattery = capacityBattery;
    }

    public void setProduce(ArrayList<Double> produce) {
        this.produce = produce;
    }
}
