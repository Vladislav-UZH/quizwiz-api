package com.example.kahoot.dto;

import java.util.List;

public class CreateQuestionDTO {
    private String text;
    private List<CreateAnswerDTO> answers;

    // Геттеры и сеттеры

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CreateAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<CreateAnswerDTO> answers) {
        this.answers = answers;
    }
}
