package com.example.jwtpractice.controller;

import com.example.jwtpractice.service.AuthService;
import com.example.jwtpractice.controller.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @GetMapping("/token")
    public ResponseEntity<TokenResponse> token(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getAuthToken(userId));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@Authenticated Long userId) {
        return ResponseEntity.ok(String.valueOf(userId));
    }
}
