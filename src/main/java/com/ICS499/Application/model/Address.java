package com.ICS499.Application.model;


public class Address {
    private final String streetAddress;
    private final String city;
    private final String state;
    private final Integer zipCode;

    public Address(String streetAddress, String city, String state, Integer zipCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %d", streetAddress, city, state, zipCode);
    }
}
