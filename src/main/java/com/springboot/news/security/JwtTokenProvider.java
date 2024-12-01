package com.springboot.news.security;

import com.springboot.news.exception.NewsApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String token = Jwts.builder().subject(username).issuedAt(new Date()).expiration(expireDate).signWith(key()).compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
            return true;
        } catch (MalformedJwtException malformedJwtException) {
           throw new NewsApiException(HttpStatus.BAD_REQUEST, "Недійсний токен") ;
        }  catch (ExpiredJwtException expiredJwtException) {
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Термін дії токену сплив");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Непідтримуваний токен");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Рядок нульовий або порожній");
        }
    }
}
