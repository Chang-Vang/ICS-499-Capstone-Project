package com.ICS499.Application.model.Customer;

import com.ICS499.Application.model.Address;

public class Customer {
    private final String name;
    private final String email;
    private final String phone;
    private Address address;

    public Customer(String name, String streetAddress, String city, String State, Integer ZipCode, String PhoneNumber, String email) {
        this.name = name;
        this.phone = PhoneNumber;
        this.email = email;
        this.address = new Address(streetAddress, city, State, ZipCode);
    }

    @Override
    public String toString() {
        return String.format("""
                             Name: %s
                             Address: %s
                             Email: %s
                             Phone: %s""", name, address, email, phone);
    }

    public void printLabel() {
        // TODO: send name and mailing address to printer
        System.out.println(this);
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }

}
