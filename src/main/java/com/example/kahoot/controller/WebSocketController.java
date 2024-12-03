package com.example.kahoot.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/quiz")
    @SendTo("/topic/quiz-progress")
    public String handleQuizMessage(String message) {
        // Просто повертає повідомлення, щоб перевірити з'єднання
        return "Message received: " + message;
    }
}
