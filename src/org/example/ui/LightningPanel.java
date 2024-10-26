package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class LightningPanel extends JPanel {

    private double percentage = 0.0; // Процент заполнения

    // Метод для установки процента
    public void setPercentage(double percentage) {
        this.percentage = Math.min(100.0, Math.max(0.0, percentage)); // Ограничиваем значение от 0 до 100
        repaint(); // Перерисовываем компонент
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Настройка антиалиасинга для сглаживания
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем контур молнии
        GeneralPath lightning = new GeneralPath();
        lightning.moveTo(50, 10);  // Начальная точка (x, y)
        lightning.lineTo(70, 50);  // Вершина молнии
        lightning.lineTo(40, 50);  // Вниз
        lightning.lineTo(60, 90);  // Вверх
        lightning.lineTo(30, 90);  // Вниз
        lightning.lineTo(50, 130); // Вершина
        lightning.closePath();

        // Задаем цвет молнии (контур)
        g2d.setColor(Color.BLACK);
        g2d.draw(lightning);

        // Заливка в зависимости от процента
        g2d.setColor(Color.YELLOW);

        // Ограничение для заливки по высоте
        int fillHeight = (int) (this.getHeight() * (percentage / 100.0));


        // Создаем область для заливки части молнии
        g2d.clipRect(0, this.getHeight() - fillHeight, this.getWidth(), fillHeight);

        // Заполняем молнию цветом
        g2d.fill(lightning);
    }

    // Опционально: настройка размера панели
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 150); // Размер панели
    }

    public static void main(String[] args) {
        // Создаем основное окно
        JFrame frame = new JFrame("Заполняемая молния");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создаем панель с молнией
        LightningPanel lightningPanel = new LightningPanel();

        // Создаем слайдер для изменения процента заполнения
        JSlider slider = new JSlider(0, 100, 0);
        slider.addChangeListener(e -> lightningPanel.setPercentage(slider.getValue()));

        // Добавляем панель и слайдер в окно
        frame.setLayout(new BorderLayout());
        frame.add(lightningPanel, BorderLayout.CENTER);
        frame.add(slider, BorderLayout.SOUTH);

        // Показываем окно
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

