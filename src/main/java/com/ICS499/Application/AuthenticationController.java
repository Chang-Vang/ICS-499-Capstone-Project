package com.ICS499.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

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
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("email", email);
            return "redirect:dashboard/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }
    }

    // Show the signup page
    @GetMapping("/register")
    public String showSignupPage() {
        return "auth/register";
    }

    // Handle the form submission
    @PostMapping("/register")
    public String handleSignup(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        // save the user to the database, send confirmation email, etc.

        // For now, redirect to login page or show success message
        model.addAttribute("message", "Signup successful! Please login.");
        return "redirect:/login"; // Redirect to login page after signup
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
    public String handleResetPassword(@RequestParam String username,
                                      @RequestParam String newpassword,
                                      @RequestParam String confirmpassword) {
        // Logic to update password
        return "redirect:/login";
    }
}
