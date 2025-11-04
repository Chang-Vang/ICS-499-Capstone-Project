package edu.metrostate.model;

public class Order {
    private Restaurant restaurant;
    private OrderItem[] orderItem;
    private Integer totalPrice;
    private CustomerInfo customer;
    private PaymentDetails payment;

    public Order(Restaurant r, OrderItem[] orderItems,Integer price, CustomerInfo cust, PaymentDetails pay ){
        this.restaurant = r;
        this.orderItem = orderItems;
        this.totalPrice = price;
        this.customer = cust;
        this.payment = pay;
     }

     public void addCustomer(String name, String streetAddress,String city, String state, int zipCode, String phoneNum ){
        CustomerInfo customer = new CustomerInfo(name, streetAddress, city, state, zipCode, phoneNum);
     }

     public void addPayment(String ccNum, Integer ccv, String exp){
        PaymentDetails payment = new PaymentDetails(ccNum, exp, ccv);
     }

     public void addItem(OrderItem item){

     }

     public void removeItem(OrderItem item){

     }

}
