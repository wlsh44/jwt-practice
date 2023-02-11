package com.example.jwtpractice;

import com.example.jwtpractice.core.JwtTokenProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenProviderTest {

    public static final String SECRET_KEY = "secret-key secret-key secret-key secret-key secret-key secret-key secret-key";
    JwtTokenProvider provider;

    @Test
    void test() throws Exception {
        //given
        provider = new JwtTokenProvider(SECRET_KEY, 1000L);
        String token = provider.createToken("1");

        //when
        Long userId = provider.getUserId(token);

        //then
        assertThat(userId).isEqualTo(1L);
    }
}