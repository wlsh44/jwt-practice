package com.example.jwtpractice.exception;

public class AuthException extends RuntimeException {

    private final String errorMsg;

    public AuthException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
