package com.example.kahoot.dto;

import com.example.kahoot.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String userName;
    private int score;
    private Role role;

    public UserResponse(String userName, int score, Role role) {
        this.userName = userName;
        this.score = score;
        this.role = role;
    }
}
