package com.example.kahoot.auth;

import com.example.kahoot.dto.UserResponse;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "message", "user" })
public class LogoutResponse {
    private String message;
    private UserResponse user;

    public LogoutResponse(String message, UserResponse user) {
        this.message = message;
        this.user = user;
    }

    // Геттери та сеттери

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}

