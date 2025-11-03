package com.ICS499.Application;
//@Primary
public class PaypalPaymentService implements PaymentService {
    @Override
    public void processPayment(double amount) {
        // Logic to process payment using PayPal API
        System.out.println("PAYPAL");
        System.out.println("Processing payment of $" + amount + " through PayPal.");
    }
}
