package com.example.jwtpractice.exception;

public class ExpiredTokenException extends AuthException {

    public static final String ERROR_MSG = "만료된 토큰입니다.";

    public ExpiredTokenException() {
        super(ERROR_MSG);
    }
}
