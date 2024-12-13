package com.example.kahoot.dto;

public class OptionDto {
    private Long id;
    private Long questionId;
    private String text;
    private boolean isCorrect; // нове поле

    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return isCorrect;
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

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
