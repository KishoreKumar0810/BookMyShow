package com.scaler.BookMyShow.repositories;

import com.scaler.BookMyShow.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByGenre(String genre);
    Optional<Movie> findById(Long id);
    Optional<Movie> findByTitle(String title);
    List<Movie> findAll();
}
