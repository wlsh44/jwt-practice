package com.example.jwtpractice.service;

import com.example.jwtpractice.core.JwtTokenProvider;
import com.example.jwtpractice.controller.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtProvider;

    public TokenResponse getAuthToken(Long userId) {
        String token = jwtProvider.createToken(String.valueOf(userId));
        return TokenResponse.create(token);
    }
}
