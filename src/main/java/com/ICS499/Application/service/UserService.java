package com.ICS499.Application.service;

import com.ICS499.Application.User;
import com.ICS499.Application.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User registerUser(User user) {
        // Validate email uniqueness
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

//        // Hash the password ... Needs Implementation
//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        return userRepository.save(user);
    }

}
