package com.example.kahoot.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/message") // Принимает сообщения от клиента
    @SendTo("/topic/messages") // Рассылает сообщения всем подписчикам
    public String sendMessage(String message) {
        return "Received: " + message;
    }
}
