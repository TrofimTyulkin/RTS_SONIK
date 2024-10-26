package org.example;

import org.example.Generator.Data;
import org.example.ui.MainFrame;
import org.example.ui.test;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new test());
            //UIManager.setLookAndFeel( new NimbusLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainFrame(new Data());

        String url = "jdbc:sqlserver://nep;databaseName=dwh;encrypt=false;trustServerCertificate=true;";


        try (Connection conn = DriverManager.getConnection(url);) {
            System.out.print("connected");

        }catch (SQLException e){
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(url); Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
            System.out.print("connected");

            String SQL = "SELECT COLUMN_NAME, TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS";
            ResultSet rs = statement.executeQuery(SQL);
            System.out.println(rs.wasNull());

            while (rs.next()){
                System.out.println(rs.getString(1) + "  " +  rs.getString(2));
            }

            SQL = "SELECT  [Period]" +
                    "      ,[DateBegin]" +
                    "      ,[DateEnd]" +
                    "      ,[MaxF]" +
                    "      ,[ID]" +
                    "  FROM [Monf].[dbo].[PER_F_CK11]" +
                    "  where DateBegin >= '20240612' and DateEnd<= '20240614'";
            rs = statement.executeQuery(SQL);
            System.out.println(rs.wasNull());


            int i = 0;
            while (rs.next()){
                System.out.println(rs + "" + i);
                i++;
            }



        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("обшибка");
        }
        //Data data = new Data();
    }
}