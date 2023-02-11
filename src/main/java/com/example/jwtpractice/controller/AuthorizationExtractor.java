package com.example.jwtpractice.controller;

import com.example.jwtpractice.exception.AuthExtractException;
import org.apache.logging.log4j.util.Strings;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer ";

    public static String extract(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        validateAuthHeaderExists(authHeader);
        validateStartWithTokenType(authHeader);

        return authHeader.substring(TOKEN_TYPE.length());
    }

    private static void validateAuthHeaderExists(String authHeader) {
        if (Strings.isEmpty(authHeader)) {
            throw new AuthExtractException();
        }
    }

    private static void validateStartWithTokenType(String authHeader) {
        if (!authHeader.startsWith(TOKEN_TYPE)) {
            throw new AuthExtractException();
        }
    }
}
