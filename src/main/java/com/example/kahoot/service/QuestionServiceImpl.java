package com.example.kahoot.service;

import com.example.kahoot.dto.OptionDto;
import com.example.kahoot.dto.QuestionDto;
import com.example.kahoot.interfaces.QuestionService;
import com.example.kahoot.model.Option;
import com.example.kahoot.model.Question;
import com.example.kahoot.model.Quiz;
import com.example.kahoot.repository.QuestionRepository;
import com.example.kahoot.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    @Override
    public QuestionDto createQuestion(QuestionDto questionDto) {
        Quiz quiz = quizRepository.findById(questionDto.getQuizStackId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Question entity = new Question();
        entity.setQuiz(quiz);
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
        quizRepository.findById(quizStackId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        return questionRepository.findAllByQuiz_Id(quizStackId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDto updateQuestion(Long id, QuestionDto questionDto) {
        Question entity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Quiz quiz = quizRepository.findById(questionDto.getQuizStackId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        entity.setQuiz(quiz);
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
        dto.setQuizStackId(entity.getQuiz().getId());
        dto.setText(entity.getText());

        List<OptionDto> optionsDto = entity.getOptions().stream().map(opt -> {
            OptionDto oDto = new OptionDto();
            oDto.setId(opt.getId());
            oDto.setQuestionId(entity.getId());
            oDto.setText(opt.getText());
            return oDto;
        }).collect(Collectors.toList());

        dto.setOptions(optionsDto);
        return dto;
    }
}
