package com.FinApp.util;

import com.FinApp.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${api.security.jwt.secret}")
    private String jwtSecret;

    @Value("${api.security.jwt.expiration.in.ms}")
    private int jwtExpirationMs;

    private Algorithm getAlgorithm(){
        return Algorithm.HMAC512(jwtSecret);
    }

    public String generateToken(User user, Set<String> roles){
        try {
            return JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
                    .withClaim("roles", String.join(",", roles))
                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .sign(getAlgorithm());
        } catch (JWTCreationException  exception) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    public String getUsernameFromToken(String token){
        return JWT.require(getAlgorithm())
                .build()
                .verify(token)
                .getSubject();
    }

    public Set<String> getRolesFromToken(String token){
        String roles = JWT.require(getAlgorithm())
                .build()
                .verify(token)
                .getClaim("roles")
                .asString();
        return Set.of(roles.split(","));
    }

    public String validateToken(String token){
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex){
            return null;
        }
    }
}