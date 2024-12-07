package com.example.kahoot.service;

import com.example.kahoot.dto.QuestionDto;
import com.example.kahoot.interfaces.QuestionService;
import com.example.kahoot.model.Question;
import com.example.kahoot.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizStackRepository quizStackRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuizStackRepository quizStackRepository) {
        this.questionRepository = questionRepository;
        this.quizStackRepository = quizStackRepository;
    }

    @Override
    public QuestionDto createQuestion(QuestionDto questionDto) {
        // Перевіряємо, чи існує QuizStack
        quizStackRepository.findById(questionDto.getQuizStackId())
                .orElseThrow(() -> new RuntimeException("QuizStack not found"));

        Question entity = new Question();
        entity.setQuizStackId(questionDto.getQuizStackId());
        entity.setText(questionDto.getText());

        Question saved = questionRepository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public QuestionDto getQuestionById(Long id) {
        Question entity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return mapToDto(entity);
    }

    @Override
    public List<QuestionDto> getQuestionsByQuizStackId(Long quizStackId) {
        // Можна переконатись, що QuizStack існує, якщо потрібно
        quizStackRepository.findById(quizStackId)
                .orElseThrow(() -> new RuntimeException("QuizStack not found"));

        return questionRepository.findAllByQuizStackId(quizStackId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDto updateQuestion(Long id, QuestionDto questionDto) {
        Question entity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Перевіряємо quizStack
        quizStackRepository.findById(questionDto.getQuizStackId())
                .orElseThrow(() -> new RuntimeException("QuizStack not found"));

        entity.setQuizStackId(questionDto.getQuizStackId());
        entity.setText(questionDto.getText());

        Question updated = questionRepository.save(entity);
        return mapToDto(updated);
    }

    @Override
    public void deleteQuestion(Long id) {
        Question entity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        questionRepository.delete(entity);
    }

    private QuestionDto mapToDto(Question entity) {
        QuestionDto dto = new QuestionDto();
        dto.setId(entity.getId());
        dto.setQuizStackId(entity.getQuizStackId());
        dto.setText(entity.getText());
        return dto;
    }
}
