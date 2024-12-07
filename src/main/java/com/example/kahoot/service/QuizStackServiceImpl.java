package com.example.kahoot.service;

import com.example.kahoot.dto.QuizStackDto;
import com.example.kahoot.interfaces.QuizStackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizStackServiceImpl implements QuizStackService {

    private final QuizStackRepository quizStackRepository;

    public QuizStackServiceImpl(QuizStackRepository quizStackRepository) {
        this.quizStackRepository = quizStackRepository;
    }

    @Override
    public QuizStackDto createQuizStack(QuizStackDto quizStackDto) {
        QuizStack entity = new QuizStack();
        entity.setName(quizStackDto.getName());
        QuizStack saved = quizStackRepository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public QuizStackDto getQuizStackById(Long id) {
        QuizStack entity = quizStackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizStack not found"));
        return mapToDto(entity);
    }

    @Override
    public List<QuizStackDto> getAllQuizStacks() {
        return quizStackRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizStackDto updateQuizStack(Long id, QuizStackDto quizStackDto) {
        QuizStack entity = quizStackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizStack not found"));

        entity.setName(quizStackDto.getName());
        QuizStack updated = quizStackRepository.save(entity);
        return mapToDto(updated);
    }

    @Override
    public void deleteQuizStack(Long id) {
        QuizStack entity = quizStackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizStack not found"));
        quizStackRepository.delete(entity);
    }

    private QuizStackDto mapToDto(QuizStack entity) {
        QuizStackDto dto = new QuizStackDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
