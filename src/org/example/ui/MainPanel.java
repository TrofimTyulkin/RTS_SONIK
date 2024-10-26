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
    Data data;
    public MainPanel(Data data) throws HeadlessException {
        this.data = data;

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
        btnContainer.setLayout(new GridLayout(3,1));

        JTextArea area = new JTextArea(10, 30);
        area.setEditable(false);
        JScrollPane textContainer = new JScrollPane(area);

        Indicator indicatorErrorBattery = new Indicator();
        Indicator indicatorErrorPanel = new Indicator();

        setPadding(layout, 400, 0, 250, 0, metricsContainer, this);

        setPadding(layout, 0, -1, 0, -1, textContainer, this);
        //для штуки которая хранит в себе логи необходим кастомный размер, поэтому не использую метод setPadding()
        layout.putConstraint(
                SpringLayout.WEST, textContainer, -1 - 450, SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.NORTH, textContainer, -1 - 200, SpringLayout.SOUTH, this);

        JLabel labelErrorPanel = new JLabel("Ошибка в контроллере панели");
        JLabel labelErrorBattery = new JLabel("Ошибка в контроллере батареи");

        setPadding(layout, 0, 500, 350, 0, indicatorErrorBattery, this);
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

        //Data data = new Data();//Дата хранит в себе данные по панели

        JButton button = new JButton("Перезапустить контроллер батареи");
        button.addActionListener(e-> data.setErrorRecharge(false));

        JButton button1 = new JButton("Перезапустить контроллер панели");
        button1.addActionListener(e-> data.setErrorVoltage(false));

        JButton button2 = new JButton("Отключить нахрен все");
        button2.addActionListener(e-> {
            if (data.getTimer().isRunning()) {
                data.getTimer().stop();
            } else {
                data.getTimer().start();
            }
        });

        btnContainer.add(button);
        btnContainer.add(button1);
        btnContainer.add(button2);

        setPadding(layout, 0, -1, 10, 0, btnContainer, this);
        add(btnContainer);

        JSlider slider3 = new JSlider(0, 100, 20);
        add(slider3);
        setPadding(layout, 1, 0, 30, 0, slider3, this);
        slider3.addChangeListener(e->{
            //System.out.println(slider3.getValue());
            data.setProduce(slider3.getValue());
        });

        Timer timer2 = new Timer(1000, e ->
                label.setText(String.valueOf(LocalDateTime.now().toLocalDate()) +' ' + String.valueOf(LocalDateTime.now().toLocalTime()).split("\\.")[0]));
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
            area.setForeground(Color.black);

            if (data.getLastDataIsLowCapacity()){
                //area.setFont(new Font("Arial", Font.PLAIN, 12));
                area.setForeground(Color.yellow);
                area.append(formattedTime + "   Низкий заряд батареи. Потребители отключены." + "\n");
                System.out.println(area.getForeground());
            }
            if(!data.getLastDataIsCharge()){
                area.setForeground(Color.red);
                area.append(formattedTime + "   Батарея не заряжается." + "\n");
            }
            if(data.getLastDataIsOverCharge()){
                area.append(formattedTime + "   Батарея перезаряжена! Электролит кипит." + "\n");
            }
            if(data.isErrorVoltage()){
                area.setForeground(Color.red);
                area.append(formattedTime + "   Ошибка контроллера: неверное выходное напряжение." + "\n");
                System.out.println("error "+area.getForeground());
                indicatorErrorPanel.setColor(Color.red);
            } else {
                indicatorErrorPanel.setColor(Color.green);
                area.setForeground(Color.red);
                }
            if(data.isErrorRecharge()){
                area.setForeground(Color.red);
                area.append(formattedTime + "   Ошибка батареи. Перезарядка." + "\n");
                indicatorErrorBattery.setColor(Color.red);
            } else {
                indicatorErrorBattery.setColor(Color.green);
                area.setForeground(Color.red);
                }
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

        g2d.setColor(Color.BLACK);
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
        //g2d.drawPolygon(xPoints, yPoints, xPoints.length);
        //g2d.fillPolygon(xPoints1, yPoints1, xPoints.length);

        int[] xPointsSolarPanel = new int[]{20, 40, 120, 100};
        int[] yPointsSolarPanel = new int[]{180, 130, 130, 180};
        g2d.drawPolygon(xPointsSolarPanel, yPointsSolarPanel, yPointsSolarPanel.length);

        g2d.setColor(Color.green);
        g2d.drawPolyline(new int[]{110, 150, 150}, new int[]{155, 155, 180}, 3);
        g2d.setColor(Color.BLACK);
        //g2d.drawPolygon(new int[]{130, 130, 200, 200}, new int[]{220, 180, 180, 220}, 4);
        int batteryTop = 220;
        int batteryHeight = 40;
        int batteryBottom = batteryTop + batteryHeight;
        g2d.drawRect(130,batteryTop, 70, batteryHeight);
        Double percent = data.getLastDataCapacityBattery()/48*100;
        if (percent > 100){
            percent = 100.0;
            g2d.setColor(Color.yellow);
        }else g2d.setColor(Color.green);
        if (data.getLastDataCapacityBattery() > 50) g2d.setColor(Color.red);
        int fillHeight = (int) (batteryHeight * (percent / 100.0));
        g2d.fillRect(131, batteryBottom - fillHeight+1, 69, fillHeight-1);
        //g2d.clipRect(0, this.getHeight() - fillHeight, this.getWidth(), fillHeight);

        // Заполняем молнию цветом
        //g2d.fill(lightning);

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
