package com.ICS499.Application;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {


    public static void main(String[] args) {


        // VERY IMPORTANT: turn off headless BEFORE Spring Boot starts
        System.setProperty("java.awt.headless", "false");


        ApplicationContext context = SpringApplication.run(Application.class, args);


        System.out.println("Spring Boot started. Beans: " + context.getBeanDefinitionCount());


        // Try to open the browser automatically
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("http://localhost:8080"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is still not supported. Please open http://localhost:8080 manually.");
        }
    }
}


//package com.ICS499.Application;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//
//@SpringBootApplication
//public class Application {
//
//	public static void main(String[] args) {
//                ApplicationContext context = SpringApplication.run(Application.class, args);
//
//	}
//}
