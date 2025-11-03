package com.ICS499.Application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public PaymentService paypal(){

        return new PaypalPaymentService();
    }

    @Bean
    public OrderService orderService(){
        return  new OrderService(paypal());
    }
}
