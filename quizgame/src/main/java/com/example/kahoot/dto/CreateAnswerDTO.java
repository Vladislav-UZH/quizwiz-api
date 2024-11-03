package com.example.kahoot.dto;

public class CreateAnswerDTO {
    private String text;
    private boolean isCorrect;

    // Геттеры и сеттеры

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
