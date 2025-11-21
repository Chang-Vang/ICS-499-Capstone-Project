package com.ICS499.Application.repositories;

import com.ICS499.Application.FoodItem;
import com.ICS499.Application.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<User, String> {


}