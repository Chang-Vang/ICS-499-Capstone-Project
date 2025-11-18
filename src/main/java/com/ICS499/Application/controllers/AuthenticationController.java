package com.ICS499.Application.controllers;

import com.ICS499.Application.User;
import com.ICS499.Application.dto.RegistrationForm;
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
            Model model) {
        try {
            User user = userRepository.findByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                model.addAttribute("email", email);
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
        model.addAttribute("registrationForm", new RegistrationForm());
        return "auth/register"; // your template path
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registrationForm") RegistrationForm form, Model model) {
        // Basic validation
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "auth/register";
        }
        try {
            User user = new User();
            user.setEmail(form.getEmail());

            user.setPassword(form.getPassword()); // will be hashed inside service //needs implementation

            userService.registerUser(user);
            model.addAttribute("message", "Signup successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPassword() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String email) {
        // Logic to send reset email
        return "redirect:/login";
    }

    @GetMapping("/reset-password")
    public String showResetPassword() {
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam String email,
                                      @RequestParam String newpassword,
                                      @RequestParam String confirmpassword) {
        // Logic to update password
        return "redirect:/login";
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
}
