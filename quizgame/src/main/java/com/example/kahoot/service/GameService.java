package com.example.kahoot.service;

import com.example.kahoot.model.Quiz;
import com.example.kahoot.model.User;
import com.example.kahoot.repository.QuizRepository;
import com.example.kahoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuizRepository quizRepository;

    public int calculateScore(User user, Quiz quiz) {
        // Логика расчета баллов
        return 0;
    }
}
