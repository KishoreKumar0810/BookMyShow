package com.scaler.BookMyShow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequestDto {
    private List<Long> showSeatId;
    private Long showId;
    private Long userId;
}
