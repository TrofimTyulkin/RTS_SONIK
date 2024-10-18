package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindmillWithCardLayout {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel animationPanel;
    private JPanel controlPanel;

    public WindmillWithCardLayout() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Панель с анимацией
        animationPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Логика рисования анимации
            }
        };

        // Панель с кнопками
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JButton("Start"));
        controlPanel.add(new JButton("Stop"));

        cardPanel.add(animationPanel, "Animation");
        cardPanel.add(controlPanel, "Controls");

        JFrame frame = new JFrame("Windmill with CardLayout");
        frame.setLayout(new BorderLayout());
        frame.add(cardPanel, BorderLayout.CENTER);

        // Переключение между панелями
        JButton switchButton = new JButton("Controls");
        JButton switchButton1 = new JButton("ANIMATION");
        switchButton.addActionListener(e -> cardLayout.show(cardPanel, "Controls"));
        switchButton1.addActionListener(e -> cardLayout.show(cardPanel, "Animation"));

        Container c = new Container();
        c.setLayout(new GridLayout(1,2));
        c.add(switchButton);
        c.add(switchButton1);
        frame.add(c, BorderLayout.NORTH);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WindmillWithCardLayout::new);
    }
}
