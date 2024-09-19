package com.example.foodApp.reponsitory;

import com.example.foodApp.model.RefeshToken;
import com.example.foodApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenReponsitory extends JpaRepository<RefeshToken,Long> {
    Optional<RefeshToken> findByToken(String token);
    RefeshToken findByUserId(Long userId);

    @Modifying
    int deleteByUser(User user);
}
