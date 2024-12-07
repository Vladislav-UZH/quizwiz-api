package com.example.kahoot.service;

import com.example.kahoot.dto.OptionDto;
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
        // Перевіряємо, чи існує Question
        questionRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Option entity = new Option();
        entity.setQuestionId(optionDto.getQuestionId());
        entity.setText(optionDto.getText());

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
        // Перевірка існування питання
        questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        return optionRepository.findAllByQuestionId(questionId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OptionDto updateOption(Long id, OptionDto optionDto) {
        Option entity = optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found"));

        questionRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        entity.setQuestionId(optionDto.getQuestionId());
        entity.setText(optionDto.getText());

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
        dto.setQuestionId(entity.getQuestionId());
        dto.setText(entity.getText());
        return dto;
    }
}
