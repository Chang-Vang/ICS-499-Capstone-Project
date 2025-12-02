package com.ICS499.Application.controllers;

import com.ICS499.Application.Restaurant;
import com.ICS499.Application.User;
import com.ICS499.Application.repositories.RestaurantRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/owner/restaurants")
public class OwnerRestaurantSelectorController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public ResponseEntity<?> listOwnedRestaurants(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        List<Restaurant> restaurants = restaurantRepository.findAllByOwnerId(user.getId());
        return ResponseEntity.ok(restaurants != null ? restaurants : Collections.emptyList());
    }

    @PostMapping("/select/{restaurantId}")
    public ResponseEntity<?> selectRestaurant(
            @PathVariable Long restaurantId,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        boolean ownsRestaurant = restaurantRepository
                .existsByIdAndOwnerId(restaurantId, user.getId());

        if (!ownsRestaurant) {
            return ResponseEntity.status(403)
                    .body("You do not own this restaurant.");
        }

        Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Set both id and actual Restaurant object in session so other controllers read the current restaurant
        session.setAttribute("activeRestaurantId", restaurantId);
        session.setAttribute("restaurant", opt.get());

        return ResponseEntity.ok("Restaurant selected");
    }
}