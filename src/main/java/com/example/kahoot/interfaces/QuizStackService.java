package com.example.kahoot.interfaces;

import com.example.kahoot.dto.QuizStackDto;

import java.util.List;

public interface QuizStackService {
    QuizStackDto createQuizStack(QuizStackDto quizStackDto);
    QuizStackDto getQuizStackById(Long id);
    List<QuizStackDto> getAllQuizStacks();
    QuizStackDto updateQuizStack(Long id, QuizStackDto quizStackDto);
    void deleteQuizStack(Long id);
}
