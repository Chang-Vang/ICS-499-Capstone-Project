package com.ICS499.Application.repositories;

import com.ICS499.Application.User;
import com.ICS499.Application.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByUser(User user);

}
