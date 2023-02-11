package com.example.jwtpractice.exception;

public class InvalidTokenException extends AuthException {

    public static final String ERROR_MSG = "올바르지 않은 토큰입니다.";

    public InvalidTokenException() {
        super(ERROR_MSG);
    }
}
