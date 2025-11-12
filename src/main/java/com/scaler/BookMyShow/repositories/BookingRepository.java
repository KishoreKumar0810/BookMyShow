package com.scaler.BookMyShow.repositories;

import com.scaler.BookMyShow.models.Booking;
import com.scaler.BookMyShow.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);
    List<Booking> findByShow(Show show);
    Booking save(Booking booking);
}
