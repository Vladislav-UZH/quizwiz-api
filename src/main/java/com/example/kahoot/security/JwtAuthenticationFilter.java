package com.example.kahoot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.kahoot.model.User;
import com.example.kahoot.service.UserService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("JwtAuthenticationFilter is called for URI: " + request.getRequestURI());

        String path = request.getRequestURI();

        // Якщо запит стосується публічних ендпойнтів, пропускаємо без перевірки токена
        if (path.startsWith("/api/quizzes") || path.startsWith("/api/questions") || path.startsWith("/api/options")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                System.out.println("JWT Token: " + jwt);
                if (tokenProvider.validateToken(jwt)) {
                    System.out.println("Token is valid");
                    if (tokenProvider.isTokenOfType(jwt, "access")) {
                        System.out.println("Token is of type 'access'");
                    } else {
                        System.out.println("Token is NOT of type 'access'");
                    }
                } else {
                    System.out.println("Token is invalid");
                }
            } else {
                System.out.println("No JWT Token found in request");
            }

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) &&
                    tokenProvider.isTokenOfType(jwt, "access")) {

                Long userId = tokenProvider.getUserIdFromToken(jwt);
                User user = userService.getUserById(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            // Логування або інша обробка помилок
            ex.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
