package com.example.kahoot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String text; // Переименовано для соответствия содержанию

    @Setter
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Setter
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    public Question() {}

    public Question(String text) {
        this.text = text;
    }

}
