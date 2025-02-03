package com.example.kahoot.service;

import com.example.kahoot.dto.OptionDto;
import com.example.kahoot.dto.QuestionDto;
import com.example.kahoot.dto.QuizDto;
import com.example.kahoot.interfaces.QuizService;
import com.example.kahoot.model.Question;
import com.example.kahoot.model.Quiz;
import com.example.kahoot.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public QuizDto createQuiz(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDto.getTitle());
        Quiz saved = quizRepository.save(quiz);
        return mapToDto(saved);
    }

    @Override
    public QuizDto getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        return mapToDto(quiz);
    }

    @Override
    public List<QuizDto> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizDto updateQuiz(Long id, QuizDto quizDto) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setTitle(quizDto.getTitle());
        Quiz updated = quizRepository.save(quiz);
        return mapToDto(updated);
    }

    @Override
    public void deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quizRepository.delete(quiz);
    }

    private QuizDto mapToDto(Quiz quiz) {
        // Створюємо DTO для самого Quiz
        QuizDto dto = new QuizDto();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());

        // Мапимо список питань
        List<QuestionDto> questionDtos = quiz.getQuestions().stream()
                .map(q -> {
                    // Створюємо DTO для одного Question
                    QuestionDto qDto = new QuestionDto();
                    qDto.setId(q.getId());
                    // Зверніть увагу: поле в DTO називається quizStackId,
                    //  але фактично ми сюди кладемо quiz.getId().
                    //  Якщо це дійсно коректно, залишаємо як є.
                    qDto.setQuizStackId(quiz.getId());
                    qDto.setText(q.getText());

                    // Тепер МАПІНГ OPTIONS для кожного question
                    List<OptionDto> optionDtos = q.getOptions().stream()
                            .map(opt -> {
                                OptionDto oDto = new OptionDto();
                                oDto.setId(opt.getId());
                                oDto.setQuestionId(q.getId());
                                oDto.setText(opt.getText());
                                oDto.setCorrect(opt.isCorrect());
                                return oDto;
                            })
                            .collect(Collectors.toList());

                    qDto.setOptions(optionDtos);
                    return qDto;
                })
                .collect(Collectors.toList());

        dto.setQuestions(questionDtos);
        return dto;
    }

}
