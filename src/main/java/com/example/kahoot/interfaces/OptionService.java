package com.example.kahoot.interfaces;

import com.example.kahoot.dto.OptionDto;

import java.util.List;

public interface OptionService {
    OptionDto createOption(OptionDto optionDto);
    OptionDto getOptionById(Long id);
    List<OptionDto> getOptionsByQuestionId(Long questionId);
    OptionDto updateOption(Long id, OptionDto optionDto);
    void deleteOption(Long id);
}

