package com.example.kahoot.interfaces;

import com.example.kahoot.dto.QuizSessionDto;

public interface QuizSessionService {
    QuizSessionDto createRoom(Integer quizTime, Long quizId);
    QuizSessionDto questionStart(Long id, Integer currentQuestionId, Integer currentQuestionStartTime);
    Integer questionEnd(Long id, Integer currentQuestionId, Integer finishTime, boolean correct);

    QuizSessionDto getQuizSessionById(Long id);
    // Якщо потрібно, можна залишити інші CRUD методи, але з умови не зрозуміло, чи потрібні вони:
    // Виходячи з вимог, зосередимось на нових методах.
}
