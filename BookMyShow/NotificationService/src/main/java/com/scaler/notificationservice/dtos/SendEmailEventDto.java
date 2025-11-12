package com.scaler.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailEventDto {
    private String toEmail;
    private String subject;
    private String body;
}
