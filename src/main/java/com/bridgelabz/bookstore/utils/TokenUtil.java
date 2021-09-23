package com.bridgelabz.bookstore.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.auth0.jwt.JWT;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class TokenUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

//    public String generateVerificationToken(String email) {
//        log.info("Inside generateVerificationToken method.");
//        long currentTime = System.currentTimeMillis();
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(currentTime +3600000))
//                .signWith(HS256, jwtSecret)
//                .compact();
//    }

    public  String generateToken(int userId)   {

        try {
            //to set algorithm
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

            String token = JWT.create()
                    .withClaim("user_id", userId)
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException | IllegalArgumentException exception) {

            exception.printStackTrace();
            //log Token Signing Failed
        } // TODO Auto-generated catch block
        return null;
    }



//    public String parseToken(String token) {
//        log.info("Inside parseToken method.");
//        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//    }

    public int decodeToken(String token) {
        Integer userId;
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(jwtSecret));
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
        JWTVerifier jwtVerifier = verification.build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim claim = decodedJWT.getClaim("user_id");
        userId = claim.asInt();
        return userId;
    }
}
