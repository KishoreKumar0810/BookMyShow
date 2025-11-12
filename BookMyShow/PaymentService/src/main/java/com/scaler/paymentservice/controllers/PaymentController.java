package com.scaler.paymentservice.controllers;

import com.scaler.paymentservice.services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    @PostMapping("/initiate/{orderId}") // http://localhost:8080/payments/initiate/1
    public String GeneratePaymentLink(@PathVariable("orderId") Long orderId) throws StripeException {
        return paymentService.generatePaymentLink(orderId);
    }

}
