package com.scaler.BookMyShow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment extends BaseModel {
    private int refNumber;
    private int amount;
    private PaymentProvider paymentProvider;
    private PaymentStatus paymentStatus;
}
