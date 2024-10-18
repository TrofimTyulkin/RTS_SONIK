package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class Animation extends JPanel implements ActionListener {

    private double angle = 0; // Угол поворота лопастей
    private Timer timer;
    int bladeLength = 70;// Радиус лопастей

    public Animation() {
        // Таймер для обновления изображения каждые 16 миллисекунд (~60 FPS)
        timer = new Timer(1600000, this);
        timer.start();
        //setSize(200,100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Координаты центра панели
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Поворачиваем лопасти
        AffineTransform old = g2d.getTransform();
        g2d.translate(centerX, centerY);
        g2d.setColor(new Color(Color.YELLOW.getRGB()));
        g2d.fillOval(-35,-35, 70,70);
        g2d.rotate(angle);

        // Рисуем 4 лопасти
        for (int i = 0; i < 12; i++) {
            g2d.fillRect(15, 0, bladeLength, 2); // Лопасть
            g2d.rotate(Math.PI / 6); // Поворот на 90 градусов для следующей лопасти
        }

        // Восстанавливаем исходное состояние трансформаций
        g2d.setTransform(old);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Увеличиваем угол поворота для анимации
        angle += 0.05; // Изменяйте это значение для изменения скорости вращения
        repaint();
    }

    public int getBladeLength(){
        System.out.println(getWidth());
        return 100;
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Windmill Animation");
//        WindmillAnimation animation = new WindmillAnimation();
//        frame.add(animation);
//        frame.setSize(400, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
}
