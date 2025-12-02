package com.ICS499.Application.service;


import com.ICS499.Application.Restaurant;
import com.ICS499.Application.User;
import com.ICS499.Application.repositories.RestaurantRepository;
import com.ICS499.Application.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public boolean isOwner(Long userId) {
        return userRepository.findById(userId)
                .map(user -> Boolean.TRUE.equals(user.getIsRestaurant_owner()))
                .orElse(false);
    }

    public boolean isOwnerByEmail(String email) {
        return userRepository.findByEmail(email) != null &&
               Boolean.TRUE.equals(userRepository.findByEmail(email).getIsRestaurant_owner());
    }

    public List<Restaurant> getRestaurantsByOwnerEmail(String email) {
        var user = userRepository.findByEmail(email);
        if (user != null) {
            return restaurantRepository.findAllByOwner(user);
        }
        return List.of(); // Return empty list if user not found
    }











}
