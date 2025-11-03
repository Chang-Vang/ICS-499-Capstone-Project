package com.ICS499.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.ICS499.Application.service.DatabaseImplementation;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

                DatabaseImplementation db = DatabaseImplementation.getInstance();
                db.migrate();

                ApplicationContext context = SpringApplication.run(Application.class, args);
                var orderService = context.getBean(OrderService.class);
                orderService.placeOrder();

	}
}
