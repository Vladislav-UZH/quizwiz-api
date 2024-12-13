package com.example.kahoot.controller;

import com.example.kahoot.dto.QuizDto;
import com.example.kahoot.interfaces.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto quizDto) {
        QuizDto createdQuiz = quizService.createQuiz(quizDto);
        return ResponseEntity.ok(createdQuiz); // Повертає статус 200 та DTO
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long id) {
        QuizDto quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz); // Повертає статус 200 та DTO
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> getAllQuizzes() {
        List<QuizDto> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes); // Повертає статус 200 та список DTO
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDto> updateQuiz(@PathVariable Long id, @RequestBody QuizDto quizDto) {
        QuizDto updatedQuiz = quizService.updateQuiz(id, quizDto);
        return ResponseEntity.ok(updatedQuiz); // Повертає статус 200 та оновлений DTO
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok().build(); // Повертає статус 200 без тіла, але за потреби можна повертати і DTO
    }
}
