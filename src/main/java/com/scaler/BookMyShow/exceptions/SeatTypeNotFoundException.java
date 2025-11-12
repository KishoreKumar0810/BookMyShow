package com.scaler.BookMyShow.exceptions;

public class SeatTypeNotFoundException extends RuntimeException {
    public SeatTypeNotFoundException(Long id) {
        super("Seat type with id " + id + " not found");
    }

    public SeatTypeNotFoundException(String name) {
        super("Seat type with name " + name + " not found");
    }

    public SeatTypeNotFoundException() {
        super("Seat type not found");
    }
}
