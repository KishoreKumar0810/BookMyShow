package com.scaler.BookMyShow.exceptions;

public class ShowNotFoundException extends RuntimeException{
    public ShowNotFoundException(Long showId){
        super("Show with ID" + showId + " not found");
    }

    public ShowNotFoundException(String movie){
        super("Show for the movie " + movie + " not found");
    }

    public ShowNotFoundException(){
        super("Show not found");
    }
}
