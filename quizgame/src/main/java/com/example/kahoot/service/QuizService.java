package com.example.kahoot.service;

import com.example.kahoot.model.Quiz;
import com.example.kahoot.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found"));
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz); // Сохранение квиза и возвращение результата
    }
}
