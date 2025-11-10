package com.ICS499.Application.repositories;

import com.ICS499.Application.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}