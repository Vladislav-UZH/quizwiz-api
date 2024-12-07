package com.example.kahoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_stack_id", nullable = false)
    private Long quizStackId;

    @Column(nullable = false)
    private String text;

    // Конструктори
    public Question() {
    }

    public Question(Long quizStackId, String text) {
        this.quizStackId = quizStackId;
        this.text = text;
    }

    // Гетери та сетери
    public Long getId() {
        return id;
    }

    public Long getQuizStackId() {
        return quizStackId;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuizStackId(Long quizStackId) {
        this.quizStackId = quizStackId;
    }

    public void setText(String text) {
        this.text = text;
    }
}
