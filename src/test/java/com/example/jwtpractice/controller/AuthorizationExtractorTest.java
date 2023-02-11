package com.example.jwtpractice.controller;

import com.example.jwtpractice.exception.AuthExtractException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthorizationExtractorTest {

    private static final String AUTHORIZATION_HEADER_TYPE = "Authorization";

    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @ParameterizedTest
    @NullSource @EmptySource
    @ValueSource(strings = {"Bearrer jwt-token", "Bearer"})
    @DisplayName("추출 실패시 예외 리턴")
    void extractNothingEmpty(String token) {
        given(request.getHeader(AUTHORIZATION_HEADER_TYPE)).willReturn(token);

        assertThatThrownBy(() -> AuthorizationExtractor.extract(request))
                .isInstanceOf(AuthExtractException.class);
    }

    @Test
    @DisplayName("토큰 추출")
    void extract() {
        given(request.getHeader(AUTHORIZATION_HEADER_TYPE)).willReturn("Bearer jwt-token");

        assertThat(AuthorizationExtractor.extract(request)).isEqualTo("jwt-token");
    }
}