package com.scaler.BookMyShow.exceptions;

public class TheatreNotFoundException extends RuntimeException {
    public TheatreNotFoundException(Long id) {
        super("Theatre with id " + id + " not found.");
    }

    public TheatreNotFoundException(String name) {
        super("Theatre with name " + name + " not found.");
    }
}
