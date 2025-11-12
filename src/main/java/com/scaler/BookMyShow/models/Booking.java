package com.scaler.BookMyShow.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel{

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    @ManyToOne
    private Show show;

    @ManyToMany
    private List<ShowSeat> showSeat;

    @ManyToOne
    private User user;

    private Date bookedAt;

    @OneToMany
    private List<Payment> payment;

    private int amount;

}
