package com.example.kahoot.controller;

import com.example.kahoot.dto.*;
import com.example.kahoot.dto.CreateRoomRequest;
import com.example.kahoot.dto.QuestionEndRequest;
import com.example.kahoot.dto.QuestionStartRequest;
import com.example.kahoot.interfaces.QuizSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz-sessions")
public class QuizSessionController {

    private final QuizSessionService quizSessionService;

    public QuizSessionController(QuizSessionService quizSessionService) {
        this.quizSessionService = quizSessionService;
    }

    // createRoom - тепер приймає дані з тіла JSON у форматі CreateRoomRequest
    @PostMapping("/create-room")
    public ResponseEntity<QuizSessionDto> createRoom(@RequestBody CreateRoomRequest request) {
        QuizSessionDto dto = quizSessionService.createRoom(request.getQuizTime(), request.getQuizId());
        return ResponseEntity.ok(dto);
    }

    // questionStart - тепер приймає дані з тіла JSON у форматі QuestionStartRequest
    @PostMapping("/question-start")
    public ResponseEntity<QuizSessionDto> questionStart(@RequestBody QuestionStartRequest request) {
        QuizSessionDto dto = quizSessionService.questionStart(
                request.getId(),
                request.getCurrentQuestionId(),
                request.getCurrentQuestionStartTime()
        );
        return ResponseEntity.ok(dto);
    }

    // questionEnd - приймає дані з тіла JSON у форматі QuestionEndRequest
    @PostMapping("/question-end")
    public ResponseEntity<Integer> questionEnd(@RequestBody QuestionEndRequest request) {
        int increment = quizSessionService.questionEnd(
                request.getId(),
                request.getCurrentQuestionId(),
                request.getFinish_time(),
                request.isCorrect()
        );
        return ResponseEntity.ok(increment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizSessionDto> getQuizSession(@PathVariable Long id) {
        return ResponseEntity.ok(quizSessionService.getQuizSessionById(id));
    }
}
