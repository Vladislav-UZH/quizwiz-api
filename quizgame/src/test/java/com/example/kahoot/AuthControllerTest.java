package com.example.kahoot;

import com.example.kahoot.dto.UserCredentials;
import com.example.kahoot.model.Role;
import com.example.kahoot.model.User;
import com.example.kahoot.security.JwtTokenProvider;
import com.example.kahoot.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//@AutoConfigureMockMvc
 class AuthControllerTest {

  /*  @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private UserService userService;

    @Test
    void testRegisterSuccess() throws Exception {
        // Мок користувача
        User mockUser = new User(1L, "testUser", "hashedPassword", Role.ROLE_USER, 0);

        // Мок відповіді на виклик сервісу
        Mockito.when(userService.createUser(Mockito.anyString(), Mockito.anyString(), Mockito.eq(Role.ROLE_USER)))
                .thenReturn(mockUser);

        Mockito.when(tokenProvider.generateToken(Mockito.eq(mockUser), Mockito.eq("access")))
                .thenReturn("accessToken");
        Mockito.when(tokenProvider.generateToken(Mockito.eq(mockUser), Mockito.eq("refresh")))
                .thenReturn("refreshToken");

        // Виконання POST-запиту на /auth/register
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"))
                .andExpect(jsonPath("$.user.username").value("testUser"))
                .andExpect(jsonPath("$.user.role").value("ROLE_USER"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Мок користувача
        User mockUser = new User(1L, "testUser", "hashedPassword", Role.ROLE_USER, 10);

        // Мок відповіді на виклик сервісу
        Mockito.when(userService.authenticate(Mockito.any(UserCredentials.class))).thenReturn(mockUser);

        Mockito.when(tokenProvider.generateToken(Mockito.eq(mockUser), Mockito.eq("access")))
                .thenReturn("accessToken");
        Mockito.when(tokenProvider.generateToken(Mockito.eq(mockUser), Mockito.eq("refresh")))
                .thenReturn("refreshToken");

        // Виконання POST-запиту на /auth/login
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"))
                .andExpect(jsonPath("$.user.username").value("testUser"))
                .andExpect(jsonPath("$.user.score").value(10));
    }

    @Test
    void testRefreshSuccess() throws Exception {
        // Мок користувача
        User mockUser = new User(1L, "testUser", "hashedPassword", Role.ROLE_USER, 20);

        // Мок відповіді на виклик сервісу
        Mockito.when(tokenProvider.validateToken(Mockito.anyString())).thenReturn(true);
        Mockito.when(tokenProvider.isTokenOfType(Mockito.anyString(), Mockito.eq("refresh"))).thenReturn(true);
        Mockito.when(tokenProvider.getUserIdFromToken(Mockito.anyString())).thenReturn(1L);
        Mockito.when(userService.getUserById(Mockito.eq(1L))).thenReturn(mockUser);

        Mockito.when(tokenProvider.generateToken(Mockito.eq(mockUser), Mockito.eq("access")))
                .thenReturn("newAccessToken");

        // Виконання POST-запиту на /auth/refresh
        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"refreshToken\": \"validRefreshToken\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value("validRefreshToken"))
                .andExpect(jsonPath("$.user.username").value("testUser"))
                .andExpect(jsonPath("$.user.score").value(20));
    }

    @Test
    void testRefreshInvalidToken() throws Exception {
        // Мок перевірки токена
        Mockito.when(tokenProvider.validateToken(Mockito.anyString())).thenReturn(false);

        // Виконання POST-запиту на /auth/refresh
        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"refreshToken\": \"invalidToken\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid refresh token"));
    }*/
}
