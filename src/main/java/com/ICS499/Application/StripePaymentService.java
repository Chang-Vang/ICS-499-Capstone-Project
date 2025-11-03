package com.ICS499.Application;

import org.springframework.stereotype.Service;

@Service("stripe")
public class StripePaymentService implements PaymentService {
    // @Value("${stripe.apiUrl}")
    // private String apiUrl;
    // @Value("${strip.enable}")
    // private boolean enabled;
    // @Value("${stripe.timeout:3000}")
    // private int timeout;
    // @Value("${stripe.supported-currencies}")
    // private List<String> supportedCurrencies;

    @Override
    public void processPayment(double amount) {
        // Logic to process payment using Stripe API
        System.out.println("STRIPE");
        // System.out.println("API URL: " + apiUrl);
        // System.out.println("Enabled: " + enabled);
        // System.out.println("Timeout: " + timeout);
        // System.out.println("Currencies" + supportedCurrencies);
        System.out.println("Processing payment of $" + amount + " through Stripe.");

    }
}
