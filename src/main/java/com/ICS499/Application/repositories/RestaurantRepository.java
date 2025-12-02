package com.ICS499.Application.repositories;

import com.ICS499.Application.Restaurant;
import com.ICS499.Application.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByOwner(User owner);
    Restaurant findByOwnerId(Long ownerId);

    List <Restaurant> findAllByOwnerId(Long ownerId);
    boolean existsByIdAndOwnerId(Long restaurantId, Long ownerId);

    // Having issues dealing with multiple restaurants for one owner
    List <Restaurant> findAllByOwner(User owner);

    List <Restaurant> findAllByOwnerEmail(String email);
}
