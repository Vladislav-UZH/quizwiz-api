package com.example.kahoot.interfaces;

import com.example.kahoot.dto.QuizDto;

import java.util.List;

public interface QuizService {
    QuizDto createQuiz(QuizDto quizDto);
    QuizDto getQuizById(Long id);
    List<QuizDto> getAllQuizzes();
    QuizDto updateQuiz(Long id, QuizDto quizDto);
    void deleteQuiz(Long id);
}
