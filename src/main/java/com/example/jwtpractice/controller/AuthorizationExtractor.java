package com.example.jwtpractice.controller;

import com.example.jwtpractice.exception.AuthExtractException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {

    private static final String TOKEN_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        return getToken(authHeader);
    }

    private static String getToken(String authHeader) {
        validateAuthHeaderExists(authHeader);
        String[] authContents = authHeader.split(" ");
        validateStartWithTokenType(authContents);
        return authContents[1];
    }

    private static void validateAuthHeaderExists(String authHeader) {
        if (Strings.isEmpty(authHeader)) {
            throw new AuthExtractException();
        }
    }

    private static void validateStartWithTokenType(String[] authContents) {
        if (!(authContents.length == 2 && TOKEN_TYPE.equals(authContents[0]))) {
            throw new AuthExtractException();
        }
    }
}
