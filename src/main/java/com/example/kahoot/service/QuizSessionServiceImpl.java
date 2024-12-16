package com.example.kahoot.service;

import com.example.kahoot.dto.QuizSessionDto;
import com.example.kahoot.interfaces.QuizSessionService;
import com.example.kahoot.model.Quiz;
import com.example.kahoot.model.Question;
import com.example.kahoot.model.QuizSession;
import com.example.kahoot.repository.QuestionRepository;
import com.example.kahoot.repository.QuizRepository;
import com.example.kahoot.repository.QuizSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizSessionServiceImpl implements QuizSessionService {

    private final QuizSessionRepository quizSessionRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuizSessionServiceImpl(QuizSessionRepository quizSessionRepository,
                                  QuizRepository quizRepository,
                                  QuestionRepository questionRepository) {
        this.quizSessionRepository = quizSessionRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public QuizSessionDto createQuizSession(QuizSessionDto quizSessionDto) {
        Quiz quiz = quizRepository.findById(quizSessionDto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        QuizSession session = new QuizSession();
        session.setQuizTime(quizSessionDto.getQuizTime());
        session.setQuiz(quiz);
        session.setScore(quizSessionDto.getScore() != null ? quizSessionDto.getScore() : 0);

        if (quizSessionDto.getCurrentQuestionId() != null) {
            Question question = questionRepository.findById(quizSessionDto.getCurrentQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            session.setCurrentQuestion(question);
        }

        session.setCurrentQuestionStartTime(quizSessionDto.getCurrentQuestionStartTime());

        QuizSession saved = quizSessionRepository.save(session);
        return mapToDto(saved);
    }

    @Override
    public QuizSessionDto getQuizSessionById(Long id) {
        QuizSession session = quizSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizSession not found"));
        return mapToDto(session);
    }

    @Override
    public List<QuizSessionDto> getAllQuizSessions() {
        return quizSessionRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizSessionDto updateQuizSession(Long id, QuizSessionDto quizSessionDto) {
        QuizSession session = quizSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizSession not found"));

        Quiz quiz = quizRepository.findById(quizSessionDto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        session.setQuizTime(quizSessionDto.getQuizTime());
        session.setQuiz(quiz);
        session.setScore(quizSessionDto.getScore() != null ? quizSessionDto.getScore() : session.getScore());

        if (quizSessionDto.getCurrentQuestionId() != null) {
            Question question = questionRepository.findById(quizSessionDto.getCurrentQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            session.setCurrentQuestion(question);
        } else {
            session.setCurrentQuestion(null);
        }

        session.setCurrentQuestionStartTime(quizSessionDto.getCurrentQuestionStartTime());

        QuizSession updated = quizSessionRepository.save(session);
        return mapToDto(updated);
    }

    @Override
    public void deleteQuizSession(Long id) {
        QuizSession session = quizSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizSession not found"));
        quizSessionRepository.delete(session);
    }

    private QuizSessionDto mapToDto(QuizSession entity) {
        QuizSessionDto dto = new QuizSessionDto();
        dto.setId(entity.getId());
        dto.setQuizTime(entity.getQuizTime());
        dto.setQuizId(entity.getQuiz().getId());
        dto.setScore(entity.getScore());
        dto.setCurrentQuestionId(entity.getCurrentQuestion() != null ? entity.getCurrentQuestion().getId() : null);
        dto.setCurrentQuestionStartTime(entity.getCurrentQuestionStartTime());
        return dto;
    }
}
