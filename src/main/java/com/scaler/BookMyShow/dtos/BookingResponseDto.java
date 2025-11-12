package com.scaler.BookMyShow.dtos;

import com.scaler.BookMyShow.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDto {
    private int amount;
    private Long bookingId;
    private ResponseStatus responseStatus;
}
