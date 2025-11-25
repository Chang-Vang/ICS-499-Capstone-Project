package com.ICS499.Application.service;

import com.ICS499.Application.repositories.AddressRepository;
import com.ICS499.Application.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


}
