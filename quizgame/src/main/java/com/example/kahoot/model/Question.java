package com.example.kahoot.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String title;
    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;


    public Question(String title) {
        this.title = title;
    }
}
