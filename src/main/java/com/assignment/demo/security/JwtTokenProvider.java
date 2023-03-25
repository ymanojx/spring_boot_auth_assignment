package com.assignment.demo.security;

import com.assignment.demo.exception.AppException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app-jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpiryDuration;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpiryDuration);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        LOGGER.info("token generated at: " + currentDate + " for user: " + username);

        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    //get username from token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return username;
    }

    //validate jwt token
    public boolean validateToken(String token) {

        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException exception) {
            LOGGER.error("JWT token: " + token + " is invalid");
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException exception) {
            LOGGER.error("JWT token: " + token + " is expired");
            throw new AppException(HttpStatus.BAD_REQUEST, "expired JWT token");
        } catch (UnsupportedJwtException exception) {
            LOGGER.error("JWT token: " + token + " is not supported");
            throw new AppException(HttpStatus.BAD_REQUEST, "unsupported JWT token");
        } catch (IllegalArgumentException exception) {
            LOGGER.error("JWT claims is empty in token: " + token);
            throw new AppException(HttpStatus.BAD_REQUEST, "JWT claims string is empty in token");
        }
    }
}
