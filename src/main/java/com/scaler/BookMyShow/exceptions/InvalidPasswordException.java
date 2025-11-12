package com.scaler.BookMyShow.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(){
        super("Invalid Password");
    }
}
