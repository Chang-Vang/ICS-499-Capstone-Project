package edu.metrostate.service;

import edu.metrostate.model.BasicFoodItem;
import edu.metrostate.model.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.metrostate.service.DatabaseImplementation.closeQuietly;

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
