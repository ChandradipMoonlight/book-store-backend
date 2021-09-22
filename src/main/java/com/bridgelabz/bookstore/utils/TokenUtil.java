package com.bridgelabz.bookstore.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.auth0.jwt.JWT;

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

    public int decodeToken(String token) {
        int getToken;
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(jwtSecret));
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
        assert verification != null;
        JWTVerifier jwtVerifier = verification.build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim claim = decodedJWT.getClaim("id");
        getToken = claim.asInt();
        return getToken;
    }


}
