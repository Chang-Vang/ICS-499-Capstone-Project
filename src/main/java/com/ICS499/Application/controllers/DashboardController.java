package com.ICS499.Application.controllers;

import com.ICS499.Application.User;
import com.ICS499.Application.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

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
        public record Offer(String description, String price) {

    }

    public record Restaurant(String name, double rating) {

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


}