package com.example.kahoot.interfaces;

import com.example.kahoot.dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    QuestionDto createQuestion(QuestionDto questionDto);
    QuestionDto getQuestionById(Long id);
    List<QuestionDto> getQuestionsByQuizStackId(Long quizStackId);
    QuestionDto updateQuestion(Long id, QuestionDto questionDto);
    void deleteQuestion(Long id);
}

