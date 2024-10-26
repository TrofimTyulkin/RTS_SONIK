package org.example.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//Внутренние классы
class Table_form_example extends JPanel {//Графическое окно словаря
    DefaultTableModel model;//Модель таблицы
    private JTable table;//Отображение таблицы
    String[] headers;//Заголовки таблицы
    String[][] values;//Значения в таблице
    int max_length = 0;
    int[] char_amount;//нужно для автоматического масштабирования ячеек в таблице

    Table_form_example(String [][] input_table) {//Инициализация таблицы

        //находим самую длинную строку и узнаём её длину
        for (String[] lines : input_table) {
            if (max_length < lines.length) max_length = lines.length;
        }
        values = new String[input_table.length-1][max_length];
        headers = input_table[0];
        for (int i = 0; i < input_table.length-1; i++){
            values[i] = input_table[i+1];
        }
        column_details_update();
        model = new DefaultTableModel(values, headers);

        //Установка layout менеджера spring
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);


//        JButton SaveBTN = new JButton("Сохранить");
//        SaveBTN.addActionListener(e -> {Write_to_table.w(new String[][][]{input_table});});
//        springLayout.putConstraint(SpringLayout.NORTH, SaveBTN, -6, SpringLayout.NORTH, this);
//        springLayout.putConstraint(SpringLayout.WEST, SaveBTN, 6, SpringLayout.EAST, this);
//        springLayout.putConstraint(SpringLayout.SOUTH, SaveBTN, -10, SpringLayout.SOUTH, this);
//        springLayout.putConstraint(SpringLayout.EAST, SaveBTN, -6, SpringLayout.WEST, this);
//        add(SaveBTN);

        //Создание графического отображения таблицы
        table = new JTable(model);
        set_cell_bounds(char_amount);
//        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        table.setRowSelectionAllowed(false);
//        table.setColumnSelectionAllowed(false);
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //Слайдер для прокрутки экрана (на случай, если таблица не будет помещаться)
        JScrollPane scrollPane = new JScrollPane(table);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10 , SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
        //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
    }

    private void set_cell_bounds(int[] char_amount) {//Установка размеров ячеек
        table.setRowHeight(20);
        for (int i = 0; i < model.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setMinWidth(10 * char_amount[i]);
        }
    }
    private void column_details_update() {//Создаём загаловки таблицы и базовую ширину ячеек
        char_amount = new int[max_length];
        for (int i = 0; i < max_length; i++) {
            char_amount[i] = headers[i].length();
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (values[i][j].length() > char_amount[j]) {
                    char_amount[j] = values[i][j].length();
                }
            }
        }
    }
}
