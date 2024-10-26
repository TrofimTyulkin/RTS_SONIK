package org.example.ui;

import org.example.Generator.Data;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DataPanel extends JPanel {
    DefaultTableModel model;//Модель таблицы
    ArrayList<Double> capacityBattery;
    ArrayList<Double> power;
    ArrayList<Double> produce;
    Data data;
    String font = "Segoe UI";

    public DataPanel(Data data) {
        this.data = data;
        JTable table = new JTable(model);

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
//
//
//
//        voltageDataset.addValue(5, "Датчик 1", "1 сек");
//        voltageDataset.addValue(7, "Датчик 1", "2 сек");
//        voltageDataset.addValue(6, "Датчик 1", "3 сек");
//
//        voltageDataset.addValue(4, "Датчик 2", "1 сек");
//        voltageDataset.addValue(6, "Датчик 2", "2 сек");
//        voltageDataset.addValue(7, "Датчик 2", "3 сек");

        // Создаем первый график (для напряжения)
/*        JFreeChart chart = ChartFactory.createLineChart(
                "Напряжение и Сила тока",   // Заголовок графика
                "Время",                    // Ось X
                "Напряжение (Вольты)",       // Ось Y1 (напряжение)
                voltageDataset
        );*/
        JFreeChart chart = ChartFactory.createLineChart(
                "Напряжение и Сила тока",   // Заголовок графика
                "Время",                    // Ось X
                "Емкость батареи (Ватт/час)",       // Ось Y1 (напряжение)
                capacityDataset,
                PlotOrientation.VERTICAL, // ориентация графика
                true, // отображать легенду
                true, // включить tooltips
                false // включить генерацию URL
        );

        chart.getLegend().setItemFont(new Font(font, Font.PLAIN, 14));

        CategoryPlot plot = chart.getCategoryPlot();

        // Создаем второй набор данных для силы тока
        DefaultCategoryDataset currentDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset powerDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset produceDataset = new DefaultCategoryDataset();
/*        currentDataset.addValue(1, "Датчик 1", "1 сек");
        currentDataset.addValue(1.2, "Датчик 1", "2 сек");
        currentDataset.addValue(1.5, "Датчик 1", "4 сек");

        currentDataset.addValue(0.8, "Датчик 2", "1 сек");
        currentDataset.addValue(1.1, "Датчик 2", "2 сек");
        currentDataset.addValue(1.3, "Датчик 2", "4 сек");*/


        // Создаем вторую ось Y (для силы тока)
        NumberAxis secondAxis = new NumberAxis("Накапливаемая мощность (Ватт/час)");
        plot.setBackgroundPaint(Color.white); // Цвет фона для области графика
        plot.setRangeGridlinePaint(Color.white);  // Цвет линий сетки по оси Y
        plot.setOutlineVisible(false);            // Отключить рамку вокруг графика
        plot.setRangeAxis(1, secondAxis);
        plot.getDomainAxis().setTickLabelFont(new Font(font, Font.PLAIN, 12)); // Шрифт для меток оси X
        plot.getRangeAxis().setTickLabelFont(new Font(font, Font.PLAIN, 12));  // Шрифт для меток оси Y
        plot.getDomainAxis().setLabelFont(new Font(font, Font.BOLD, 14));      // Шрифт для подписи оси X
        plot.getRangeAxis().setLabelFont(new Font(font, Font.BOLD, 14));       // Шрифт для подписи оси Y

        // Добавляем второй набор данных на второй оси Y
        plot.setDataset(1, currentDataset);
        plot.mapDatasetToRangeAxis(1, 1);  // Указываем, что данные 1-й группы идут на вторую ось (ось 1)

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);  // Цвет первой линии
        renderer.setSeriesPaint(1, Color.GREEN); // Цвет второй линии
        renderer.setSeriesShapesVisible(0, true);  // Включаем отображение точек для первой серии
        renderer.setSeriesShapesVisible(1, true);  // Включаем отображение точек для второй серии
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 4, 4)); // Устанавливаем форму точки для первой серии
        renderer.setSeriesShape(1, new java.awt.geom.Rectangle2D.Double(-3, -3, 4, 4)); // Устанавливаем форму точки для второй серии
        plot.setRenderer(0, renderer);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0.0, 60.0);
        rangeAxis.setAutoRange(false);

        CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();
        domainAxis.setMaximumCategoryLabelWidthRatio(100);  // Установить диапазон оси X от 0 до 100

        // Настраиваем отображение второго набора данных (сила тока)
        renderer1.setSeriesPaint(0, Color.YELLOW);  // Цвет первой линии
        plot.setRenderer(1, renderer1);  // Используем тот же рендерер для обеих осей
        plot.setDataset(1, powerDataset);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
        //setSize(1350,600);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(1100, 520));
        add(panel);
        chart.setBackgroundPaint(Color.white);
        chart.setTitle(new TextTitle("Кастомизированный график",
                new Font("Segoe UI", Font.PLAIN, 18))); // Изменение шрифта заголовка

        Timer timer = new Timer(500, e -> {
            //setCapacityBattery();
            capacityBattery = data.getDataCapacityBatteryForChart();
            power = data.getDataPowerForChart();
            produce = data.getDataProduceForChart();

            for (int i = 0; i < capacityBattery.size(); i++) {
                capacityDataset.addValue(capacityBattery.get(capacityBattery.size()-1-i), "Емкость батареи", (double)i/2 - 11 + " сек");
                //System.out.println("dataset "+capacityBattery.get(i));
                repaint();
            }
            for (int i = 0; i < power.size(); i++) {
                powerDataset.addValue(power.get(power.size()-1-i), "Накапливаемая мощность", (double)i/2 - 11 + " сек");
                //System.out.println("dataset "+capacityBattery.get(i));
                repaint();
            }
            for (int i = 0; i < produce.size(); i++) {
                powerDataset.addValue(produce.get(produce.size()-1-i), "Отдаваемая мощность", (double)i/2 - 11 + " сек");
                //System.out.println("dataset "+capacityBattery.get(i));
                repaint();
            }
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
