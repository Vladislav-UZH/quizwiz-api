package com.example.kahoot.repository;

import com.example.kahoot.model.QuizStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizStackRepository extends JpaRepository<QuizStack, Long> {
    // Тут можна прописувати додаткові методи пошуку, якщо потрібно
}
