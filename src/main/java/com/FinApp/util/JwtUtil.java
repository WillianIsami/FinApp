package com.FinApp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    private Algorithm getAlgorithm(){
        return Algorithm.HMAC512(jwtSecret);
    }

    // Generate JWT token
    public String generateToken(String username, Set<String> roles){
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", String.join(",", roles))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .sign(getAlgorithm());
    }

    // Get username from JWT token
    public String getUsernameFromToken(String token){
        return JWT.require(getAlgorithm())
                .build()
                .verify(token)
                .getSubject();
    }

    // Get roles from JWT token
    public Set<String> getRolesFromToken(String token){
        String roles = JWT.require(getAlgorithm())
                .build()
                .verify(token)
                .getClaim("roles")
                .asString();
        return Set.of(roles.split(","));
    }

    // Validate JWT token
    public boolean validateToken(String token){
        try {
            JWT.require(getAlgorithm()).build().verify(token);
            return true;
        } catch (JWTVerificationException ex){
            // Log exception if necessary
            return false;
        }
    }
}