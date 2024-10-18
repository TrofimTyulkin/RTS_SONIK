package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class Indicator extends JPanel implements ActionListener {
    Color color;
    Timer timer;

    public Indicator() {
        // Таймер для обновления изображения каждые 16 миллисекунд (~60 FPS)
        timer = new Timer(16, this);
        timer.start();
        //setSize(200,100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
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

        //System.err.println(centerY);

        // Поворачиваем лопасти
        AffineTransform old = g2d.getTransform();
        g2d.translate(centerX, centerY);
        g2d.setColor(color);
        g2d.fillOval(5,-5, 10,10);

        // Восстанавливаем исходное состояние трансформаций
        g2d.setTransform(old);
    }
    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
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
