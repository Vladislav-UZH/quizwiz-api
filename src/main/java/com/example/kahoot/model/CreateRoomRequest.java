package com.example.kahoot.dto;

public class CreateRoomRequest {
    private Integer quizTime;
    private Long quizId;

    public Integer getQuizTime() {
        return quizTime;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizTime(Integer quizTime) {
        this.quizTime = quizTime;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}