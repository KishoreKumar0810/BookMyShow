package com.scaler.BookMyShow.exceptions;

import com.scaler.BookMyShow.models.User;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long userId){
        super("User with UserID " + userId + " not found");
    }
}
