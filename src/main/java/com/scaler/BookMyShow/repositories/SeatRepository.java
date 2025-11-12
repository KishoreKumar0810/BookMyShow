package com.scaler.BookMyShow.repositories;

import com.scaler.BookMyShow.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Override
    List<Seat> findAllById(Iterable<Long> longs);
}
