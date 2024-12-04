package com.example.kahoot.security;

import com.example.kahoot.model.Token;
import com.example.kahoot.model.User;
import com.example.kahoot.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private final long jwtExpirationInMillis = 3600000; // 1 година

    private SecretKey secretKey;

    @Autowired
    private TokenRepository tokenRepository;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @PostConstruct
    public void init() {
        if (this.secretKey == null) {
            if (jwtSecret == null || jwtSecret.isBlank()) {
                throw new IllegalArgumentException("JWT secret key cannot be null or empty!");
            }
            this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        }
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
            logger.info("JWT token parsed successfully");

            Optional<Token> storedToken = tokenRepository.findByTokenValue(tokenValue);
            if (storedToken.isPresent()) {
                boolean isExpired = isTokenExpired(storedToken.get());
                logger.info("Token exists in database. Is expired: {}", isExpired);
                return !isExpired;
            }

            logger.warn("Token does not exist in database.");
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Token validation failed: {}", e.getMessage());
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
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(tokenValue)
                .getBody();
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
