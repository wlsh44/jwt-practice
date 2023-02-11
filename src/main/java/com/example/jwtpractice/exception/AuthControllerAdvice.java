package com.example.jwtpractice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Void> authException(AuthException exception) {
        log.info("exception.getErrorMsg() = {}", exception.getErrorMsg());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
