package edu.metrostate.model;

public class CustomerInfo {
    private final String name;
    private final String streetAddress;
    private final String city;
    private final String state;
    private final Integer zipCode;
    private final String phone;

    public CustomerInfo(String name, String streetAddress, String city, String State, Integer ZipCode, String PhoneNumber) {
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = State;
        this.zipCode = ZipCode;
        this.phone = PhoneNumber;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\n" +
                "Address:%s" +
                "%s %s, %d" +
                "%s", name, streetAddress, city, state, zipCode, phone);
    }

    public void printLabel() {
        // TODO: send name and mailing address to printer
        System.out.println(this);
    }
}
