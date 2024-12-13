package com.example.kahoot.controller;

import com.example.kahoot.dto.QuestionDto;
import com.example.kahoot.interfaces.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto questionDto) {
        QuestionDto createdQuestion = questionService.createQuestion(questionDto);
        return ResponseEntity.ok(createdQuestion); // 200 OK з даними створеного питання
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
        QuestionDto question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question); // 200 OK з даними питання
    }

    @GetMapping("/by-quiz/{quizStackId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByQuizStackId(@PathVariable Long quizStackId) {
        List<QuestionDto> questions = questionService.getQuestionsByQuizStackId(quizStackId);
        return ResponseEntity.ok(questions); // 200 OK з списком питань
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long id, @RequestBody QuestionDto questionDto) {
        QuestionDto updatedQuestion = questionService.updateQuestion(id, questionDto);
        return ResponseEntity.ok(updatedQuestion); // 200 OK з оновленим питанням
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().build(); // 200 OK без тіла
    }
}
