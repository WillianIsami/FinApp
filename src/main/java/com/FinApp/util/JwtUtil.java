package com.FinApp.util;

import com.FinApp.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${api.security.jwt.secret}")
    private String jwtSecret;

    @Value("${api.security.jwt.expiration.in.ms}")
    private int jwtExpirationMs;

    private Algorithm getAlgorithm(){
        return Algorithm.HMAC512(jwtSecret);
    }

    public String generateToken(User user){
        try {
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .sign(getAlgorithm());
        } catch (JWTCreationException  exception) {
            throw new RuntimeException("Error while authenticating", exception);
        }
    }

    public String getEmailFromToken(String token){
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }
}