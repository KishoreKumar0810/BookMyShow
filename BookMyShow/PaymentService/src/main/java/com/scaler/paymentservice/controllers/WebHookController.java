package com.scaler.paymentservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks")
public class WebHookController {
    @GetMapping("/trigger")
    public void triggerWebHook(){
        System.out.println("WebHook Triggered");
    }
}
