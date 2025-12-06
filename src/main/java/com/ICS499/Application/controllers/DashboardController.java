package com.ICS499.Application.controllers;

import com.ICS499.Application.Restaurant;
import com.ICS499.Application.User;
import com.ICS499.Application.entities.OrderItem;
import com.ICS499.Application.repositories.FoodItemRepository;
import com.ICS499.Application.repositories.RestaurantRepository;
import com.ICS499.Application.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodItemRepository foodItemRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Setter
    @Getter
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/home")
    public String getHome(Model model, HttpSession session) {
        //get email from Session
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByEmail(email);

        // Build a safe full name (no "null")
        String fullName = "User";
        if (user != null) {
            String first = user.getFirstName();
            String last  = user.getLastName();

            if ((first != null && !first.isBlank()) || (last != null && !last.isBlank())) {
                fullName = ((first != null) ? first : "") + " " + ((last != null) ? last : "");
                fullName = fullName.trim();
            } else if (user.getEmail() != null) {
                fullName = user.getEmail();
            }
        }

        model.addAttribute("fullName", fullName);

        // Sample offers data
        var allItems = foodItemRepository.findAll();
        List<Offer> offers = allItems.stream()
                .limit(7)
                .map(item -> new Offer(
                        item.getId(),
                        item.getName(),
                        "$" + item.getPrice()
                ))
                .toList();

        model.addAttribute("offers", offers);

        // Load restaurants from the database
        List<Restaurant> restaurants = restaurantRepository.findAll();
        model.addAttribute("restaurants", restaurants);

        int cartCount = getCartCount(session);
        model.addAttribute("cartCount", cartCount);

        return "/dashboard/home";
    }

    @GetMapping("/owner/dashboard")
    public String getOwnerDashboard(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(email);

        String fullName = "Owner";
        if (user.getFirstName() != null || user.getLastName() != null) {
            String first = Objects.toString(user.getFirstName(), "");
            String last = Objects.toString(user.getLastName(), "");
            fullName = (first + " " + last).trim();
            if (fullName.isBlank()) fullName = user.getEmail();
        }

        model.addAttribute("fullName", fullName);
        return "/dashboard/owner-home";
    }

    public record Offer(Long foodId, String description, String price) {

    }

    // Controller to handle which restaurant menu to view
    @GetMapping("/restaurant/{id}")
    public String viewRestaurant(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        if (restaurant == null) {
            return "redirect:/home";
        }

        // Add restaurant details
        model.addAttribute("restaurant", restaurant);

        // Get the food items for this restaurant
        model.addAttribute("foodItems", restaurant.getFoodItems());

        // Add cart count
        int cartCount = getCartCount(session);
        model.addAttribute("cartCount", cartCount);

        return "dashboard/restaurant-menu";
    }


    // profile
    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        int cartCount = getCartCount(session);
        model.addAttribute("cartCount", cartCount);

        return "dashboard/profile";
    }
    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String address,
            @RequestParam String gender,
            @RequestParam String dateOfBirth,
            @RequestParam String phoneNumber,
            HttpSession session) {

        String email = (String) session.getAttribute("email");
        User user = userRepository.findByEmail(email);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setPhoneNumber(phoneNumber);

        userRepository.save(user);

        return "redirect:/home";
    }

    private int getCartCount(HttpSession session) {
        Map<Long, OrderItem> cart = (Map<Long, OrderItem>) session.getAttribute("cart");
        if (cart == null) { return 0; }
        int count = 0;
        for (OrderItem item : cart.values()) {
            count += item.getQuantity();
        }
        return count;
    }




}