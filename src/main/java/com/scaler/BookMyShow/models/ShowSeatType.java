package com.scaler.BookMyShow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel{ // showSeatType is a combination of show and seatType to show the price of a seat
    @ManyToOne
    private Show show;

    private int price;

    @ManyToOne
    private SeatType seatType;
}
