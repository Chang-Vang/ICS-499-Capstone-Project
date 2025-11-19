package com.ICS499.Application.repositories;

import com.ICS499.Application.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> email(String email);
}