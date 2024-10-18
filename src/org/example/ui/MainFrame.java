package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private double angle = 0; // Угол поворота лопастей
    JPanel cardPanel;
    CardLayout cardLayout;
    public MainFrame() throws HeadlessException {
        super("TEST");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);

        Animation animation = new Animation();
        Animation animation2 = new Animation();

        JPanel test = new DataPanel();
        //cardPanel.add(test, "test");
        //JPanel mainPanel = new MainPanel();
        //cardPanel.add(mainPanel, "main");

        // Таймер для обновления изображения каждые 16 миллисекунд (~60 FPS)
//        timer = new Timer(16, e -> {
//            angle += 0.05; // Изменяйте это значение для изменения скорости вращения
//            repaint();
//        });
//        timer.start();

        //Начальная настройка окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        setSize(1150, 600);
        setResizable(true);
        setLocationRelativeTo(null);//типо в центр экрана кидаем


        GridBagConstraints gbc = new GridBagConstraints();

        JButton switchButton = new JButton("Controls");
        JButton switchButton1 = new JButton("ANIMATION");
        switchButton.addActionListener(e -> cardLayout.show(cardPanel, "test"));
        switchButton1.addActionListener(e -> cardLayout.show(cardPanel, "main"));

        Container c = new Container();
        c.setLayout(new GridLayout(1,2));
        c.add(switchButton1);
        c.add(switchButton);
        add(c, BorderLayout.NORTH);


//        Container container = new Container();//контейнер, в котором наши поля ввода и кнопка будут
//        container.setLayout(new GridLayout(1, 2));//тип расположения элементов в контейнере
//        JLabel label = new JLabel(String.valueOf(LocalDateTime.now().toLocalDate()) +' ' + String.valueOf(LocalDateTime.now().toLocalTime()).split("\\.")[0]);
//        container.add(label);
//
//        SpringLayout layout = new SpringLayout();
//        setLayout(layout);
//
////        gbc.gridx = 0;
////        gbc.gridy = 0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        //gbc.fill = GridBagConstraints.VERTICAL;
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.weightx = 1.0f;
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        add(container);
//        //animation.setSize(100,100);
//        int padding = 20;
//        int paddingBlade = 350;
////        System.out.println((getSize().height/2) - animation.getBladeLength());
////        System.out.println((getSize().height/2) + animation.getBladeLength());
//        add(animation);
//        layout.putConstraint(SpringLayout.WEST, animation, paddingBlade, SpringLayout.WEST, getContentPane());
//        layout.putConstraint(SpringLayout.EAST, animation, paddingBlade+animation.getBladeLength()*2, SpringLayout.WEST, getContentPane());
//        layout.putConstraint(SpringLayout.NORTH, animation, padding, SpringLayout.WEST, getContentPane());
//        layout.putConstraint(SpringLayout.SOUTH, animation, animation.getBladeLength()*2 + padding, SpringLayout.WEST, getContentPane());
////        add(animation2);
////        layout.putConstraint(SpringLayout.WEST, animation2, paddingBlade+animation.getBladeLength()*2 + 300, SpringLayout.WEST, getContentPane());
////        layout.putConstraint(SpringLayout.EAST, animation2, paddingBlade+animation.getBladeLength()*2 + 300 + animation.bladeLength + animation.getBladeLength()*2, SpringLayout.WEST, getContentPane());
////        layout.putConstraint(SpringLayout.NORTH, animation2, padding, SpringLayout.WEST, getContentPane());
////        layout.putConstraint(SpringLayout.SOUTH, animation2, animation.getBladeLength()*2 + padding, SpringLayout.WEST, getContentPane());
//
//        Container metricsContainer = new Container();
//        metricsContainer.setLayout(new GridLayout(3,1));
//
//        data = new JLabel();
//        data1 = new JLabel();
//
//        metricsContainer.add(data);
//        metricsContainer.add(data1);
////        metricsContainer.add(new JLabel(String.valueOf(Generator.generateVolt(200))));
//
//
//
//        layout.putConstraint(SpringLayout.WEST, metricsContainer, paddingBlade, SpringLayout.WEST, getContentPane());
//        layout.putConstraint(SpringLayout.EAST, metricsContainer, paddingBlade+animation.getBladeLength()*2, SpringLayout.WEST, getContentPane());
//        layout.putConstraint(SpringLayout.NORTH, metricsContainer, animation.getBladeLength()*2 + padding, SpringLayout.WEST, getContentPane());
//        //layout.putConstraint(SpringLayout.SOUTH, metricsContainer, animation.getBladeLength()*2 + padding, SpringLayout.WEST, getContentPane());
//
//        add(metricsContainer);
//
//        layout.putConstraint(SpringLayout.NORTH, container, padding, SpringLayout.WEST, getContentPane());
//
//        Timer timer2 = new Timer(1000, e -> label.setText(String.valueOf(LocalDateTime.now().toLocalDate()) +' ' + String.valueOf(LocalDateTime.now().toLocalTime()).split("\\.")[0]));
//        timer2.start();
//
//        Timer timer3 = new Timer(300, e -> {
//            data.setText(String.valueOf(Data.getLastData()));
//            //System.err.println(Data.getLastData());
//            data1.setText(String.valueOf(Data.getLastData()));
//        });
//        timer3.start();
//        timer2.start();

        setVisible(true);
    }
    public void paint(Graphics g) {
        super.paint(g);//хз зачем

//        g.fill3DRect(300,300, 50, 50, true);
//        g.draw3DRect(300,300, 50, 50, true);



        //g.setColor(Color.magenta);
        Font f = new Font("Arial", Font.ITALIC, 20);//шрифтик красивый
        g.setFont(f);//задали рисовалке шрифт
        //g.drawString(String.valueOf(LocalDateTime.now().toLocalDate()) +' ' + String.valueOf(LocalDateTime.now().toLocalTime()).split("\\.")[0], 100,100);
//        try {
//            Thread.sleep(1000);
//            repaint();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


//
//            for (int i = 0; i< out.length-1; i++){
//                g.drawString(out[i], 10,180 + 24*i);
//            }
    }
    public JPanel getPanel(){
        return cardPanel;
    }

    public CardLayout getLayout(){
        return cardLayout;
    }


}
