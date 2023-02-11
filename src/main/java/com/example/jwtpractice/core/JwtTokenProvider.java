package com.example.jwtpractice.core;

import com.example.jwtpractice.exception.ExpiredTokenException;
import com.example.jwtpractice.exception.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long expiredMillSecond;

    public JwtTokenProvider(
            @Value("${security.jwt.token.secret-key}") String key,
            @Value("${security.jwt.token.expired}") long expired) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.expiredMillSecond = expired;
    }

    public String createToken(String payload) {
        Date now = new Date();
        long expiredTime = now.getTime() + expiredMillSecond;

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(new Date(expiredTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getUserId(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build();

            String subject = parser.parseClaimsJws(token).getBody().getSubject();
            return Long.parseLong(subject);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }
}
