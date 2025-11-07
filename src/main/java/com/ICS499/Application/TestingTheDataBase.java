package com.ICS499.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestingTheDataBase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/application?createDatabaseIfNotExist=true";
        String username = "root";
        String password = "password";

        try {
            // Establish connection
            Connection conn = DriverManager.getConnection(url, username, password);
            // Create statement
            Statement stmt = conn.createStatement();
            // Execute query
            ResultSet rs = stmt.executeQuery("SELECT name, price FROM food_items"); // replace with your table

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                System.out.println("\t" + name + "\t" + price);
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}