package com.ICS499.Application.controllers;

import com.ICS499.Application.FoodItem;
import com.ICS499.Application.Restaurant;
import com.ICS499.Application.repositories.FoodItemRepository;
import com.ICS499.Application.repositories.RestaurantRepository;
import com.ICS499.Application.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/owner/food")
public class OwnerFoodController {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    private final Path uploadDir = Paths.get("src/main/resources/static/uploads/owner");

    public OwnerFoodController() throws IOException {
        Files.createDirectories(uploadDir);
    }

    // Return only food items for the restaurant stored in the session
    @GetMapping
    public List<FoodItem> listAll(HttpSession session) {
        Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
        if (restaurant == null) {
            // Not a logged-in owner / not associated with a restaurant -> return empty list
            return Collections.emptyList();
        }
        return foodItemRepository.findAllByRestaurant(restaurant);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createFood(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Float price,
            @RequestParam String category,
            @RequestParam(required = false) MultipartFile image,
            HttpSession session) throws IOException {


        System.out.println(session.getAttribute("user"));

        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("Name is required");
        }
        if (price == null || price < 0) {
            return ResponseEntity.badRequest().body("Price must be non-negative");
        }

        FoodItem item = new FoodItem();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);

        // Image upload handling (commented out for now)
//        if (image != null && !image.isEmpty()) {
//            String filename = System.currentTimeMillis() + "-" + StringUtils.cleanPath(image.getOriginalFilename());
//            Path target = uploadDir.resolve(filename);
//            Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
//            item.setImageUrl("/uploads/owner/" + filename);
//        }

        // Passing Restaurant is not working properly, need to fix or find alternative
//        Restaurant restaurant;
//        try {
//            User user = (User) session.getAttribute("user");
//            var session_restaurant = session.getAttribute("restaurant");
//
//            List<Restaurant> restaurants = Collections.singletonList(restaurantRepository.findByOwnerId(user.getId()));
//            restaurant = restaurants.get(0); // return the first restaurant
//        } catch (Exception e) {
//            return ResponseEntity.status(403).body("Unauthorized: No session found");
//        }


        Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
        if (restaurant == null) {
            return ResponseEntity.status(401).body("Not logged in as restaurant owner");
        }

        item.setRestaurant(restaurant);


        FoodItem saved = foodItemRepository.save(item);
        return ResponseEntity.ok(saved);
    }

    @PutMapping(path = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateFood(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Float price,
            @RequestParam String category,
            @RequestParam(required = false) MultipartFile image) throws IOException {

        Optional<FoodItem> opt = foodItemRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        FoodItem item = opt.get();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);

        // Image update is currently disabled
//        if (image != null && !image.isEmpty()) {
//            String filename = System.currentTimeMillis() + "-" + StringUtils.cleanPath(image.getOriginalFilename());
//            Path target = uploadDir.resolve(filename);
//            Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
//            item.setImageUrl("/uploads/owner/" + filename);
//        }

        FoodItem saved = foodItemRepository.save(item);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {
        if (!foodItemRepository.existsById(id)) return ResponseEntity.notFound().build();
        foodItemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}