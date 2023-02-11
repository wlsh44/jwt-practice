package com.example.jwtpractice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private final String token;

    public static TokenResponse create(String token) {
        return new TokenResponse(token);
    }
}
