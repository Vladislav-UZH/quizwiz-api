package com.example.kahoot.auth;

import com.example.kahoot.dto.UserResponse;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({ "accessToken", "refreshToken", "user" })
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UserResponse user;

    public AuthResponse(String accessToken, String refreshToken, UserResponse user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    // Геттери та сеттери

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
