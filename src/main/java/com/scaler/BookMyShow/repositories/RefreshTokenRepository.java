package com.scaler.BookMyShow.repositories;

import com.scaler.BookMyShow.models.RefreshToken;
import com.scaler.BookMyShow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
}
