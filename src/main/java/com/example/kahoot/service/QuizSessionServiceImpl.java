package com.example.kahoot.service;

import com.example.kahoot.dto.QuizSessionDto;
import com.example.kahoot.interfaces.QuizSessionService;
import com.example.kahoot.model.Quiz;
import com.example.kahoot.model.QuizSession;
import com.example.kahoot.repository.QuizRepository;
import com.example.kahoot.repository.QuizSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuizSessionServiceImpl implements QuizSessionService {

    private final QuizSessionRepository quizSessionRepository;
    private final QuizRepository quizRepository;

    public QuizSessionServiceImpl(QuizSessionRepository quizSessionRepository, QuizRepository quizRepository) {
        this.quizSessionRepository = quizSessionRepository;
        this.quizRepository = quizRepository;
    }

    @Override
    public QuizSessionDto createRoom(Integer quizTime, Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        QuizSession session = new QuizSession();
        session.setQuizTime(quizTime);
        session.setQuiz(quiz);
        session.setScore(0);
        session.setCurrentQuestionId(-1);
        session.setCurrentQuestionStartTime(0);

        QuizSession saved = quizSessionRepository.save(session);
        return mapToDto(saved);
    }

    @Override
    public QuizSessionDto questionStart(Long id, Integer currentQuestionId, Integer currentQuestionStartTime) {
        QuizSession session = quizSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizSession not found"));
        session.setCurrentQuestionId(currentQuestionId);
        session.setCurrentQuestionStartTime(currentQuestionStartTime);
        QuizSession updated = quizSessionRepository.save(session);
        return mapToDto(updated);
    }

    @Override
    public Integer questionEnd(Long id, Integer currentQuestionId, Integer finishTime, boolean correct) {
        QuizSession session = quizSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizSession not found"));

        // Перевіримо, що currentQuestionId збігається з тим, що вказано
        if (!session.getCurrentQuestionId().equals(currentQuestionId)) {
            throw new RuntimeException("Current question does not match session's current question.");
        }

        Quiz quiz = session.getQuiz();
        int n = quiz.getQuestions().size(); // Кількість питань у квізі
        int quizTime = session.getQuizTime();
        int currentQuestionStartTime = session.getCurrentQuestionStartTime();

        // ФОРМУЛА:
        // (1 - ((finish_time - currentQuestionStartTime) / (quizTime / n))) * 500 + 500
        // Якщо correct = true, застосовуємо формулу, інакше increment = 0.

        int increment = 0;
        if (correct) {
            double numerator = (finishTime - currentQuestionStartTime);
            double denominator = (double)quizTime / n;
            double ratio = numerator / denominator;
            double value = (1 - ratio) * 500 + 500;
            increment = (int)Math.round(value);
        }

        session.setScore(session.getScore() + increment);
        quizSessionRepository.save(session);

        return increment; // Повертаємо тільки increment
    }

    @Override
    public QuizSessionDto getQuizSessionById(Long id) {
        QuizSession session = quizSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizSession not found"));
        return mapToDto(session);
    }

    private QuizSessionDto mapToDto(QuizSession entity) {
        return new QuizSessionDto(
                entity.getId(),
                entity.getQuizTime(),
                entity.getQuiz().getId(),
                entity.getScore(),
                entity.getCurrentQuestionId(),
                entity.getCurrentQuestionStartTime()
        );
    }
}
