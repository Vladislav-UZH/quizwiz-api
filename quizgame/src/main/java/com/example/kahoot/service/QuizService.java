package com.example.kahoot.service;

import com.example.kahoot.dto.AnswerDTO;
import com.example.kahoot.dto.QuestionDTO;
import com.example.kahoot.dto.QuizDTO;
import com.example.kahoot.model.Question;
import com.example.kahoot.model.Quiz;
import com.example.kahoot.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found"));
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public QuizDTO mapToDTO(Quiz quiz) {
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitle(quiz.getTitle());

        List<QuestionDTO> questionDTOs = quiz.getQuestions().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        quizDTO.setQuestions(questionDTOs);

        return quizDTO;
    }

    private QuestionDTO mapToDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setText(question.getText());

        List<AnswerDTO> answerDTOs = question.getAnswers().stream()
                .map(answer -> {
                    AnswerDTO answerDTO = new AnswerDTO();
                    answerDTO.setId(answer.getId());
                    answerDTO.setText(answer.getText());
                    return answerDTO;
                })
                .collect(Collectors.toList());

        questionDTO.setAnswers(answerDTOs);
        return questionDTO;
    }
}

