package com.bridgelabz.bookstore.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TokenUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateVerificationToken(String email) {
        log.info("Inside generateVerificationToken method.");
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime +3600000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String parseToken(String token) {
        log.info("Inside parseToken method.");
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
