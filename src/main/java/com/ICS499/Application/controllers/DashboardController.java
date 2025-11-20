package com.ICS499.Application.controllers;

import com.ICS499.Application.User;
import com.ICS499.Application.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

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

        // Build a safe full name (no "null null")
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
        List<Offer> offers = Arrays.asList(
                new Offer("The spicy burger", "$10.00"),
                new Offer("Vegan salad", "$8.50"),
                new Offer("Pizza combo", "$12.00"),
                new Offer("Pasta special", "$9.75")
        );
        model.addAttribute("offers", offers);

        // Sample restaurants data
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant("Burger House", 4.5),
                new Restaurant("Vegan Delight", 4.2),
                new Restaurant("Pizza Corner", 4.7)
        );
        model.addAttribute("restaurants", restaurants);

        return "/dashboard/home";
    }

    // Inner classes for data models,
    // only used for example right now, need to integrate with database.
    public static class Offer {
        private String description;
        private String price;

        public Offer(String description, String price) {
            this.description = description;
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public String getPrice() {
            return price;
        }
    }

    public static class Restaurant {
        private String name;
        private double rating;

        public Restaurant(String name, double rating) {
            this.name = name;
            this.rating = rating;
        }

        public String getName() {
            return name;
        }

        public double getRating() {
            return rating;
        }
    }
}