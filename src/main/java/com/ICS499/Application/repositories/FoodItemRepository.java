package com.ICS499.Application.repositories;

import com.ICS499.Application.FoodItem;
import com.ICS499.Application.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    //get all items for restaurant
    List<FoodItem> findByRestaurant(com.ICS499.Application.Restaurant restaurant);

    //find by category
    List<FoodItem> findByCategory(String category);

    //find all by restaurant
    List<FoodItem> findAllByRestaurant(Restaurant restaurant);
}