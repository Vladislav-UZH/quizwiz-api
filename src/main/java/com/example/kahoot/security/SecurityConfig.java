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

// Для CORS
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

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

        http
                // 1. Увімкнути CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 2. Вимкнути CSRF (необов’язково, якщо використовуєте токени)
                .csrf(AbstractHttpConfigurer::disable)
                // 3. Налаштування правил доступу
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

    /**
     * Глобальна конфігурація CORS.
     * Дозволяє фронтенду з http://localhost:3000 (або будь-якого іншого порту)
     * звертатись до нашого API.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Дозволити конкретний Origin або поставити "*" (але на продакшні краще конкретний список)
        configuration.addAllowedOrigin("http://localhost:3000");
        // Якщо потрібно дозволяти будь-які, можна використати:
        // configuration.addAllowedOriginPattern("*");

        // Дозволити всі типи заголовків
        configuration.addAllowedHeader("*");
        // Дозволити всі методи (GET, POST, PUT, DELETE...)
        configuration.addAllowedMethod("*");
        // Якщо потрібно відправляти кукі чи авторизацію між доменами
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Застосувати цю конфігурацію для всіх URL
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
