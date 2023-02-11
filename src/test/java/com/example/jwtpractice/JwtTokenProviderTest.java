package com.example.jwtpractice;

import com.example.jwtpractice.core.JwtTokenProvider;
import com.example.jwtpractice.exception.ExpiredTokenException;
import com.example.jwtpractice.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenProviderTest {

    public static final String SECRET_KEY = "secret-key secret-key secret-key secret-key secret-key secret-key secret-key";
    JwtTokenProvider provider;

    @Test
    @DisplayName("토큰 생성 및 추출")
    void create() throws Exception {
        //given
        provider = new JwtTokenProvider(SECRET_KEY, 1000L);
        String token = provider.createToken("1");

        //when
        Long userId = provider.getUserId(token);

        //then
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    @DisplayName("토큰 기간 만료")
    void getUserId() throws Exception {
        //given
        provider = new JwtTokenProvider(SECRET_KEY, 0);
        String token = provider.createToken("1");

        //when then
        assertThatThrownBy(() -> provider.getUserId(token))
                .isInstanceOf(ExpiredTokenException.class);
    }

    @Test
    void invalidToken() throws Exception {
        //given
        provider = new JwtTokenProvider(SECRET_KEY, 100L);
        String token = "token token";

        //when then
        assertThatThrownBy(() -> provider.getUserId(token))
            .isInstanceOf(InvalidTokenException.class);
    }
}