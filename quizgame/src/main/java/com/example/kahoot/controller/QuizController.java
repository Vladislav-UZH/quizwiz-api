package com.example.kahoot.controller;

import com.example.kahoot.dto.QuizDTO;
import com.example.kahoot.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// QuizController.java
@RestController
@RequestMapping("/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<QuizDTO> quizzes = quizService.getAllQuizzes().stream()
                .map(quizService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quizzes);
    }
}