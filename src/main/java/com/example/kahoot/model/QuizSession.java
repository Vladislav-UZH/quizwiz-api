package com.example.kahoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_session")
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_time", nullable = false)
    private Integer quizTime; // загальний час, відведений на тест, у секундах чи іншій одиниці

    @ManyToOne
    @JoinColumn(name = "quiz_stack_id", nullable = false)
    private Quiz quiz; // посилання на Quiz

    @Column(name = "score", nullable = false)
    private Integer score = 0; // дефолтне значення 0

    @ManyToOne
    @JoinColumn(name = "current_question_id")
    private Question currentQuestion; // може бути null, якщо немає активного питання

    @Column(name = "current_question_start_time")
    private Integer currentQuestionStartTime; // час у мілісекундах з епохи, можна розглядати як long або int

    // Гетери і сетери
    public Long getId() {
        return id;
    }

    public Integer getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(Integer quizTime) {
        this.quizTime = quizTime;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Integer getCurrentQuestionStartTime() {
        return currentQuestionStartTime;
    }

    public void setCurrentQuestionStartTime(Integer currentQuestionStartTime) {
        this.currentQuestionStartTime = currentQuestionStartTime;
    }
}
