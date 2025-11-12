package com.scaler.BookMyShow.repositories;

import com.scaler.BookMyShow.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findById(Long id);
    Optional<Region> findRegionByName(String name);
    List<Region> findAll();
}
