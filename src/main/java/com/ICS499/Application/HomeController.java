package com.ICS499.Application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

//    @RequestMapping("/login")
//    public String login() {
//        // Forward to static resource
//        return "static/HomePage/HomePg.html";
//    }
}
