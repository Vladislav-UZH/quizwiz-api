package com.example.kahoot.auth;

import com.example.kahoot.dto.UserResponse;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({"message", "user"})
public class LogoutResponse {
    private String message;
    private UserResponse user;

    public LogoutResponse(String message, UserResponse user) {
        this.message = message;
        this.user = user;
    }


}

