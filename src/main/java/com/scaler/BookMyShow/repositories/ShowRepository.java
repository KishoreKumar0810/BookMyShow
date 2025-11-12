package com.scaler.BookMyShow.repositories;

import com.scaler.BookMyShow.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    Optional<Show> findById(Long id);

    List<Show> findByMovie_Id(Long movieId);

    List<Show> findByTheatre_Id(Long theatreId);
}
