package com.example.foodApp.Service;

import com.example.foodApp.Exception.TokenRefreshException;
import com.example.foodApp.model.RefeshToken;
import com.example.foodApp.reponsitory.RefreshTokenReponsitory;
import com.example.foodApp.reponsitory.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenReponsitory refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefeshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefeshToken createRefreshToken(Long userId) {
        RefeshToken refreshTokenOld = refreshTokenRepository.findByUserId(userId);
        if(refreshTokenOld !=null){
            refreshTokenRepository.delete(refreshTokenOld);
        }
        RefeshToken refreshToken = new RefeshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefeshToken verifyExpiration(RefeshToken token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");

        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
