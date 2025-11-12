package com.scaler.BookMyShow.exceptions;

public class SeatBookedException extends RuntimeException{
    public SeatBookedException(){
        System.out.println("Something went wrong. The Seats are no longer available" );
    }
}
