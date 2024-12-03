package com.example.kahoot.repository;

import com.example.kahoot.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {}
