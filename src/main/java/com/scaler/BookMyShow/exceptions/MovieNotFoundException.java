package com.scaler.BookMyShow.exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long id) {
        super("Movie with ID " + id + " not found.");
    }
    public MovieNotFoundException(String title) {
        super("Movie with title " + title + " not found.");
    }
}
