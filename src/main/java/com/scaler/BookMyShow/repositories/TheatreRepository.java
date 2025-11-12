package com.scaler.BookMyShow.repositories;

import com.scaler.BookMyShow.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Optional<Theatre> findById(Long id);
    Optional<Theatre> findByName(String name);

}
