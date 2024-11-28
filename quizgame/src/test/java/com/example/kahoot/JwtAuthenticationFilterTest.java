package com.example.kahoot;


import com.example.kahoot.model.User;
import com.example.kahoot.security.JwtAuthenticationFilter;
import com.example.kahoot.security.JwtTokenProvider;
import com.example.kahoot.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


//@SpringBootTest
//@AutoConfigureMockMvc
public class JwtAuthenticationFilterTest {

  /*  private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtTokenProvider mockTokenProvider;
    private UserService mockUserService;
    private FilterChain mockFilterChain;

    @BeforeEach
    void setUp() {
        mockTokenProvider = mock(JwtTokenProvider.class);
        mockUserService = mock(UserService.class);
        mockFilterChain = mock(FilterChain.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(mockTokenProvider, mockUserService);
    }

    @Test
    public void testNoJwtTokenInRequest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthenticationFilter.doFilterInternal(request, response, mockFilterChain);

        // Перевірка, що контекст аутентифікації порожній
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(mockFilterChain).doFilter(request, response);
        verifyNoInteractions(mockTokenProvider, mockUserService);
    }

    @Test
    public void testInvalidJwtToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid_token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(mockTokenProvider.validateToken("invalid_token")).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, mockFilterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(mockTokenProvider).validateToken("invalid_token");
        verifyNoInteractions(mockUserService);
        verify(mockFilterChain).doFilter(request, response);
    }

    @Test
    public void testJwtTokenNotAccessType() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid_refresh_token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(mockTokenProvider.validateToken("valid_refresh_token")).thenReturn(true);
        when(mockTokenProvider.isTokenOfType("valid_refresh_token", "access")).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, mockFilterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(mockTokenProvider).validateToken("valid_refresh_token");
        verify(mockTokenProvider).isTokenOfType("valid_refresh_token", "access");
        verifyNoInteractions(mockUserService);
        verify(mockFilterChain).doFilter(request, response);
    }

    @Test
    public void testValidAccessToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid_access_token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        User mockUser = mock(User.class);
        when(mockTokenProvider.validateToken("valid_access_token")).thenReturn(true);
        when(mockTokenProvider.isTokenOfType("valid_access_token", "access")).thenReturn(true);
        when(mockTokenProvider.getUserIdFromToken("valid_access_token")).thenReturn(1L);
        when(mockUserService.getUserById(1L)).thenReturn(mockUser);
        when(mockUser.getAuthorities()).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, mockFilterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(mockTokenProvider).validateToken("valid_access_token");
        verify(mockTokenProvider).isTokenOfType("valid_access_token", "access");
        verify(mockTokenProvider).getUserIdFromToken("valid_access_token");
        verify(mockUserService).getUserById(1L);
        verify(mockFilterChain).doFilter(request, response);
    }*/
}