package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.exceptions.SeatBookedException;
import com.scaler.BookMyShow.exceptions.ShowNotFoundException;
import com.scaler.BookMyShow.exceptions.UserNotFoundException;
import com.scaler.BookMyShow.models.*;
import com.scaler.BookMyShow.repositories.BookingRepository;
import com.scaler.BookMyShow.repositories.ShowRepository;
import com.scaler.BookMyShow.repositories.ShowSeatRepository;
import com.scaler.BookMyShow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PricingService pricingService;
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(UserRepository userRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository,
                          PricingService pricingService,
                          BookingRepository bookingRepository){
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.pricingService = pricingService;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> showSeatIds, Long showId){
        /* Get the user, showSeat and show using their id's from DB
         * If a seat is available or locked before 10 mins. Change the status
         * Save DB the updated showSeat and the booking
         */
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new UserNotFoundException(userId);
        User bookedBy = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty())
            throw new ShowNotFoundException(showId);
        Show bookedShow = showOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        for(ShowSeat showSeat : showSeats){
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE))
                throw new SeatBookedException();
        }
        List<ShowSeat> savedShowSeat = new ArrayList<>();
        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            savedShowSeat.add(showSeatRepository.save(showSeat));
        }

        Booking booking = new Booking();
        booking.setUser(bookedBy);
        booking.setShow(bookedShow);
        booking.setShowSeat(savedShowSeat);
        booking.setBookedAt(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(pricingService.calculatePrice(savedShowSeat, bookedShow));

        return bookingRepository.save(booking);
    }
}
