package com.ICS499.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.application.name}")
    private String appName;

    @PostMapping("/home")
    public void processHome(
            @RequestParam String email,
            Model model) {
        User user = userRepository.findByEmail(email);
        model.addAllAttributes("username", user.getEmail() );

    }
    @GetMapping("/home")
    public String getHome(Model model) {
        // Set the username
        model.addAttribute("username", "Jane Cooper");

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

        return "dashboard/home";
    }

    // Inner classes for data models
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