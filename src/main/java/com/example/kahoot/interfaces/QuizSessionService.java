package com.example.kahoot.interfaces;

import com.example.kahoot.dto.QuizSessionDto;

import java.util.List;

public interface QuizSessionService {
    QuizSessionDto createQuizSession(QuizSessionDto quizSessionDto);
    QuizSessionDto getQuizSessionById(Long id);
    List<QuizSessionDto> getAllQuizSessions();
    QuizSessionDto updateQuizSession(Long id, QuizSessionDto quizSessionDto);
    void deleteQuizSession(Long id);
}
