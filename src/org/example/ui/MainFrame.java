package org.example.ui;

import org.example.Generator.Data;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    JPanel cardPanel;
    CardLayout cardLayout;
    Data data;
    public MainFrame(Data data) throws HeadlessException {
        super("TEST");
        this.data = data;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);

        //MenuBar menuBar = new MenuBar();


        DataPanel dataPanel = new DataPanel(data);
        cardPanel.add(dataPanel, "dataPanel");
//        MoneyPanel moneyPanel = new MoneyPanel(data);
        String[] head = new String[]{"", "Заработано всего", "Простои по батареи", "Простои по панели", "Количество проданного", "Итого"};
        String[][] test = new String[][]{
                new String[]{"", "Заработано всего", "Простои по батареи", "Простои по панели", "head2", "head2"},
                new String[]{" ", "head2", String.valueOf(data.getLastDataMoneyErrorBattery()), String.valueOf(data.getLastDataMoneyErrorPanel())},
                new String[]{"body1", "body1", "body1", "body1"},
                new String[]{"body1", "body2", "body1", "body1"},
                new String[]{"body1123", "body2", "body1", "body1"},
                new String[]{"body1123", "body2", "body1", "body1"},
                new String[]{"123", "body2", "head2", "head2"}
        };

       Table_form_example moneyPanel = new Table_form_example(test);

        cardPanel.add(moneyPanel, "moneyPanel");
        JPanel mainPanel = new MainPanel(data);
        cardPanel.add(mainPanel, "main");
        cardLayout.show(cardPanel, "main");

        JMenu chart = new JMenu("Графики");
        chart.addActionListener(e -> {
            cardLayout.show(cardPanel, "dataPanel");
            System.err.println("chart");
        });
        JMenu main = new JMenu("Главное окно");
        main.addActionListener(e -> cardLayout.show(cardPanel, "main"));
        JMenuBar menu = new JMenuBar();
        menu.add(main);
        menu.add(chart);
        setJMenuBar(menu);
        //Начальная настройка окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(1150, 600);
        setResizable(true);
        setLocationRelativeTo(null);//типо в центр экрана кидаем


        JButton switchButton = new JButton("Графики");
        JButton switchButton1 = new JButton("Главное окно");
        JButton switchButton2 = new JButton("Финансы");
        switchButton.addActionListener(e -> cardLayout.show(cardPanel, "dataPanel"));
        switchButton1.addActionListener(e -> cardLayout.show(cardPanel, "main"));
        switchButton2.addActionListener(e -> cardLayout.show(cardPanel, "moneyPanel"));

        Container c = new Container();
        c.setLayout(new GridLayout(1,3));
        c.add(switchButton1);
        c.add(switchButton);
        c.add(switchButton2);
        add(c, BorderLayout.NORTH);

        setVisible(true);
    }
}
