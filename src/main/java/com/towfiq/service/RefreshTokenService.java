package com.towfiq.service;

import com.towfiq.datamapper.DataMapper;
import com.towfiq.dto.RefreshTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${refresh.token.expiration.time}")
    private Integer EXPIRATION_TIME;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    public RefreshTokenDTO createRefreshToken(String username) {
        RefreshTokenDTO refreshToken = RefreshTokenDTO.builder()
                .userDetails(myUserDetailsService.loadUserByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(EXPIRATION_TIME))
                .build();
        DataMapper.userRefreshTokenMapper.put(username, refreshToken);
        DataMapper.tokenRefreshTokenMapper.put(refreshToken.getToken(), refreshToken);
        return refreshToken;
    }


    public RefreshTokenDTO findByToken(String token) {
        return DataMapper.tokenRefreshTokenMapper.get(token);
    }

    public RefreshTokenDTO verifyExpiration(RefreshTokenDTO token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            DataMapper.userRefreshTokenMapper.remove(token.getUserDetails().getUsername());
            DataMapper.tokenRefreshTokenMapper.remove(token.getToken());
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login!");
        }
        return token;
    }

}
