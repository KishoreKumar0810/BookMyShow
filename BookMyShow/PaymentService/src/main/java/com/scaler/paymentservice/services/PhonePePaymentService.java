package com.scaler.paymentservice.services;

import org.springframework.stereotype.Service;

@Service
public class PhonePePaymentService implements PaymentService{
    @Override
    public String generatePaymentLink(Long orderId) {
        return "";
    }
}
