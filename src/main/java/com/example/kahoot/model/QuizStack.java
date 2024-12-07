package com.example.kahoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_stack")
public class QuizStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Конструктори
    public QuizStack() {
    }

    public QuizStack(String name) {
        this.name = name;
    }

    // Гетери та сетери
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
