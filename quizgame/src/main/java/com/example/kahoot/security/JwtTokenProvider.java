package com.example.kahoot.security;

import com.example.kahoot.model.Token;
import com.example.kahoot.model.User;
import com.example.kahoot.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenProvider {

    private final String jwtSecret = "AngxLHD4linAzspMkdrmhPavoJ6+FUxI6otyYN5EpN+JRqnwmRezv18l5Q/+2KqlTf51vkrqbrzTdUqzvQWsYA==";
    private final long jwtExpirationInMillis = 3600000; // 1 година

    private final SecretKey secretKey;

    @Autowired
    private TokenRepository tokenRepository;

    public JwtTokenProvider() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }


    public String generateToken(User user, String tokenType) {
        long expirationTime = tokenType.equals("access") ? jwtExpirationInMillis : jwtExpirationInMillis * 24; // Refresh токен діє довше
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + expirationTime);

        // Генерація унікального токену для кожного типу
        String tokenValue = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .claim("type", tokenType) // Додаємо тип токена в claims
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        // Видалення існуючого токена такого ж типу
        tokenRepository.findByUserAndTokenType(user, tokenType).ifPresent(tokenRepository::delete);

        // Збереження нового токена
        Token newToken = new Token();
        newToken.setUser(user);
        newToken.setTokenValue(tokenValue);
        newToken.setTokenType(tokenType);
        newToken.setExpirationDate(expiresAt.toInstant());
        tokenRepository.save(newToken);

        return tokenValue;
    }


    public boolean validateToken(String tokenValue) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(tokenValue);

            Optional<Token> storedToken = tokenRepository.findByTokenValue(tokenValue);
            return storedToken.isPresent() && !isTokenExpired(storedToken.get());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String generateOrUpdateToken(User user, String tokenType) {
        Optional<Token> existingToken = tokenRepository.findByUserAndTokenType(user, tokenType);

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + jwtExpirationInMillis);

        String tokenValue = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            token.setTokenValue(tokenValue);
            token.setExpirationDate(expiresAt.toInstant());
            tokenRepository.save(token);
        } else {
            Token newToken = new Token();
            newToken.setUser(user);
            newToken.setTokenValue(tokenValue);
            newToken.setTokenType(tokenType);
            newToken.setExpirationDate(expiresAt.toInstant());
            tokenRepository.save(newToken);
        }

        return tokenValue;
    }

    public Long getUserIdFromToken(String tokenValue) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(tokenValue).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean isTokenOfType(String tokenValue, String tokenType) {
        Optional<Token> storedToken = tokenRepository.findByTokenValue(tokenValue);
        return storedToken.isPresent() && storedToken.get().getTokenType().equalsIgnoreCase(tokenType);
    }

    public void revokeToken(String tokenValue) {
        Optional<Token> storedToken = tokenRepository.findByTokenValue(tokenValue);
        storedToken.ifPresent(tokenRepository::delete);
    }

    private boolean isTokenExpired(Token token) {
        return token.getExpirationDate().isBefore(Instant.now());
    }
}
