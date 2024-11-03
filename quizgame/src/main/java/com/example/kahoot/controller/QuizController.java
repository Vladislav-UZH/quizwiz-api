package com.example.kahoot.controller;

import com.example.kahoot.dto.QuizDTO;
import com.example.kahoot.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

// QuizController
@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        logger.info("Fetching all quizzes");
        List<QuizDTO> quizzes = quizService.getAllQuizzes().stream()
                .map(quizService::mapToDTO)
                .collect(Collectors.toList());

        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(quizzes);
    }
}