package com.scaler.BookMyShow.exceptions;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super("Email not found: " + email);
    }
}
