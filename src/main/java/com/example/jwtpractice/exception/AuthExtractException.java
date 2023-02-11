package com.example.jwtpractice.exception;

public class AuthExtractException extends AuthException {

    public static final String ERROR_MSG = "인증 헤더 추출에 실패했습니다.";

    public AuthExtractException() {
        super(ERROR_MSG);
    }
}
