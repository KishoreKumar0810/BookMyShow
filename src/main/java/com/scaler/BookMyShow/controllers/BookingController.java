package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.dtos.BookingRequestDto;
import com.scaler.BookMyShow.dtos.BookingResponseDto;
import com.scaler.BookMyShow.models.ResponseStatus;
import com.scaler.BookMyShow.services.BookingService;
import com.scaler.BookMyShow.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }
    public BookingResponseDto bookMovie(BookingRequestDto bookingRequestDto){
        BookingResponseDto bookingResponseDto = new BookingResponseDto();

        Booking booking;
        try {
            booking = bookingService.bookMovie(
                    bookingRequestDto.getUserId(),
                    bookingRequestDto.getShowSeatId(),
                    bookingRequestDto.getShowId()
            );
            bookingResponseDto.setAmount(booking.getAmount());
            bookingResponseDto.setBookingId(booking.getId());
            bookingResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception ex){
                bookingResponseDto.setResponseStatus(ResponseStatus.FAILED);
        }
        return bookingResponseDto;
    }
}
