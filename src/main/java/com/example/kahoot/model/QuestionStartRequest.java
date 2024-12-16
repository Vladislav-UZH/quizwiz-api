package com.example.kahoot.dto;

public class QuestionStartRequest {
    private Long id;
    private Integer currentQuestionId;
    private Integer currentQuestionStartTime;

    public Long getId() {
        return id;
    }

    public Integer getCurrentQuestionId() {
        return currentQuestionId;
    }

    public Integer getCurrentQuestionStartTime() {
        return currentQuestionStartTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCurrentQuestionId(Integer currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public void setCurrentQuestionStartTime(Integer currentQuestionStartTime) {
        this.currentQuestionStartTime = currentQuestionStartTime;
    }
}