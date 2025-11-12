package com.scaler.BookMyShow.exceptions;

public class ScreenNotFoundException extends RuntimeException {
    public ScreenNotFoundException(Long id) {
        super("Screen with id " + id + " not found");
    }

    public ScreenNotFoundException(String name) {
        super("Screen with name " + name + " not found");
    }

    public ScreenNotFoundException(){
        super("Screen not found");
    }
}
