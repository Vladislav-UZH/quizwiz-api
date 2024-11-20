package com.example.kahoot.repository;

import com.example.kahoot.model.Token;
import com.example.kahoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenValue(String tokenValue);
    Optional<Token> findByUserAndTokenType(User user, String tokenType);
}
