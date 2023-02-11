package com.example.jwtpractice.controller;

import com.example.jwtpractice.controller.dto.TokenResponse;
import com.example.jwtpractice.core.JwtTokenProvider;
import com.example.jwtpractice.exception.AuthExtractException;
import com.example.jwtpractice.exception.InvalidTokenException;
import com.example.jwtpractice.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JwtTokenProvider provider;

    @Test
    @DisplayName("토큰 발급 테스트")
    void tokenIssue() throws Exception {
        //given
        TokenResponse expect = new TokenResponse("jwt token");
        given(authService.getAuthToken(1L))
                .willReturn(expect);

        //when
        ResultActions results = mockMvc.perform(get("/token")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        results.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @Test
    @DisplayName("토큰 검증 테스트")
    void verifyToken() throws Exception {
        //given
        String expect = "1";
        String token = provider.createToken(expect);

        //when
        ResultActions results = mockMvc.perform(get("/verify")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token));

        //then
        results.andExpect(status().isOk())
                .andExpect(content().string(expect));
    }

    @Test
    @DisplayName("JWT 형식이 아닌 토큰")
    void invalidToken_notJwtTokenFormat() throws Exception {
        //given
        String invalidToken = "invalid token";

        //when
        ResultActions results = mockMvc.perform(get("/verify")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken));

        //then
        results.andDo(print())
                .andExpect(status().isUnauthorized()).
                andExpect(content().string(InvalidTokenException.ERROR_MSG));

    }

    @Test
    @DisplayName("인증 타입이 Bearer이 아닌 경우")
    void invalidToken_invalidAuthType() throws Exception {
        //given
        String token = provider.createToken("1");

        //when
        ResultActions results = mockMvc.perform(get("/verify")
                .header(HttpHeaders.AUTHORIZATION, "Bearerr " + token));

        //then
        results.andDo(print())
                .andExpect(status().isUnauthorized()).
                andExpect(content().string(AuthExtractException.ERROR_MSG));
    }
}