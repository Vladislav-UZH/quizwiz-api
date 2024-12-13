package com.example.kahoot.service;

import com.example.kahoot.dto.OptionDto;
import com.example.kahoot.interfaces.OptionService;
import com.example.kahoot.model.Option;
import com.example.kahoot.model.Question;
import com.example.kahoot.repository.OptionRepository;
import com.example.kahoot.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final QuestionRepository questionRepository;

    public OptionServiceImpl(OptionRepository optionRepository, QuestionRepository questionRepository) {
        this.optionRepository = optionRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public OptionDto createOption(OptionDto optionDto) {
        Question question = questionRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Option entity = new Option();
        entity.setQuestion(question);
        entity.setText(optionDto.getText());
        entity.setCorrect(optionDto.isCorrect()); // встановлюємо значення

        Option saved = optionRepository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public OptionDto getOptionById(Long id) {
        Option entity = optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found"));
        return mapToDto(entity);
    }

    @Override
    public List<OptionDto> getOptionsByQuestionId(Long questionId) {
        questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        return optionRepository.findAllByQuestion_Id(questionId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OptionDto updateOption(Long id, OptionDto optionDto) {
        Option entity = optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found"));

        Question question = questionRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        entity.setQuestion(question);
        entity.setText(optionDto.getText());
        entity.setCorrect(optionDto.isCorrect());

        Option updated = optionRepository.save(entity);
        return mapToDto(updated);
    }

    @Override
    public void deleteOption(Long id) {
        Option entity = optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found"));
        optionRepository.delete(entity);
    }

    private OptionDto mapToDto(Option entity) {
        OptionDto dto = new OptionDto();
        dto.setId(entity.getId());
        dto.setQuestionId(entity.getQuestion().getId());
        dto.setText(entity.getText());
        dto.setCorrect(entity.isCorrect()); // мапимо значення
        return dto;
    }
}
