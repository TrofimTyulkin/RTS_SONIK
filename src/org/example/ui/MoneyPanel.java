package org.example.ui;

import org.example.Generator.Data;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MoneyPanel extends JPanel {
    Data data;
    public MoneyPanel(Data data){
        this.data = data;
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        String[][] test = new String[][]{
                new String[]{"", "Заработано всего", "Простои по батареи", "Простои по панели", "head2", "head2"},
                new String[]{" ", "head2", String.valueOf(data.getLastDataMoneyErrorBattery()), String.valueOf(data.getLastDataMoneyErrorPanel())},
                new String[]{"body1", "body1", "body1", "body1"},
                new String[]{"body1", "body2", "body1", "body1"},
                new String[]{"body1123", "body2", "body1", "body1"},
                new String[]{"body1123", "body2", "body1", "body1"},
                new String[]{"123", "body2", "head2", "head2"}
        };
        add(new Table_form_example(test));

        Container container = new Container();//контейнер для вывода даты
        container.setLayout(new GridLayout(1, 1));//тип расположения элементов в контейнере

        JLabel label = new JLabel(String.valueOf(LocalDateTime.now().toLocalDate()) +' ' + String.valueOf(LocalDateTime.now().toLocalTime()).split("\\.")[0]);
        container.add(label);
        setPadding(layout, 5, 0, 1, 0, container, this);
        add(container);


        Container metricsContainer = new Container();
        metricsContainer.setLayout(new GridLayout(2,3));

        Container btnContainer = new Container();
        btnContainer.setLayout(new GridLayout(2,1));

        JTextArea area = new JTextArea(10, 30);
        area.setEditable(false);
        JScrollPane textContainer = new JScrollPane(area);


        setPadding(layout, 100, 0, 100, 0, metricsContainer, this);

        setPadding(layout, 0, -1, 0, -1, textContainer, this);
        //для штуки которая хранит в себе логи необходим кастомный размер, поэтому не использую метод setPadding()
        layout.putConstraint(
                SpringLayout.WEST, textContainer, -1 - 450, SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.NORTH, textContainer, -1 - 200, SpringLayout.SOUTH, this);


        add(metricsContainer);

        add(textContainer);

        setPadding(layout, 0, -1, 10, 0, btnContainer, this);
        add(btnContainer);


        Timer timer2 = new Timer(1000, e ->
                label.setText(String.valueOf(LocalDateTime.now().toLocalDate()) +' ' + String.valueOf(LocalDateTime.now().toLocalTime()).split("\\.")[0]));
        timer2.start();



        Timer timer3 = new Timer(500, e -> {
//            String[][] test = new String[][]{
//                    new String[]{"", "Заработано всего", "Простои по батареи", "Простои по панели", "head2", "head2"},
//                    new String[]{" ", "head2", String.valueOf(data.getLastDataMoneyErrorBattery()), String.valueOf(data.getLastDataMoneyErrorPanel())},
//                    new String[]{"body1", "body1", "body1", "body1"},
//                    new String[]{"body1", "body2", "body1", "body1"},
//                    new String[]{"body1123", "body2", "body1", "body1"},
//                    new String[]{"body1123", "body2", "body1", "body1"},
//                    new String[]{"123", "body2", "head2", "head2"}
//            };
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
            String formattedTime = LocalTime.now().format(formatter);
            area.setForeground(Color.black);
            area.append(formattedTime + "   test." + "\n");

            repaint();

        });
        timer2.start();
        timer3.start();


    }
    public void setPadding (SpringLayout layout, int westPadding, int eastPadding, int northPadding, int southPadding, Component c1, Component c2){
        if(westPadding!=0) layout.putConstraint(SpringLayout.WEST, c1, westPadding, SpringLayout.WEST, c2);
        if(eastPadding!=0) layout.putConstraint(SpringLayout.EAST, c1, -eastPadding, SpringLayout.EAST, c2);
        if(northPadding!=0) layout.putConstraint(SpringLayout.NORTH, c1, northPadding, SpringLayout.NORTH, c2);
        if(southPadding!=0) layout.putConstraint(SpringLayout.SOUTH, c1, -southPadding, SpringLayout.SOUTH, c2);
        if(westPadding==-1) layout.putConstraint(SpringLayout.WEST, c1, 0, SpringLayout.WEST, c2);
        if(eastPadding==-1) layout.putConstraint(SpringLayout.EAST, c1, 0, SpringLayout.EAST, c2);
        if(northPadding==-1) layout.putConstraint(SpringLayout.NORTH, c1, 0, SpringLayout.NORTH, c2);
        if(southPadding==-1) layout.putConstraint(SpringLayout.SOUTH, c1, 0, SpringLayout.SOUTH, c2);
    }
}
