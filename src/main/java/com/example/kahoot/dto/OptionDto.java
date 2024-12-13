package com.example.kahoot.dto;

public class OptionDto {
    private Long id;
    private Long questionId;
    private String text;

    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void setText(String text) {
        this.text = text;
    }
}
