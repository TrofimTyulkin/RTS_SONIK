package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    JPanel cardPanel;
    CardLayout cardLayout;
    public MainFrame() throws HeadlessException {
        super("TEST");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);

        DataPanel dataPanel = new DataPanel(new ArrayList<Double>(), new ArrayList<Double>());
        cardPanel.add(dataPanel, "dataPanel");
        JPanel mainPanel = new MainPanel();
        cardPanel.add(mainPanel, "main");
        cardLayout.show(cardPanel, "main");

        //Начальная настройка окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(1150, 600);
        setResizable(true);
        setLocationRelativeTo(null);//типо в центр экрана кидаем


        JButton switchButton = new JButton("Controls");
        JButton switchButton1 = new JButton("ANIMATION");
        switchButton.addActionListener(e -> cardLayout.show(cardPanel, "dataPanel"));
        switchButton1.addActionListener(e -> cardLayout.show(cardPanel, "main"));

        Container c = new Container();
        c.setLayout(new GridLayout(1,2));
        c.add(switchButton1);
        c.add(switchButton);
        add(c, BorderLayout.NORTH);

        setVisible(true);
    }
}
