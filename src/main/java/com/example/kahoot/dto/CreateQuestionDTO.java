package com.example.kahoot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateQuestionDTO {
    private String text;
    private List<CreateAnswerDTO> answers;

}
