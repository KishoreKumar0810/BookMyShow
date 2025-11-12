package com.scaler.oauth2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
    @GetMapping("/")
    public String home(){
        return "Welcome Buddy";
    }

    @GetMapping("/secured")
    public String secured(){
        return "You are successfully authenticated";
    }
}
