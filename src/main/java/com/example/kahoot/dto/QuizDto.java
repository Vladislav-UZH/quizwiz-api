package com.example.kahoot.dto;

import java.util.List;

public class QuizDto {
    private Long id;
    private String title;
    private List<QuestionDto> questions;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}