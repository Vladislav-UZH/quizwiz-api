package com.example.kahoot.dto;

import lombok.Getter;
import lombok.Setter;

public class CreateAnswerDTO {
    @Setter
    @Getter
    private String text;
    private boolean isCorrect;

    public CreateAnswerDTO(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
