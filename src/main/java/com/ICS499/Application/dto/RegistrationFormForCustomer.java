package com.ICS499.Application.dto;

import lombok.Data;

@Data
public class RegistrationFormForCustomer {
    private String firstName;
    private String lastName;
    private String email;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private String password;
    private String confirmPassword;


}
