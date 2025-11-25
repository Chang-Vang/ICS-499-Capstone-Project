package com.ICS499.Application.controllers;

import com.ICS499.Application.User;
import com.ICS499.Application.dto.RegistrationFormForCustomer;
import com.ICS499.Application.model.Address;
import com.ICS499.Application.repositories.AddressRepository;
import com.ICS499.Application.repositories.UserRepository;
import com.ICS499.Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/")
    public String index() {
        return "auth/login"; // matches templates/auth/login.html
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login"; // matches templates/auth/login.html
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        User user = userRepository.findByEmail(email);
        try {

            if (user != null && user.getPassword().equals(password)) {
                session.setAttribute("email", email);
                if (Boolean.TRUE.equals(user.getIsRestaurant_owner())) {
                    return "redirect:/owner/dashboard";
                }
                return "redirect:/home";
            } else {
                // Stay on the login page and show an error message
                model.addAttribute("error", "Invalid email or password");
                return "auth/login";
            }
        } catch (Exception ex) {
            // In case of any repository/database error, do not redirect to /error.
            // Show a generic login error on the same page so the user is informed.
            model.addAttribute("error", "Invalid email or password");
            return "auth/login";
        }
    }

    // Show registration page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registrationForm", new RegistrationFormForCustomer());
        return "auth/register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registrationForm") RegistrationFormForCustomer form, Model model) {
        // Basic validation
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "auth/register";
        }
        try {
            User user = new User();
            user.setEmail(form.getEmail());
            user.setPassword(form.getPassword());
            user.setPhoneNumber(form.getPhoneNumber());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            userService.registerCustomer(user);

            Address address = new Address();
            address.setStreet(form.getStreet());
            address.setCity(form.getCity());
            address.setState(form.getState());
            address.setZipCode(Long.valueOf(form.getZipCode()));
            address.setUser(user);
            addressRepository.save(address);

            model.addAttribute("success", "Signup successful! Please login.");
            System.out.println("User registered: " + user.getEmail());
            return "auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println("Registration error: " + e.getMessage());
            return "auth/register";
        }
    }
    @GetMapping("/forgot-password")
    public String showForgotPassword() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String email, Model model) {
        // Logic to send reset email
        model.addAttribute("email", email);
        return "redirect:/reset-password?email=" + email;
    }

    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam(required = false) String email,Model model) {
        model.addAttribute("email", email);
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam String email,
                                      @RequestParam String newPassword,
                                      @RequestParam String confirmPassword,
                                      Model model) {
        // Logic to update password
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("email", email);
            return "auth/reset-password";
        }

        // Check if user exists
        User user = userRepository.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email not found");
            return "auth/reset-password";
        }

        //Update password
        user.setPassword(newPassword);
        userRepository.save(user);
        // Success message
        model.addAttribute("message", "Password reset successful! Please login.");
        return "redirect:/login";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "auth/about";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "auth/contact";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            try {
                session.invalidate();
            } catch (IllegalStateException ignored) {
                // session may already be invalidated; ignore
            }
        }
        return "redirect:/login";
    }

    // restaurant owner
    @GetMapping("/owner-login")
    public String showOwnerLoginPage() {
        return "auth/owner-login";
    }

    @GetMapping("/owner-register")
    public String showOwnerRegister() {
        return "auth/owner-register";
    }


}
