package com.ICS499.Application.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ICS499.Application.model.BasicFoodItem;
import com.ICS499.Application.model.Menu;
import static com.ICS499.Application.service.DatabaseImplementation.closeQuietly;

public class MenuService  {

    Connection connection;
    public MenuService(Connection connection) {
        this.connection = connection;
    }

    public Menu getMenu(int restaurant_id) {
        Menu menu = new Menu();
        try {
            String sql = "SELECT * FROM food_items WHERE resturant_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, restaurant_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String desc = resultSet.getString("description");
                String cat = resultSet.getString("category");
                float price = resultSet.getFloat("price");

                BasicFoodItem food = new BasicFoodItem(name, desc, cat, price);
                food.setID(id);
                menu.addItem(food);
            }

            closeQuietly(resultSet);
            closeQuietly(preparedStatement);
        } catch (SQLException ex) {
            // ex.printStackTrace();
        }
        return menu;
    }

}
