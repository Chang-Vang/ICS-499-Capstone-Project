package com.ICS499.Application.model;

import com.ICS499.Application.model.Customer.Customer;

public class Order {
    private OrderItem[] orderItem;
    private Integer totalPrice;
    private Customer customer;
    private PaymentDetails payment;

    public Order(Restaurant_old r, OrderItem[] orderItems, Integer price, Customer cust, PaymentDetails pay ){
        this.orderItem = orderItems;
        this.totalPrice = price;
        this.customer = cust;
        this.payment = pay;
     }

     public void addCustomer(String name, String streetAddress,String city, String state, Long zipCode, String phoneNum, String email ){
        Customer customer = new Customer(name, streetAddress, city, state, zipCode, phoneNum, email);
     }

     public void addPayment(String ccNum, Integer ccv, String exp){
        PaymentDetails payment = new PaymentDetails(ccNum, exp, ccv);
     }

     public void addItem(OrderItem item){

     }

     public void removeItem(OrderItem item){

     }

}
