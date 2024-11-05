package com.example.kahoot;

import com.example.kahoot.controller.AdminController;
import com.example.kahoot.model.Quiz;
import com.example.kahoot.model.Role;
import com.example.kahoot.model.User;
import com.example.kahoot.service.QuizService;
import com.example.kahoot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @MockBean
    private UserService userService;

    private User adminUser;
    private User regularUser;
    private Quiz quiz;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        adminUser = new User(1L, "admin", 0, Role.ROLE_ADMIN);
        regularUser = new User(2L, "user", 0, Role.ROLE_USER);
        quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
    }

    @Test
    void testCreateQuiz_AdminAccess() throws Exception {
        when(userService.getUserById(1L)).thenReturn(adminUser);
        when(quizService.createQuiz(any(Quiz.class))).thenReturn(quiz);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/create-quiz")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Sample Quiz\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateQuiz_NonAdminAccess() throws Exception {
        // Arrange
        when(userService.getUserById(2L)).thenReturn(regularUser);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/create-quiz")
                        .param("userId", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Sample Quiz\"}"))
                .andExpect(status().isForbidden());

        verify(quizService, never()).createQuiz(any(Quiz.class));
    }

    @Test
    void testCreateQuiz_UserNotFound() throws Exception {
        // Arrange
        when(userService.getUserById(3L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/create-quiz")
                        .param("userId", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Sample Quiz\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateQuiz_UserIdMissing() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/create-quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Sample Quiz\"}"))
                .andExpect(status().isBadRequest());
    }

}