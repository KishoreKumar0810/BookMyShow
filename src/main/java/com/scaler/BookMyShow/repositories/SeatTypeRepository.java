package com.scaler.BookMyShow.repositories;

import com.scaler.BookMyShow.models.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {
    Optional<SeatType> findById(Long id);
    Optional<SeatType> findByName(String name);
    List<SeatType> findAll();
}
