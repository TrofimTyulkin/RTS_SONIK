package org.example.ui;

import org.example.Generator.Data;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainPanel extends JPanel {
    JLabel dataAmperagePanel = new JLabel();
    JLabel dataVoltagePanel = new JLabel();
    JLabel dataAmperageController = new JLabel();
    JLabel dataVoltageController = new JLabel();
    JLabel dataCapacityBattery = new JLabel();
    JLabel dataVoltageBattery = new JLabel();
    JLabel dataPowerProducer = new JLabel();
    public MainPanel() throws HeadlessException {

        SpringLayout layout = new SpringLayout();
        setLayout(layout);

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

        Indicator indicatorErrorBattery = new Indicator();
        Indicator indicatorErrorPanel = new Indicator();

        setPadding(layout, 100, 0, 100, 0, metricsContainer, this);

        setPadding(layout, 0, -1, 0, -1, textContainer, this);
        //для штуки которая хранит в себе логи необходим кастомный размер, поэтому не использую метод setPadding()
        layout.putConstraint(
                SpringLayout.WEST, textContainer, -1 - 450, SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.NORTH, textContainer, -1 - 200, SpringLayout.SOUTH, this);

        JLabel labelErrorPanel = new JLabel("Ошибка в контроллере панели");
        JLabel labelErrorBattery = new JLabel("Ошибка в контроллере батареи");

        setPadding(layout, 0, 500, 200, 0, indicatorErrorBattery, this);
        setPadding(layout, 0, -1, 20, 0, indicatorErrorPanel, indicatorErrorBattery);
        setPadding(layout, 0, 15, -4, 0, labelErrorPanel, indicatorErrorPanel);
        setPadding(layout, 0, 15, -4, 0, labelErrorBattery, indicatorErrorBattery);

        add(indicatorErrorBattery);
        add(indicatorErrorPanel);
        add(labelErrorPanel);
        add(labelErrorBattery);

        metricsContainer.add(dataAmperagePanel);
        metricsContainer.add(dataAmperageController);
        metricsContainer.add(dataCapacityBattery);
        metricsContainer.add(dataVoltagePanel);
        metricsContainer.add(dataVoltageController);
        metricsContainer.add(dataPowerProducer);
        add(metricsContainer);

        add(textContainer);

        Data data = new Data();//Дата хранит в себе данные по панели

        JButton button = new JButton("Перезапустить контроллер батареи");
        button.addActionListener(e-> data.setErrorRecharge(false));

        JButton button1 = new JButton("Перезапустить контроллер панели");
        button1.addActionListener(e-> data.setErrorVoltage(false));

        btnContainer.add(button);
        btnContainer.add(button1);

        setPadding(layout, 0, -1, 10, 0, btnContainer, this);
        add(btnContainer);

        JSlider slider3 = new JSlider(0, 100, 20);
        add(slider3);
        setPadding(layout, 1, 0, 30, 0, slider3, this);
        slider3.addChangeListener(e->{
            //System.out.println(slider3.getValue());
            data.setProduce(slider3.getValue());
        });

        Timer timer2 = new Timer(1000, e -> label.setText(String.valueOf(LocalDateTime.now().toLocalDate()) +' ' + String.valueOf(LocalDateTime.now().toLocalTime()).split("\\.")[0]));
        timer2.start();


        Timer timer3 = new Timer(500, e -> {
            dataAmperagePanel.setText("Panel " + data.getLastDataAmperagePanel() + " A");
            dataVoltagePanel.setText("Panel " + data.getLastDataVoltagePanel() + " V");
            dataAmperageController.setText("Controller " + data.getLastAmperageController() + " A");
            dataVoltageController.setText("Controller " + data.getLastDataVoltController() + " V");
            dataCapacityBattery.setText("Battery " + data.getLastDataCapacityBattery() + " Wh");
            dataPowerProducer.setText("Produce"+ data.getLastDataProduce() + " Wh");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
            String formattedTime = LocalTime.now().format(formatter);

//            area.append(formattedTime + " ABOBA" + "\n");
            if (data.getLastDataIsLowCapacity()){
                area.setCaretColor(Color.yellow);
                area.append(formattedTime + "   Низкий заряд батареи. Потребители отключены." + "\n");
            }
            if(!data.getLastDataIsCharge()){
                area.setCaretColor(Color.red);
                area.append(formattedTime + "   Батарея не заряжается." + "\n");
            }
            if(data.getLastDataIsOverCharge()){
                area.setCaretColor(Color.red);
                area.append(formattedTime + "   Батарея перезаряжена! Электролит кипит." + "\n");
            }
            if(data.isErrorVoltage()){
                area.setCaretColor(Color.red);
                area.append(formattedTime + "   Ошибка контроллера: неверное выходное напряжение." + "\n");
                indicatorErrorPanel.setColor(Color.red);
            } else indicatorErrorPanel.setColor(Color.green);
            if(data.isErrorRecharge()){
                area.setCaretColor(Color.red);
                area.append(formattedTime + "   Ошибка батареи. Перезарядка." + "\n");
                indicatorErrorBattery.setColor(Color.red);
            } else indicatorErrorBattery.setColor(Color.green);
            repaint();

        });
        timer2.start();
        timer3.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Координаты центра панели

        g2d.setColor(new Color(Color.BLACK.getRGB()));
        int[] xPoints = new int[]{33, 35, 23, 38, 35, 45};
        int[] xPoints1 = new int[]{33, 35, 23, 38, 35, 45};
        for (int i = 0; i < xPoints1.length; i++) {
            xPoints1[i] +=30;
        }
        int[] yPoints = new int[]{120, 102, 102, 80, 95, 95};
        int[] yPoints1 = new int[]{120, 102, 102, 80, 95, 95};
        for (int i = 0; i < yPoints1.length; i++) {
            if (yPoints1[i]<=100){
                yPoints1[i]=100;
            }
        }
        g2d.drawPolygon(xPoints, yPoints, xPoints.length);
        g2d.fillPolygon(xPoints1, yPoints1, xPoints.length);

        int[] xPointsSolarPanel = new int[]{20, 40, 120, 100};
        int[] yPointsSolarPanel = new int[]{180, 130, 130, 180};
        g2d.drawPolygon(xPointsSolarPanel, yPointsSolarPanel, yPointsSolarPanel.length);

        //g2d.fillPolygon();
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
