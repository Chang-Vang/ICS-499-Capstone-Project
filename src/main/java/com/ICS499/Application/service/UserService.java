package com.ICS499.Application.service;

import com.ICS499.Application.User;
import com.ICS499.Application.entities.Address;
import com.ICS499.Application.repositories.AddressRepository;
import com.ICS499.Application.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public void registerCustomer(User user) {

        validateEmailUnique(user.getEmail());

        // Hash the password ... Needs Implementation
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setIsRestaurant_owner(false); // Default to regular user because this is customer registration

        // Save user as customer
        userRepository.save(user);
    }

    public void registerOwner(User user) {

        validateEmailUnique(user.getEmail());

        user.setIsRestaurant_owner(true); // Set as restaurant owner because this is owner registration

        // Save user as owner
        userRepository.save(user);
    }

    // Helper to reduce redundant email-uniqueness validation
    private void validateEmailUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    public Address fetchAddress(User user){
        return addressRepository.findByUser(user);
    }
}