package edu.metrostate.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.metrostate.model.Menu;
import edu.metrostate.model.Restaurant;

import static edu.metrostate.service.DatabaseImplementation.closeQuietly;

public class RestaurantService {
    Connection connection;
    public RestaurantService (Connection connection) {
        this.connection = connection;
    }

    public List<Restaurant> loadResturants() {
        List<Restaurant> r_list = new ArrayList<>();
        MenuService ms = new MenuService(connection);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM resturant");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                // get location, category, menu
                String loc = resultSet.getString("location");
                String cat = resultSet.getString("category");

                Menu menu = ms.getMenu(id);
                Restaurant restaurant = new Restaurant(name, loc, cat, menu);
                restaurant.setID(id);
                r_list.add(restaurant);
            }
            closeQuietly(resultSet);
            closeQuietly(statement);
        } catch (SQLException ex) {
            // ex.printStackTrace();
        }
        return r_list;
    }
/*
    public void addRestaurant(Restaurant restaurant)
    {
        // Insert restaurant object into restaurant table
    }

    public void removeRestaurant(Restaurant restaurant)
    {
        // Find row in restaurant table a delete the row
    }

    public ArrayList<Restaurant> getAllRestaurants()
    {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        // Select all restaurants from table and add to ArrayList
        private static List<Restaurant> load() {
        List<Restaurant> people = Person.loadAll(connection);
        return restaurants;
    }



    public ArrayList<String> getAllCategories()
    {
        ArrayList<String> categories = new ArrayList<String>();
        // Select all categories from table and add to ArrayList

        return categories;
    }

    public ArrayList<Restaurant> getByCategory(String category)
    {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        // Select all restaurants with the specified category

        return restaurants;
    }

    public ArrayList<Restaurant> getByCity(String city)
    {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        // Select all restaurants with the specified city

        return restaurants;
    }*/
}
