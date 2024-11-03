package com.example.kahoot.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String userName;
    private int score;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void setRole(Role role) {
        this.role = role;
    }

    public void setScore(int score) {
        this.score = score;
    }
}