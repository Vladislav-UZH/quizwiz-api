package com.example.kahoot.security;

import com.example.kahoot.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @Autowired
    public SecurityConfig(JwtTokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(tokenProvider, userService);


        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // 2) Disable CSRF if you’re using stateless JWT
        http.csrf(csrf -> csrf.disable());
        

        http
                        .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register", "/auth/refresh").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/api/quizzes/**").permitAll()
                        .requestMatchers("/api/questions/**").permitAll()
                        .requestMatchers("/api/options/**").permitAll()
                        .requestMatchers("/api/quiz-sessions/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 4. Сесію не зберігати (JWT — Stateless)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 5. Вставляємо наш фільтр перед стандартним фільтром аутентифікації
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // 6. Обробка помилок
                .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
                    System.out.println("Authentication Entry Point called: " + authException.getMessage());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                }));

        return http.build();
    }

 @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // explicitly list the origins instead of '*'
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        // allow credentials
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        
        // Then apply to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
        
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
