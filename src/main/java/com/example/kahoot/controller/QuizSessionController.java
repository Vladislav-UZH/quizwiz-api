package com.example.kahoot.controller;

import com.example.kahoot.dto.QuizSessionDto;
import com.example.kahoot.interfaces.QuizSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-sessions")
public class QuizSessionController {

    private final QuizSessionService quizSessionService;

    public QuizSessionController(QuizSessionService quizSessionService) {
        this.quizSessionService = quizSessionService;
    }

    @PostMapping
    public ResponseEntity<QuizSessionDto> createQuizSession(@RequestBody QuizSessionDto quizSessionDto) {
        QuizSessionDto created = quizSessionService.createQuizSession(quizSessionDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizSessionDto> getQuizSessionById(@PathVariable Long id) {
        QuizSessionDto session = quizSessionService.getQuizSessionById(id);
        return ResponseEntity.ok(session);
    }

    @GetMapping
    public ResponseEntity<List<QuizSessionDto>> getAllQuizSessions() {
        List<QuizSessionDto> sessions = quizSessionService.getAllQuizSessions();
        return ResponseEntity.ok(sessions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizSessionDto> updateQuizSession(@PathVariable Long id, @RequestBody QuizSessionDto quizSessionDto) {
        QuizSessionDto updated = quizSessionService.updateQuizSession(id, quizSessionDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QuizSessionDto> deleteQuizSession(@PathVariable Long id) {
        // Отримаємо DTO до видалення
        QuizSessionDto sessionBeforeDelete = quizSessionService.getQuizSessionById(id);
        // Виконаємо видалення
        quizSessionService.deleteQuizSession(id);
        // Повертаємо DTO того, що було видалено
        return ResponseEntity.ok(sessionBeforeDelete);
    }
}
