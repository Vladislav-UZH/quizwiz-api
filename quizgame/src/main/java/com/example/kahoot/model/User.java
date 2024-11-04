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
    @Setter
    private int score;

    public User() {
    }

    public User(Long id, String userName, int score, Role role) {
        this.id = id;
        this.userName = userName;
        this.score = score;
        this.role = role;
    }

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    public void setId(Long id) {
        this.id = id;
    }
}