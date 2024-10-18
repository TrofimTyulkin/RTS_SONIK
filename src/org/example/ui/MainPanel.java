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
    public MainPanel(JFrame frame) throws HeadlessException {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        Container container = new Container();//контейнер, в котором наши поля ввода и кнопка будут
        container.setLayout(new GridLayout(1, 1));//тип расположения элементов в контейнере
        JLabel label = new JLabel(String.valueOf(LocalDateTime.now().toLocalDate()) +' ' + String.valueOf(LocalDateTime.now().toLocalTime()).split("\\.")[0]);
        container.add(label);
        setPreferredSize(new Dimension(1150, 600));


        Animation animation = new Animation();
        Animation animation2 = new Animation();

        add(container);
        //animation.setSize(100,100);
        int padding = 20;
        int paddingBlade = 350;
//        System.out.println((getSize().height/2) - animation.getBladeLength());
//        System.out.println((getSize().height/2) + animation.getBladeLength());
//        add(animation);
        layout.putConstraint(SpringLayout.WEST, animation, paddingBlade, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(SpringLayout.EAST, animation, paddingBlade+animation.getBladeLength()*2, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, animation, padding, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, animation, animation.getBladeLength()*2 + padding, SpringLayout.WEST, frame.getContentPane());
//        add(animation2);
//        layout.putConstraint(SpringLayout.WEST, animation2, paddingBlade+animation.getBladeLength()*2 + 300, SpringLayout.WEST, getContentPane());
//        layout.putConstraint(SpringLayout.EAST, animation2, paddingBlade+animation.getBladeLength()*2 + 300 + animation.bladeLength + animation.getBladeLength()*2, SpringLayout.WEST, getContentPane());
//        layout.putConstraint(SpringLayout.NORTH, animation2, padding, SpringLayout.WEST, getContentPane());
//        layout.putConstraint(SpringLayout.SOUTH, animation2, animation.getBladeLength()*2 + padding, SpringLayout.WEST, getContentPane());

        Container metricsContainer = new Container();
        metricsContainer.setLayout(new GridLayout(2,3));

        Container btnContainer = new Container();
        btnContainer.setLayout(new GridLayout(2,1));

        //textContainer.setLayout(new BorderLayout());

        JTextArea area = new JTextArea(10, 30);
        area.setEditable(false);
        JScrollPane textContainer = new JScrollPane(area);

        Indicator indicatorErrorBattery = new Indicator();
        Indicator indicatorErrorPanel = new Indicator();
        //area.setText("test");


        //metricsContainer.add(data);
        //metricsContainer.add(data1);


        layout.putConstraint(
                SpringLayout.WEST, metricsContainer, paddingBlade, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.EAST, metricsContainer, paddingBlade+animation.getBladeLength()*4, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.NORTH, metricsContainer, animation.getBladeLength()*2 + padding, SpringLayout.WEST, frame.getContentPane());

        layout.putConstraint(
                SpringLayout.WEST, textContainer, 650, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.EAST, textContainer, 940, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.NORTH, textContainer, 300, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.SOUTH, textContainer, 510, SpringLayout.WEST, frame.getContentPane());

        layout.putConstraint(
                SpringLayout.WEST, indicatorErrorBattery, 250, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.EAST, indicatorErrorBattery, 240, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.NORTH, indicatorErrorBattery, 300, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.SOUTH, indicatorErrorBattery, 320, SpringLayout.WEST, frame.getContentPane());

        layout.putConstraint(
                SpringLayout.WEST, indicatorErrorPanel, 250, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.EAST, indicatorErrorPanel, 240, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.NORTH, indicatorErrorPanel, 330, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.SOUTH, indicatorErrorPanel, 350, SpringLayout.WEST, frame.getContentPane());


        add(indicatorErrorBattery);
        add(indicatorErrorPanel);

        metricsContainer.add(dataAmperagePanel);
        metricsContainer.add(dataAmperageController);
        metricsContainer.add(dataCapacityBattery);
        metricsContainer.add(dataVoltagePanel);
        metricsContainer.add(dataVoltageController);
        metricsContainer.add(dataPowerProducer);


        add(metricsContainer);
        add(textContainer);
        Data data = new Data();

        JButton button = new JButton("Перезапустить контроллер батареи");
        button.addActionListener(e-> data.setErrorRecharge(false));

        JButton button1 = new JButton("Перезапустить контроллер панели");
        button1.addActionListener(e-> data.setErrorVoltage(false));

        JSlider slider3 = new JSlider(0, 100, 20);
        add(slider3);
        slider3.addChangeListener(e->{
            //System.out.println(slider3.getValue());
            data.setProduce(slider3.getValue());
        });



        //add(pane);

        btnContainer.add(button);
        btnContainer.add(button1);

        add(btnContainer);

        layout.putConstraint(SpringLayout.NORTH, container, padding, SpringLayout.WEST, frame.getContentPane());

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
            }else indicatorErrorPanel.setColor(Color.green);
            if(data.isErrorRecharge()){
                area.setCaretColor(Color.red);
                area.append(formattedTime + "   Ошибка батареи. Перезарядка." + "\n");
                indicatorErrorBattery.setColor(Color.red);
            }else indicatorErrorBattery.setColor(Color.green);
            repaint();

        });
        timer2.start();
        timer3.start();

/*        for(char i='a'; i<='z'; i++){
            System.out.println(i+" :"+ (int) i +".");
        }*/


        layout.putConstraint(
                SpringLayout.EAST, btnContainer, 800, SpringLayout.EAST, frame.getContentPane());
//        layout.putConstraint(
//                SpringLayout.EAST, btnContainer, paddingBlade+animation.getBladeLength()*4, SpringLayout.WEST, frame.getContentPane());
        layout.putConstraint(
                SpringLayout.NORTH, btnContainer, padding, SpringLayout.NORTH, frame.getContentPane());
        //layout.putConstraint(SpringLayout.SOUTH, metricsContainer, animation.getBladeLength()*2 + padding, SpringLayout.WEST, getContentPane());

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
}
